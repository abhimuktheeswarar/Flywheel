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
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertNull

@OptIn(ExperimentalCoroutinesApi::class)
class PureReducerValidationTest {

    private fun <S : State> stateReserve(
        scope: CoroutineScope,
        initialState: InitialState<S>,
        reduce: Reduce<S>,
    ): StateReserve<S> {
        val config =
            StateReserveConfig(
                scope = scope,
                debugMode = true
            )
        return StateReserve(
            initialState = initialState,
            reduce = reduce,
            config = config,
            middlewares = null)
    }

    @Test
    fun pureReducerShouldNotFail() = runTest(UnconfinedTestDispatcher()) {
        var caughtException: Throwable? = null
        val handler = CoroutineExceptionHandler { _, throwable -> caughtException = throwable }
        val scope = CoroutineScope(SupervisorJob() + UnconfinedTestDispatcher(testScheduler) + handler)
        val reduce: Reduce<TestCounterState> = { action, state ->
            when (action) {
                is TestCounterAction.IncrementAction -> state.copy(count = state.count + 1)
                else -> state
            }
        }
        val stateReserve = stateReserve(scope, InitialState.set(TestCounterState()), reduce)
        stateReserve.dispatch(TestCounterAction.IncrementAction)
        assertNull(caughtException)
    }
}
