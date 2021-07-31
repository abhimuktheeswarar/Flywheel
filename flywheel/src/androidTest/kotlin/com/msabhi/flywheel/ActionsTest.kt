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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import kotlin.test.assertEquals

@Suppress("EXPERIMENTAL_API_USAGE")
class ActionsTest {

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

    private val modifyingMiddleware: Middleware<TestCounterState> = { dispatch, getState ->

        { next ->

            { action ->

                when (action) {
                    is TestCounterAction.ForceUpdateAction -> {
                        next(action.copy(action.count + 1))
                    }
                    else -> next(action)
                }
            }
        }
    }

    private val dispatchingMiddleware: Middleware<TestCounterState> = { dispatch, getState ->

        { next ->

            { action ->

                when (action) {
                    is TestCounterAction.DecrementAction -> {
                        dispatch(TestCounterAction.IncrementAction)
                    }
                    else -> next(action)
                }
            }
        }
    }

    private val skipMiddleware: Middleware<State> = { _, _ ->
        { next ->

            {
                if (it !is SkipReducer) {
                    next(it)
                }
            }
        }
    }


    private fun stateReserve(): StateReserve<TestCounterState> {
        val config =
            StateReserveConfig(
                scope = TestCoroutineScope(),
                debugMode = false)
        return StateReserve(initialState = InitialState.set(TestCounterState()),
            reduce = reduce,
            config = config,
            middlewares = listOf(plainMiddleware,
                modifyingMiddleware,
                dispatchingMiddleware,
                skipMiddleware))
    }

    object SkipAction : SkipReducer

    @Test
    fun testActionsOrder() = runBlockingTest {
        val stateReserve = stateReserve()
        val expectedActions = listOf(
            TestCounterAction.IncrementAction,
            TestCounterAction.IncrementAction,
            TestCounterAction.ForceUpdateAction(3),
            TestCounterAction.ForceUpdateAction(4),
            TestCounterAction.DecrementAction,
            TestCounterAction.IncrementAction,
            SkipAction)
        val actions = arrayListOf<Action>()
        val job = launch {
            stateReserve.actions.collect {
                actions.add(it)
            }
        }
        stateReserve.dispatch(TestCounterAction.IncrementAction)
        stateReserve.dispatch(TestCounterAction.IncrementAction)
        assertEquals(2, stateReserve.state().count)
        stateReserve.dispatch(TestCounterAction.ForceUpdateAction(3))
        assertEquals(4, stateReserve.state().count)
        stateReserve.dispatch(TestCounterAction.DecrementAction)
        assertEquals(5, stateReserve.state().count)
        stateReserve.dispatch(SkipAction)

        job.cancel()

        assertEquals(expectedActions, actions.toList())
    }

    @Test
    fun repeatSameActions() = runBlockingTest {
        val stateReserve = stateReserve()
        val expectedActions = listOf(
            TestCounterAction.IncrementAction,
            TestCounterAction.IncrementAction,
            TestCounterAction.IncrementAction,
            TestCounterAction.IncrementAction,
            TestCounterAction.IncrementAction)
        val actions = arrayListOf<Action>()
        val job = launch {
            stateReserve.actions.collect {
                actions.add(it)
            }
        }
        stateReserve.dispatch(TestCounterAction.IncrementAction)
        stateReserve.dispatch(TestCounterAction.IncrementAction)
        stateReserve.dispatch(TestCounterAction.IncrementAction)
        stateReserve.dispatch(TestCounterAction.IncrementAction)
        stateReserve.dispatch(TestCounterAction.IncrementAction)

        assertEquals(5, stateReserve.state().count)

        job.cancel()

        assertEquals(expectedActions, actions.toList())
    }
}