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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.flow.toList
import kotlin.test.Test
import kotlin.test.assertEquals

class ColdSideEffectReplayTest {

    @Test
    fun replayTest() = runBlocking {
        repeat(1) {
            singleReplayTestIteration(N = 200, subscribers = 1)
        }
    }

    /*@Test
    fun replayLargeTest() = runBlocking {
        singleReplayTestIteration(N = 100_000, subscribers = 10)
    }*/

    /**
     * Tests consistency of produced flow. E.g. for just increment reducer output must be
     * 1,2,3,4,5
     * not 1,3,4,5 (value missing)
     * or 4,3,4,5 (incorrect order)
     * or 3,3,4,5 (duplicate value)
     */
    private suspend fun singleReplayTestIteration(N: Int, subscribers: Int) =
        withContext(Dispatchers.Default) {
            val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
            val reduce: Reduce<TestCounterState> = { action, state ->
                when (action) {
                    is TestCounterAction.IncrementAction -> state.copy(count = state.count + 1)
                    else -> state
                }
            }
            val config =
                StateReserveConfig(
                    scope = scope,
                    debugMode = false)
            val stateReserve =
                StateReserve(initialState = TestCounterState(),
                    reduce = reduce,
                    config = config,
                    middlewares = null)

            launch {
                repeat(N) {
                    stateReserve.dispatch(TestCounterAction.IncrementAction)
                }
            }

            // One more scope for subscribers, to ensure subscribers are finished before cancelling StateReserve scope
            coroutineScope {
                repeat(subscribers) {
                    launch {
                        // Since only increase by 1 reducers are applied
                        // it's expected to see monotonously increasing sequence with no missing values
                        stateReserve.coldActions.map { stateReserve.awaitState() }
                            .takeWhile { it.count < N }.toList().zipWithNext { a, b ->
                                assertEquals(a.count + 1, b.count)
                            }
                    }
                }
            }
            scope.cancel()
        }


    @Suppress("DeferredResultUnused")
    @Test
    fun testProperCancellation() = runBlocking {
        val scope = CoroutineScope(Dispatchers.Default + Job())
        val reduce: Reduce<TestCounterState> = { action, state ->
            when (action) {
                is TestCounterAction.IncrementAction -> state.copy(count = state.count + 1)
                else -> state
            }
        }
        val config =
            StateReserveConfig(
                scope = scope,
                debugMode = false)
        val stateReserve =
            StateReserve(initialState = TestCounterState(),
                reduce = reduce,
                config = config,
                middlewares = null)

        val collectJob = async(start = CoroutineStart.UNDISPATCHED) {
            stateReserve.coldActions.map { stateReserve.state() }.collect {
                delay(Long.MAX_VALUE)
            }
        }
        collectJob.cancel()

        val n = 200
        coroutineScope {
            async(start = CoroutineStart.UNDISPATCHED) {
                stateReserve.coldActions.map { stateReserve.state() }.takeWhile { it.count < n }
                    .collect {
                        // no-op
                    }
            }
            async {
                repeat(n) {
                    stateReserve.dispatch(TestCounterAction.IncrementAction)
                }
            }
        }
        scope.cancel()
    }
}