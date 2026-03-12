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
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Verifies that the delta-dispatch pattern preserves all changes from concurrent SideEffects.
 *
 * Each SideEffect dispatches only the delta (new entries to add), and the reducer merges
 * them into the current map with `state.items + action.entries`. Since the reducer always
 * reads the current state, concurrent dispatches don't overwrite each other.
 *
 * Compare with [ConcurrentStateModificationTest] which demonstrates the bug when
 * SideEffects dispatch full collection replacements instead of deltas.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class ConcurrentDeltaDispatchTest {

    private val reduce = reducerForAction<CollectionAction, CollectionState> { action, state ->
        when (action) {
            is CollectionAction.PutItems -> state.copy(items = state.items + action.entries)
            else -> state
        }
    }

    internal class DeltaMapSideEffect(
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
                        delay(processingDelayMs)
                        val delta = (0 until keyCount).associate { "$keyPrefix-$it" to it }
                        dispatch(CollectionAction.PutItems(delta))
                        onCompleted.complete(Unit)
                    }
                }
            }
        }
    }

    @Test
    fun concurrentDeltaDispatchPreservesAllChanges() = runTest(UnconfinedTestDispatcher()) {
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

        DeltaMapSideEffect(stateReserve, "a", 5, 100, completedA)
        DeltaMapSideEffect(stateReserve, "b", 5, 100, completedB)

        stateReserve.dispatch(CollectionAction.TriggerProcessing)

        scope.testScheduler.apply { advanceTimeBy(200); runCurrent() }

        assertTrue(completedA.isCompleted, "SideEffect A should have completed")
        assertTrue(completedB.isCompleted, "SideEffect B should have completed")

        val finalState = stateReserve.awaitState()

        val aKeys = finalState.items.keys.filter { it.startsWith("a-") }
        val bKeys = finalState.items.keys.filter { it.startsWith("b-") }

        assertEquals(
            10, finalState.items.size,
            "Expected 10 entries (5 from A + 5 from B) but got ${finalState.items.size}. " +
                "A keys: ${aKeys.size}, B keys: ${bKeys.size}."
        )
        assertEquals(5, aKeys.size)
        assertEquals(5, bKeys.size)

        scope.cancel()
    }
}
