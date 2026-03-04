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
import kotlinx.coroutines.*
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert
import org.junit.Test
import java.util.concurrent.ConcurrentLinkedQueue

@OptIn(ExperimentalCoroutinesApi::class)
class SetStateWithStateOrderingTest {

    private fun stateReserve(
        scope: CoroutineScope,
        reduce: Reduce<TestCounterState>,
    ): StateReserve<TestCounterState> {
        val config =
            StateReserveConfig(
                scope = scope,
                debugMode = false)
        return StateReserve(initialState = InitialState.set(TestCounterState()),
            reduce = reduce,
            config = config,
            middlewares = null)
    }

    @Test
    fun test1() = runBlocking {
        val calls = mutableListOf<String>()
        val reduce: Reduce<TestCounterState> = { action, state ->
            when (action) {
                is TestCounterAction.IncrementAction -> {
                    calls += "s1"
                    state.copy(count = state.count + 1)
                }
                else -> state
            }
        }
        val stateReserve = stateReserve(TestScope(UnconfinedTestDispatcher()), reduce)
        stateReserve.dispatch(TestCounterAction.IncrementAction)
        launch {
            stateReserve.awaitState()
            calls += "w1"
        }
        assertMatches(calls, "s1", "w1")
    }

    @Test
    fun test2() = runBlocking {
        val calls = mutableListOf<String>()
        val reduce: Reduce<TestCounterState> = { action, state ->
            when (action) {
                is TestCounterAction.IncrementAction -> {
                    calls += "s1"
                    state.copy(count = state.count + 1)
                }
                else -> state
            }
        }
        val stateReserve = stateReserve(TestScope(UnconfinedTestDispatcher()), reduce)
        launch {
            stateReserve.awaitState()
            calls += "w1"
            stateReserve.dispatch(TestCounterAction.IncrementAction)
            launch {
                stateReserve.awaitState()
                calls += "w2"
            }
        }
        assertMatches(calls, "w1", "s1", "w2")
    }

    @Test
    fun test3() = runBlocking {
        val calls = mutableListOf<String>()
        val reduce: Reduce<TestCounterState> = { action, state ->
            when (action) {
                is TestCounterAction.IncrementAction -> {
                    calls += "s1"
                    state.copy(count = state.count + 1)
                }
                else -> state
            }
        }
        val stateReserve = stateReserve(TestScope(UnconfinedTestDispatcher()), reduce)
        launch {
            stateReserve.awaitState()
            calls += "w1"
            launch {
                stateReserve.awaitState()
                calls += "w2"
            }
            stateReserve.dispatch(TestCounterAction.IncrementAction)
        }
        assertMatches(calls, "w1", "s1", "w2")
    }

    @Test
    fun test4() = runBlocking {
        val calls = ConcurrentLinkedQueue<String>()
        val reduce: Reduce<TestCounterState> = { action, state ->
            when (action) {
                is TestCounterAction.IncrementAction -> {
                    calls += "s1"
                    state.copy(count = state.count + 1)
                }
                is TestCounterAction.DecrementAction -> {
                    calls += "s2"
                    state.copy(count = state.count - 1)
                }
                is TestCounterAction.ForceUpdateAction -> {
                    calls += "s3"
                    state.copy(count = action.count)
                }
                else -> state
            }
        }
        val stateReserve = stateReserve(TestScope(UnconfinedTestDispatcher()), reduce)
        launch {
            stateReserve.awaitState()
            calls += "w1"
            launch {
                calls += "w2"
            }
            stateReserve.dispatch(TestCounterAction.IncrementAction)
            stateReserve.dispatch(TestCounterAction.DecrementAction)
            val count = stateReserve.awaitState().count
            stateReserve.dispatch(TestCounterAction.ForceUpdateAction(count))
        }

        assertMatches(calls, "w1", "s1", "s2", "s3", "w2")
    }

    private suspend fun assertMatches(calls: Collection<String>, vararg expectedCalls: String) {
        while (calls.size != expectedCalls.size) {
            delay(1)
        }
        Assert.assertEquals(expectedCalls.toList(), calls.toList())
    }
}