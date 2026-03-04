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

class HotActionsTest {

    private val reduce: Reduce<TestCounterState> = { action, state ->
        when (action) {
            is TestCounterAction.IncrementAction -> state.copy(count = state.count + 1)
            is TestCounterAction.DecrementAction -> state.copy(count = state.count - 1)
            is TestCounterAction.ForceUpdateAction -> state.copy(count = action.count)
            is TestCounterAction.ResetAction -> state.copy(count = 0)
            else -> state
        }
    }

    private val plainMiddleware: Middleware<TestCounterState> = { _, _ ->
        { next -> { action -> next(action) } }
    }

    private val modifyingMiddleware: Middleware<TestCounterState> = { _, _ ->
        { next ->
            { action ->
                when (action) {
                    is TestCounterAction.ForceUpdateAction -> next(action.copy(action.count + 1))
                    else -> next(action)
                }
            }
        }
    }

    private val dispatchingMiddleware: Middleware<TestCounterState> = { dispatch, _ ->
        { next ->
            { action ->
                when (action) {
                    is TestCounterAction.DecrementAction -> dispatch(TestCounterAction.IncrementAction)
                    else -> next(action)
                }
            }
        }
    }

    private val skipMiddleware: Middleware<State> = { _, _ ->
        { next ->
            {
                if (it !is SkipReducer) next(it)
            }
        }
    }

    private fun stateReserve(scope: CoroutineScope): StateReserve<TestCounterState> {
        val config =
            StateReserveConfig(scope = scope, debugMode = false)
        return StateReserve(
            initialState = InitialState.set(TestCounterState(count = 0)),
            reduce = reduce,
            config = config,
            middlewares = listOf(plainMiddleware, modifyingMiddleware, dispatchingMiddleware, skipMiddleware)
        )
    }

    object SkipAction : SkipReducer

    @Test
    fun testHotActionsOrder() = runTest {
        val scope = CoroutineScope(coroutineContext + SupervisorJob())
        val stateReserve = stateReserve(scope)
        val expectedActions = listOf(
            TestCounterAction.IncrementAction,
            TestCounterAction.IncrementAction,
            TestCounterAction.ForceUpdateAction(3),
            TestCounterAction.ForceUpdateAction(4),
            TestCounterAction.DecrementAction,
            TestCounterAction.IncrementAction,
            SkipAction
        )
        val actions = arrayListOf<Action>()
        val job = launch(start = CoroutineStart.UNDISPATCHED) {
            stateReserve.actions.collect { actions.add(it) }
        }

        stateReserve.dispatch(TestCounterAction.IncrementAction)
        stateReserve.dispatch(TestCounterAction.IncrementAction)
        yield()
        assertEquals(2, stateReserve.awaitState().count)

        stateReserve.dispatch(TestCounterAction.ForceUpdateAction(3))
        yield()
        assertEquals(4, stateReserve.awaitState().count)

        stateReserve.dispatch(TestCounterAction.DecrementAction)
        yield()
        assertEquals(5, stateReserve.awaitState().count)

        stateReserve.dispatch(SkipAction)
        yield()
        stateReserve.awaitState()
        delay(10)

        job.cancel()
        scope.cancel()
        assertEquals(expectedActions, actions.toList())
    }

    @Test
    fun repeatSameActions() = runTest {
        val scope = CoroutineScope(coroutineContext + SupervisorJob())
        val stateReserve = stateReserve(scope)
        val expectedActions = listOf(
            TestCounterAction.IncrementAction,
            TestCounterAction.IncrementAction,
            TestCounterAction.IncrementAction,
            TestCounterAction.IncrementAction,
            TestCounterAction.IncrementAction
        )
        val actions = arrayListOf<Action>()
        val job = launch(start = CoroutineStart.UNDISPATCHED) {
            stateReserve.actions.collect { actions.add(it) }
        }

        stateReserve.dispatch(TestCounterAction.IncrementAction)
        stateReserve.dispatch(TestCounterAction.IncrementAction)
        stateReserve.dispatch(TestCounterAction.IncrementAction)
        stateReserve.dispatch(TestCounterAction.IncrementAction)
        stateReserve.dispatch(TestCounterAction.IncrementAction)
        yield()
        assertEquals(5, stateReserve.awaitState().count)
        delay(10)

        job.cancel()
        scope.cancel()
        assertEquals(expectedActions, actions.toList())
    }
}
