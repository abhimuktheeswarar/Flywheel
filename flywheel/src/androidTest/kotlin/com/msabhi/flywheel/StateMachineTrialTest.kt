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

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@Suppress("EXPERIMENTAL_API_USAGE")
class StateMachineTrialTest {


    sealed interface TestAction : Action {
        object Load : TestAction
        object Loaded : TestAction
        object Error : TestAction
    }

    sealed interface TestState : State {

        object Initial : TestState
        object Loading : TestState
        object Loaded : TestState
        object Error : TestState
    }

    data class OneState(val count: Int = 0) : State

    @Test
    fun transition() = runBlockingTest {

        val transitions = MutableSharedFlow<Transition<TestAction, TestState>>()


        val job1 = launch {
            transitions.filterIsInstance<Transition.Valid<TestAction.Load, TestState.Initial, TestState.Loading>>()
                .collect {
                    println("1 FILTERED: $it")
                }
        }

        val job2 = launch {
            transitions.filterValidTransition<TestAction.Load, TestState.Initial, TestState.Loading>()
                .collect {
                    println("2 FILTERED: $it")
                }
        }

        transitions.emit(Transition.Valid(TestAction.Load, TestState.Initial, TestState.Loading))
        transitions.emit(Transition.Valid(TestAction.Error, TestState.Loading, TestState.Error))

        job1.cancel()
        job2.cancel()
    }

    internal class SimpleReserve(
        initialState: CompletableDeferred<TestState>,
        scope: CoroutineScope,
    ) {

        val mutableSharedFlow = MutableSharedFlow<TestState>(
            replay = 1,
            extraBufferCapacity = Int.MAX_VALUE,
            onBufferOverflow = BufferOverflow.SUSPEND,
        )

        init {

            scope.launch {
                val initialState1 = initialState.await()
                mutableSharedFlow.tryEmit(initialState1)
            }
        }
    }

    @Test
    fun initialStateTest() = runBlockingTest {

        val scope = CoroutineScope(SupervisorJob())
        val initialState = CompletableDeferred<TestState>()
        val reserve = SimpleReserve(initialState, scope)
        initialState.complete(TestState.Initial)

        val job = launch {

            reserve.mutableSharedFlow.collect {
                println(it)
            }
        }

        reserve.mutableSharedFlow.emit(TestState.Loading)

        job.cancel()
    }

    @Test
    fun instanceTest() = runBlockingTest {

        val oneState0 = OneState(0)
        val oneState1 = OneState(1)

        if (oneState0::class.isInstance(oneState1)) {
            println("true")
        } else {
            println("false")
        }

        val testState0: TestState = TestState.Initial
        val testState1: TestState = TestState.Initial
        val testState2: TestState = TestState.Loading

        if (testState0::class.isInstance(testState1)) {
            println("true")
        } else {
            println("false")
        }

        if (testState1::class.isInstance(testState2)) {
            println("true")
        } else {
            println("false")
        }

    }
}


@Suppress("UNCHECKED_CAST")
inline fun <reified A : Action, reified FS : State, reified TS : State> Flow<Transition<*, *>>.filterValidTransition(): Flow<Transition.Valid<A, FS, TS>> =
    filter { it.action is A && it.fromState is FS && (it is Transition.Valid<*, *, *> && it.toState is TS) } as Flow<Transition.Valid<A, FS, TS>>


typealias Reduce2<S> = S.(action: Action) -> S
