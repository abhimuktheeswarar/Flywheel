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
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

@Suppress("EXPERIMENTAL_API_USAGE")
class PureReducerValidationTest {

    @get:Rule
    @Suppress("DEPRECATION")
    var thrown = ExpectedException.none()!!

    private fun <S : State> stateReserve(
        initialState: InitialState<S>,
        reduce: Reduce<S>,
    ): StateReserve<S> {
        val config =
            StateReserveConfig(
                scope = TestCoroutineScope(TestCoroutineDispatcher()),
                debugMode = true)
        return StateReserve(
            initialState = initialState,
            reduce = reduce,
            config = config,
            middlewares = null)
    }

    @Test
    fun impureReducerShouldFail() {
        val reduce: Reduce<TestCounterState> = { action, state ->
            when (action) {
                is TestCounterAction.IncrementAction -> state.copy(count = state.count + 1,
                    updatedOn = System.nanoTime())
                else -> state
            }
        }
        val stateReserve = stateReserve(InitialState.set(TestCounterState()), reduce)
        thrown.expect(IllegalArgumentException::class.java)
        thrown.expectMessage("Impure reducer used!")
        stateReserve.dispatch(TestCounterAction.IncrementAction)
    }

    @Test
    fun pureReducerShouldNotFail() {
        val reduce: Reduce<TestCounterState> = { action, state ->
            when (action) {
                is TestCounterAction.IncrementAction -> state.copy(count = state.count + 1)
                else -> state
            }
        }
        val stateReserve = stateReserve(InitialState.set(TestCounterState()), reduce)
        stateReserve.dispatch(TestCounterAction.IncrementAction)
    }
}
