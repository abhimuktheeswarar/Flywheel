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

import com.msabhi.flywheel.attachments.DispatcherProvider
import com.msabhi.flywheel.attachments.DispatcherProviderImpl
import com.msabhi.flywheel.attachments.SideEffect
import com.msabhi.flywheel.common.TestCounterAction
import com.msabhi.flywheel.common.TestCounterState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import kotlin.test.assertEquals

@Suppress("EXPERIMENTAL_API_USAGE")
class SideEffectTest {

    internal companion object {

        const val N = 1000
    }

    private val reduce: Reduce<TestCounterState> = { action, state ->
        when (action) {
            is TestCounterAction.IncrementAction -> state.copy(count = state.count + 1)
            else -> state
        }
    }

    private fun stateReserve(
        scope: CoroutineScope,
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

    internal class CounterSideEffect(
        stateReserve: StateReserve<TestCounterState>,
        dispatchers: DispatcherProvider,
    ) : SideEffect<TestCounterState>(stateReserve, dispatchers) {

        var count = 0

        init {

            scope.launch {
                actionStates.collect {
                    delay(N.toLong())
                    count++
                }
            }
        }
    }

    @Test(timeout = 15000)
    fun loadTest() = runBlockingTest {

        val scope = TestCoroutineScope()
        val stateReserve = stateReserve(scope)

        val sideEffects = mutableSetOf<CounterSideEffect>()

        repeat(N) {
            sideEffects.add(CounterSideEffect(stateReserve, DispatcherProviderImpl))
        }

        var count = 0

        val job0 = launch {
            stateReserve.actionStates.collect {
                count++
            }
        }

        val job1 = launch {
            repeat(N) {
                stateReserve.dispatch(TestCounterAction.IncrementAction)
            }
        }

        repeat(N) {
            scope.advanceTimeBy(N.toLong())
        }

        assertEquals(N, stateReserve.awaitState().count)

        assertEquals(N, count)

        sideEffects.forEach { assertEquals(N, it.count) }

        job0.cancel()
        job1.cancel()
    }
}