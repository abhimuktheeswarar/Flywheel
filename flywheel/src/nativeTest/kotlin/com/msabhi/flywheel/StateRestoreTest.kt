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

import com.msabhi.flywheel.common.TestCounterAction
import com.msabhi.flywheel.common.TestCounterState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.yield
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class StateRestoreTest {

    private val reduce: Reduce<TestCounterState> = { action, state ->
        when (action) {
            is TestCounterAction.IncrementAction -> state.copy(count = state.count + 1)
            is TestCounterAction.DecrementAction -> state.copy(count = state.count - 1)
            is TestCounterAction.ForceUpdateAction -> state.copy(count = action.count)
            else -> state
        }
    }

    private fun stateReserve(
        scope: CoroutineScope,
        initialState: InitialState<TestCounterState>,
    ): StateReserve<TestCounterState> {
        val config =
            StateReserveConfig(
                scope = scope,
                debugMode = false)
        return StateReserve(initialState = initialState,
            reduce = reduce,
            config = config,
            middlewares = null)
    }

    @Test
    fun testStateReserveWithInitialStateSet() = runTest {
        val scope = CoroutineScope(coroutineContext + SupervisorJob())
        val stateReserve = stateReserve(scope, InitialState.set(TestCounterState(1)))

        stateReserve.dispatch(TestCounterAction.IncrementAction)
        yield()
        assertEquals(2, stateReserve.awaitState().count)

        assertTrue(runCatching { stateReserve.restoreState(TestCounterState()) }.exceptionOrNull() != null)
        scope.cancel()
    }

    @Test
    fun testStateReserveWithDeferredState() = runTest {
        val scope = CoroutineScope(coroutineContext + SupervisorJob())
        val stateReserve = stateReserve(scope, InitialState.deferredSet())

        var actionsCount = 0
        val actionsJob = launch(start = CoroutineStart.UNDISPATCHED) {
            stateReserve.actions.collect { actionsCount++ }
        }

        var actionStatesCount = 0
        val actionStatesJob = launch(start = CoroutineStart.UNDISPATCHED) {
            stateReserve.actionStates.collect { actionStatesCount++ }
        }

        var state: TestCounterState? = null
        val stateJob = launch(start = CoroutineStart.UNDISPATCHED) {
            state = stateReserve.awaitState()
        }

        stateReserve.dispatch(TestCounterAction.IncrementAction)
        stateReserve.dispatch(TestCounterAction.IncrementAction)

        // Allow pending dispatches to be processed
        delay(10)

        assertEquals(2, actionsCount)
        assertEquals(0, actionStatesCount)
        assertNull(state)

        stateReserve.restoreState(TestCounterState(1))
        stateReserve.dispatch(TestCounterAction.IncrementAction)

        yield()
        assertEquals(4, stateReserve.awaitState().count)

        // Allow collectors to receive all emissions
        delay(10)

        assertEquals(3, actionsCount)
        assertEquals(3, actionStatesCount)
        assertNotNull(state)

        assertNotNull(runCatching {
            stateReserve.restoreState(TestCounterState())
        }.exceptionOrNull())

        actionsJob.cancel()
        actionStatesJob.cancel()
        stateJob.cancel()
        scope.cancel()
    }
}
