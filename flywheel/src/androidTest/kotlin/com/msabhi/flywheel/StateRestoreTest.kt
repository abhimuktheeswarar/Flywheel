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
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import kotlin.test.Test
import kotlin.test.assertEquals

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

    private fun stateReserve(scope: CoroutineScope): StateReserve<TestCounterState> {
        val config =
            StateReserveConfig(
                scope = scope,
                debugMode = true)
        return StateReserve(initialState = TestCounterState(count = 1),
            reduce = reduce,
            config = config,
            middlewares = null)
    }

    @Test
    fun testStateReserveRestoreFeature() = runBlockingTest {
        val stateReserve = stateReserve(TestCoroutineScope())
        stateReserve.dispatch(TestCounterAction.IncrementAction)
        assertEquals(2, stateReserve.awaitState().count)
        stateReserve.restoreState(TestCounterState(10))
        assertEquals(10, stateReserve.awaitState().count)
        stateReserve.dispatch(TestCounterAction.IncrementAction)
        assertEquals(11, stateReserve.awaitState().count)
        stateReserve.restoreState(TestCounterState(20))
        assertEquals(20, stateReserve.awaitState().count)
    }
}