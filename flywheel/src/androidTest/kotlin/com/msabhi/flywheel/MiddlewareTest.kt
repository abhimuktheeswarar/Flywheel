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
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.Test
import kotlin.test.assertEquals

@Suppress("EXPERIMENTAL_API_USAGE")
class MiddlewareTest {

    private val reduce: Reduce<TestCounterState> = { action, state ->
        when (action) {
            is TestCounterAction.IncrementAction -> state.copy(count = state.count + 1)
            is TestCounterAction.DecrementAction -> state.copy(count = state.count - 1)
            is TestCounterAction.ForceUpdateAction -> state.copy(count = action.count)
            is TestCounterAction.ResetAction -> state.copy(count = 0)
            else -> state
        }
    }

    private val plainMiddleware: Middleware<TestCounterState> = { dispatch, getState ->

        { next ->

            { action ->

                next(action)
            }
        }
    }

    private val dispatchingMiddleware: Middleware<TestCounterState> = { dispatch, getState ->

        { next ->

            { action ->

                when (action) {
                    is TestCounterAction.IncrementAction -> {
                        dispatch(TestCounterAction.ForceUpdateAction(5))
                    }
                    is TestCounterAction.DecrementAction -> {
                        dispatch(TestCounterAction.IncrementAction)
                    }
                    else -> next(action)
                }
            }
        }
    }

    private fun stateReserve(): StateReserve<TestCounterState> {
        val config =
            StateReserveConfig(
                scope = TestCoroutineScope(),
                debugMode = false)
        return StateReserve(initialState = InitialState.set(TestCounterState(1)),
            reduce = reduce,
            config = config,
            middlewares = listOf(plainMiddleware, dispatchingMiddleware))
    }

    @Test
    fun testMiddlewareBehaviour() = runBlocking {
        val stateReserve = stateReserve()
        stateReserve.dispatch(TestCounterAction.IncrementAction)
        stateReserve.dispatch(TestCounterAction.DecrementAction)
        assertEquals(5, stateReserve.state().count)
        stateReserve.dispatch(TestCounterAction.ResetAction)
        assertEquals(0, stateReserve.state().count)
    }
}