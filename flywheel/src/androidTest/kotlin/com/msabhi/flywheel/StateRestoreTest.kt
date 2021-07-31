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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@Suppress("EXPERIMENTAL_API_USAGE")
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
    fun testStateReserveWithInitialStateSet() = runBlockingTest {

        val stateReserve =
            stateReserve(TestCoroutineScope(), InitialState.set(TestCounterState(1)))

        stateReserve.dispatch(TestCounterAction.IncrementAction)
        assertEquals(2, stateReserve.awaitState().count)

        assertNotNull(runCatching {
            stateReserve.restoreState(
                TestCounterState())
        }.exceptionOrNull())
    }

    @Test
    fun testStateReserveWithDeferredState() = runBlockingTest {

        val scope = TestCoroutineScope()
        val stateReserve = stateReserve(scope, InitialState.deferredSet())

        var actionsCount = 0
        val actionsJob = launch { stateReserve.actions.collect { actionsCount++ } }

        var actionStatesCount = 0
        val actionStatesJob = launch { stateReserve.actionStates.collect { actionStatesCount++ } }

        var state: TestCounterState? = null
        val stateJob = launch { state = stateReserve.awaitState() }

        stateReserve.dispatch(TestCounterAction.IncrementAction)
        stateReserve.dispatch(TestCounterAction.IncrementAction)

        assertEquals(2, actionsCount)
        assertEquals(0, actionStatesCount)

        assertNull(state)

        stateReserve.restoreState(TestCounterState(1))
        stateReserve.dispatch(TestCounterAction.IncrementAction)
        assertEquals(4, stateReserve.awaitState().count)

        assertEquals(3, actionsCount)
        assertEquals(3, actionStatesCount)

        assertNotNull(state)

        assertNotNull(runCatching {
            stateReserve.restoreState(
                TestCounterState())
        }.exceptionOrNull())

        actionsJob.cancel()
        actionStatesJob.cancel()
        stateJob.cancel()
    }
}