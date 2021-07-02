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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test
import kotlin.test.assertEquals

@Suppress("EXPERIMENTAL_API_USAGE")
class StateReserveTest {

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
        scope: CoroutineScope = TestCoroutineScope(TestCoroutineDispatcher()),
    ): StateReserve<TestCounterState> {
        val config =
            StateReserveConfig(
                scope = scope,
                debugMode = false)
        return StateReserve(initialState = TestCounterState(count = 1),
            reduce = reduce,
            config = config,
            middlewares = null)
    }

    @Test
    fun testGetRunsSynchronouslyForTests() = runBlockingTest {
        var callCount = 0
        val reduce: Reduce<TestCounterState> = { action, state ->
            callCount++
            state
        }
        val stateReserve = stateReserve(reduce)
        stateReserve.dispatch(TestCounterAction.IncrementAction)
        Assert.assertEquals(1, callCount)
    }

    @Test
    fun testSetState() = runBlocking {
        var called = false
        val reduce: Reduce<TestCounterState> = { action, state ->
            Assert.assertEquals(2, (action as TestCounterAction.ForceUpdateAction).count)
            called = true
            state
        }
        val stateReserve = stateReserve(reduce)
        stateReserve.dispatch(TestCounterAction.ForceUpdateAction(2))
        Assert.assertTrue(called)
    }


    @Test
    fun testSubscribeNotCalledForSameValue() = runBlockingTest {
        val stateReserve = stateReserve()
        var callCount = 0
        val job = stateReserve.states.onEach {
            callCount++
        }.launchIn(this)
        assertEquals(1, callCount)
        stateReserve.dispatch(TestCounterAction.ForceUpdateAction(1))
        assertEquals(1, callCount)
        job.cancel()
    }

    @Test
    fun testBlockingReceiver() = runBlockingTest {
        val stateReserve = stateReserve()
        val values = mutableListOf<Int>()
        val job = launch {
            stateReserve.states.collect {
                values += it.count
                delay(10)
            }
        }

        (2..10).forEach {
            stateReserve.dispatch(TestCounterAction.ForceUpdateAction(it))
        }
        delay(100)
        job.cancel()
        Assert.assertEquals(listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), values)
    }
}
