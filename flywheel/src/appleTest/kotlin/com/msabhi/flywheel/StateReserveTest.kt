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

import com.msabhi.flywheel.base.BaseTest
import com.msabhi.flywheel.common.TestCounterAction
import com.msabhi.flywheel.common.TestCounterState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class StateReserveTest : BaseTest() {

    private val reduce: Reduce<TestCounterState> = { action, state ->
        when (action) {
            is TestCounterAction.IncrementAction -> state.copy(count = state.count + 1)
            is TestCounterAction.DecrementAction -> state.copy(count = state.count - 1)
            is TestCounterAction.ForceUpdateAction -> state.copy(count = action.count)
            else -> state
        }
    }

    private fun stateReserve(
        reduce: Reduce<TestCounterState> = this.reduce,
        scope: CoroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob()),
    ): StateReserve<TestCounterState> {
        val config =
            StateReserveConfig(
                scope = scope,
                debugMode = false)
        return StateReserve(initialState = InitialState.set(TestCounterState(1)),
            reduce = reduce,
            config = config,
            middlewares = null)
    }

    @Test
    fun testGetRunsSynchronouslyForTests() = runTest {
        println("start")
        var callCount = 0
        val reduce: Reduce<TestCounterState> = { _, state ->

            callCount++
            state
        }
        val stateReserve = stateReserve(reduce, this)
        stateReserve.dispatch(TestCounterAction.IncrementAction)
        stateReserve.awaitState()
        println("end")
        assertEquals(1, callCount)
    }

    @Test
    fun testSetState() = runTest {
        var called = false
        val reduce: Reduce<TestCounterState> = { action, state ->
            assertEquals(2, (action as TestCounterAction.ForceUpdateAction).count)
            called = true
            state
        }
        val stateReserve = stateReserve(reduce, this)
        stateReserve.dispatch(TestCounterAction.ForceUpdateAction(2))
        stateReserve.awaitState()
        assertTrue(called)
    }
}
