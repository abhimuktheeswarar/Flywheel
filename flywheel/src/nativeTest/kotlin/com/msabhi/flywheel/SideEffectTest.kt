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
import kotlinx.coroutines.*
import kotlin.test.Test
import kotlin.test.assertEquals

class SideEffectTest {

    internal companion object {
        const val N = 10
        const val DELAY_MS = 1L
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
                    delay(DELAY_MS)
                    count++
                }
            }
        }
    }

    @Test
    fun loadTest() = runBlocking {
        withContext(Dispatchers.Default) {
            val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
            val stateReserve = stateReserve(scope)

            val sideEffects = mutableSetOf<CounterSideEffect>()
            repeat(N) {
                sideEffects.add(CounterSideEffect(stateReserve, DispatcherProviderImpl))
            }

            // Allow side effect collector coroutines to start subscribing before actions arrive.
            // transitionsMutable has no replay, so late subscribers miss emissions.
            delay(50)

            var count = 0
            // UNDISPATCHED ensures job0 subscribes immediately before job1 is even scheduled.
            val job0 = launch(start = CoroutineStart.UNDISPATCHED) {
                stateReserve.actionStates.collect { count++ }
            }
            val job1 = launch { repeat(N) { stateReserve.dispatch(TestCounterAction.IncrementAction) } }

            // Wait until all N increments have been reduced
            withTimeout(5_000) {
                while (stateReserve.state().count < N) {
                    delay(1)
                }
            }

            assertEquals(N, stateReserve.state().count)
            assertEquals(N, count)

            // Wait until all side effects have processed all N actions
            withTimeout(5_000) {
                while (sideEffects.any { it.count < N }) {
                    delay(1)
                }
            }

            sideEffects.forEach { assertEquals(N, it.count) }

            job0.cancel()
            job1.cancel()
            scope.cancel()
        }
    }
}
