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
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

@Suppress("EXPERIMENTAL_API_USAGE")
class TrialTest {

    @Volatile
    var sharedCounter = 0

    @Before
    fun setup() {
        sharedCounter = 0
    }

    @Test
    fun flowBehaviourTest() = runBlockingTest {
        val mutableSharedFlow = MutableSharedFlow<Int>(
            replay = 1,
            onBufferOverflow = BufferOverflow.SUSPEND)

        repeat(10) {
            mutableSharedFlow.emit(it)
        }
        val items = mutableSetOf<Int>()

        val job = launch {
            mutableSharedFlow.collect {
                items.add(it)
            }
        }
        job.cancel()

        assertEquals(1, items.size)
    }

    @Test
    fun comparisonTest() {
        val a1 = TestCounterAction.IncrementAction
        val a2 = TestCounterAction.IncrementAction
        assertTrue { a1 === a2 }
        val b1 = TestCounterAction.ForceUpdateAction(1)
        val b2 = TestCounterAction.ForceUpdateAction(1)
        assertFalse { b1 === b2 }
    }


    @Test
    fun failingCounterTest() = runBlocking {

        val scope = CoroutineScope(newFixedThreadPoolContext(4,
            "synchronizationPool") + SupervisorJob()) // We want our code to run on 4 threads
        scope.launch {
            val coroutines = 1.rangeTo(1000).map { //create 1000 coroutines (light-weight threads).
                launch {
                    for (i in 1..1000) { // and in each of them, increment the sharedCounter 1000 times.
                        sharedCounter++
                    }
                }
            }

            coroutines.forEach { corotuine ->
                corotuine.join() // wait for all coroutines to finish their jobs.
            }
        }.join()

        println("The number of shared counter should be 1000000, but actually is $sharedCounter")
        assertNotEquals(1000000, sharedCounter)
    }

    //val i : GetInitialState<TestCounterState>  = suspend { TestCounterState(count = 0) }

    @Test
    fun workingCounterTest() = runBlocking {
        val scope = CoroutineScope(newFixedThreadPoolContext(4,
            "synchronizationPool") + SupervisorJob()) // We want our code to run on 4 threads
        val config =
            StateReserveConfig(
                scope = scope,
                debugMode = false)

        val reduce: Reduce<TestCounterState> = { action, state ->
            when (action) {
                is TestCounterAction.IncrementAction -> state.copy(count = state.count + 1)
                else -> state
            }
        }

        val completableDeferred = CompletableDeferred<TestCounterState>()

        val stateReserve = StateReserve(
            initialState = InitialState.set(TestCounterState(9)),
            reduce = reduce,
            config = config,
            middlewares = null)


        val j0 = scope.launch {
            val coroutines = 1.rangeTo(1000).map { //create 1000 coroutines (light-weight threads).
                launch {
                    for (i in 1..1000) { // and in each of them, increment the sharedCounter 1000 times.
                        stateReserve.dispatch(TestCounterAction.IncrementAction)
                    }
                }
            }

            coroutines.forEach { corotuine ->
                corotuine.join() // wait for all coroutines to finish their jobs.
            }
        }

        scope.launch {
            delay(1000)
            completableDeferred.complete(TestCounterState())
        }

        j0.join()

        val sharedCounter = stateReserve.awaitState().count
        println("The number of shared counter should be 1000000, but actually is $sharedCounter")
        assertEquals(1000000, sharedCounter)
    }
}