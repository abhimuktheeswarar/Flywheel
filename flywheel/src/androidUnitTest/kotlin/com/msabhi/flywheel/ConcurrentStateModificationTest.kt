/*
 * Copyright (C) 2021 Abhi Muktheeswarar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.msabhi.flywheel

import com.msabhi.flywheel.attachments.DispatcherProviderImpl
import com.msabhi.flywheel.attachments.SideEffect
import com.msabhi.flywheel.common.CollectionAction
import com.msabhi.flywheel.common.CollectionState
import kotlinx.coroutines.*
import org.junit.Test
import kotlin.test.assertTrue

/**
 * Demonstrates the concurrent lost-update problem with full-map replacement in reducers.
 *
 * Two SideEffects react to the same trigger action, each modifying different keys
 * in the state's map. Both read the same snapshot (empty map) from actionStates,
 * add their keys, and dispatch a full map replacement. The reducer replaces the
 * entire map each time, so the last writer wins and the first writer's changes are lost.
 *
 * Expected (desired): 10 entries (5 from A + 5 from B)
 * Actual (bug): 5 entries (only the last SideEffect's changes survive)
 */
class ConcurrentStateModificationTest {

    private val reduce: Reduce<CollectionState> = { action, state ->
        when (action) {
            is CollectionAction.UpdateItems -> state.copy(items = action.items)
            else -> state
        }
    }

    internal class MapModifierSideEffect(
        stateReserve: StateReserve<CollectionState>,
        private val keyPrefix: String,
        private val keyCount: Int,
        private val processingDelayMs: Long,
        private val onCompleted: CompletableDeferred<Unit>,
    ) : SideEffect<CollectionState>(stateReserve, DispatcherProviderImpl) {

        init {
            scope.launch {
                actionStates.collect { actionState ->
                    if (actionState.action is CollectionAction.TriggerProcessing) {
                        // Both SideEffects receive the SAME state snapshot here (empty map)
                        val currentItems = actionState.state.items.toMutableMap()
                        // Simulate async work (network call, computation, etc.)
                        delay(processingDelayMs)
                        // Each SideEffect modifies its own subset of keys
                        repeat(keyCount) { i -> currentItems["$keyPrefix-$i"] = i }
                        // Dispatch full map replacement — this is where the problem lies
                        dispatch(CollectionAction.UpdateItems(currentItems))
                        onCompleted.complete(Unit)
                    }
                }
            }
        }
    }

    @Test
    fun testConcurrentSideEffectsLoseMapUpdates() = runBlocking {
        val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
        val config = StateReserveConfig(scope = scope, debugMode = false)
        val stateReserve = StateReserve(
            initialState = InitialState.set(CollectionState()),
            reduce = reduce,
            config = config,
            middlewares = null
        )

        val completedA = CompletableDeferred<Unit>()
        val completedB = CompletableDeferred<Unit>()

        MapModifierSideEffect(stateReserve, "a", 5, 100, completedA)
        MapModifierSideEffect(stateReserve, "b", 5, 100, completedB)

        // Ensure SideEffects have subscribed to actionStates (SharedFlow has no replay)
        delay(50)

        stateReserve.dispatch(CollectionAction.TriggerProcessing)

        withTimeout(5_000) {
            completedA.await()
            completedB.await()
        }

        val finalState = stateReserve.awaitState()

        val aKeys = finalState.items.keys.filter { it.startsWith("a-") }
        val bKeys = finalState.items.keys.filter { it.startsWith("b-") }

        // Documents the lost-update bug: only one SideEffect's changes survive
        // because the raw reducer replaces the entire map each time (last writer wins).
        // The fix is to use buildReducer with `put` (delta) or `merge` (three-way merge) handlers.
        assertTrue(
            finalState.items.size < 10,
            "Expected fewer than 10 entries due to lost-update bug, but got ${finalState.items.size}."
        )
        assertTrue(
            aKeys.size == 5 || bKeys.size == 5,
            "Expected at least one SideEffect's full set of 5 keys. A: ${aKeys.size}, B: ${bKeys.size}."
        )

        scope.cancel()
    }
}
