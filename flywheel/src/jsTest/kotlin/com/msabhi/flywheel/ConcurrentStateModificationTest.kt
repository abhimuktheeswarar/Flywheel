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
import kotlinx.coroutines.test.*
import kotlin.test.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
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
                        val currentItems = actionState.state.items.toMutableMap()
                        delay(processingDelayMs)
                        repeat(keyCount) { i -> currentItems["$keyPrefix-$i"] = i }
                        dispatch(CollectionAction.UpdateItems(currentItems))
                        onCompleted.complete(Unit)
                    }
                }
            }
        }
    }

    @Test
    fun testConcurrentSideEffectsLoseMapUpdates() = runTest(UnconfinedTestDispatcher()) {
        val scope = TestScope(UnconfinedTestDispatcher())
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

        stateReserve.dispatch(CollectionAction.TriggerProcessing)

        // Advance virtual time past the SideEffects' processing delay
        scope.testScheduler.apply { advanceTimeBy(200); runCurrent() }

        assertTrue(completedA.isCompleted, "SideEffect A should have completed")
        assertTrue(completedB.isCompleted, "SideEffect B should have completed")

        val finalState = stateReserve.awaitState()

        val aKeys = finalState.items.keys.filter { it.startsWith("a-") }
        val bKeys = finalState.items.keys.filter { it.startsWith("b-") }

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
