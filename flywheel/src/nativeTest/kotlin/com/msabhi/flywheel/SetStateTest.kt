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
import com.msabhi.flywheel.base.BaseTest
import com.msabhi.flywheel.common.CollectionAction
import com.msabhi.flywheel.common.CollectionState
import com.msabhi.flywheel.common.TestCounterAction
import com.msabhi.flywheel.common.TestCounterState
import kotlinx.coroutines.*
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Tests for [StateReserve.setState] — transform-carrying actions applied atomically
 * against the current state on the state-machine coroutine.
 *
 * The concurrent scenario mirrors [ConcurrentStateModificationTest], which documents
 * the lost-update bug of full-map replacement. With setState, each SideEffect
 * dispatches the *operation* (merge my delta) instead of a snapshot-derived value,
 * so both SideEffects' changes survive regardless of interleaving.
 */
class SetStateTest : BaseTest() {

    private val counterReduce: Reduce<TestCounterState> = { action, state ->
        when (action) {
            is TestCounterAction.IncrementAction -> state.copy(count = state.count + 1)
            else -> state
        }
    }

    private fun counterStateReserve(scope: CoroutineScope): StateReserve<TestCounterState> =
        StateReserve(
            initialState = InitialState.set(TestCounterState()),
            reduce = counterReduce,
            config = StateReserveConfig(scope = scope, debugMode = false),
            middlewares = null
        )

    internal class MapMergingSideEffect(
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
                        // Both SideEffects see the SAME state snapshot here (empty map),
                        // exactly like the lost-update scenario.
                        delay(processingDelayMs)
                        // Heavy computation happens here, off the state machine.
                        val delta = buildMap { repeat(keyCount) { i -> put("$keyPrefix-$i", i) } }
                        // Only the cheap merge runs on the state machine, against the CURRENT state.
                        setState("merge-$keyPrefix") { copy(items = items + delta) }
                        onCompleted.complete(Unit)
                    }
                }
            }
        }
    }

    @Test
    fun testConcurrentSetStateConvergesToAllEntries() = runTest {
        val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
        val config = StateReserveConfig(scope = scope, debugMode = false)
        val stateReserve = StateReserve(
            initialState = InitialState.set(CollectionState()),
            reduce = { _, state: CollectionState -> state },
            config = config,
            middlewares = null
        )

        val completedA = CompletableDeferred<Unit>()
        val completedB = CompletableDeferred<Unit>()

        MapMergingSideEffect(stateReserve, "a", 5, 100, completedA)
        MapMergingSideEffect(stateReserve, "b", 5, 100, completedB)

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

        assertEquals(
            10, finalState.items.size,
            "Expected all 10 entries to survive with setState, but got ${finalState.items.size}."
        )
        assertEquals(5, aKeys.size, "All of SideEffect A's keys should survive.")
        assertEquals(5, bKeys.size, "All of SideEffect B's keys should survive.")

        scope.cancel()
    }

    @Test
    fun testSetStateAndDispatchAreFifoOrdered() = runTest {
        val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
        val stateReserve = counterStateReserve(scope)

        stateReserve.dispatch(TestCounterAction.IncrementAction)
        stateReserve.setState("double") { copy(count = count * 2) }
        stateReserve.dispatch(TestCounterAction.IncrementAction)

        // (0 + 1) * 2 + 1 = 3 only if the transform runs in FIFO order with dispatches
        assertEquals(3, stateReserve.awaitState().count)

        scope.cancel()
    }

    @Test
    fun testThrowingSetStateLeavesStateUnchangedAndMachineAlive() = runTest {
        val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
        val stateReserve = counterStateReserve(scope)

        stateReserve.setState("boom") { throw IllegalStateException("boom") }
        stateReserve.dispatch(TestCounterAction.IncrementAction)

        // The throwing transform is swallowed (same contract as a throwing reducer)
        // and the state machine keeps processing subsequent actions.
        assertEquals(1, stateReserve.awaitState().count)

        scope.cancel()
    }

    @Test
    fun testSetStateEmitsInActionsAndActionStatesOnce() = runTest {
        val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
        val stateReserve = counterStateReserve(scope)

        val actionsSeen = mutableListOf<Action>()
        val actionStatesSeen = mutableListOf<Action>()
        val actionsJob = scope.launch { stateReserve.actions.collect { actionsSeen.add(it) } }
        val actionStatesJob =
            scope.launch { stateReserve.actionStates.collect { actionStatesSeen.add(it.action) } }

        // Ensure collectors have subscribed (SharedFlow has no replay)
        delay(50)

        stateReserve.setState("first") { copy(count = count + 1) }
        stateReserve.setState("second") { copy(count = count + 1) }

        assertEquals(2, stateReserve.awaitState().count)
        delay(50)

        actionsJob.cancelAndJoin()
        actionStatesJob.cancelAndJoin()

        assertEquals(
            2, actionsSeen.filterIsInstance<SetStateAction<*>>().size,
            "Each setState call should appear exactly once in the actions flow."
        )
        assertEquals(
            2, actionStatesSeen.filterIsInstance<SetStateAction<*>>().size,
            "Each setState call should appear exactly once in the actionStates flow."
        )

        scope.cancel()
    }
}
