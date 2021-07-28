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
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test

@Suppress("EXPERIMENTAL_API_USAGE")
class SetStateWithStateAfterScopeCancellationTest {

    @Test
    fun setStateAfterScopeCancellation() = runBlockingTest {
        val scope = TestCoroutineScope(Job())
        scope.cancel()
        val reduce: Reduce<TestCounterState> = { action, state ->
            when (action) {
                is TestCounterAction.ForceUpdateAction -> state.copy(count = action.count)
                else -> state
            }
        }
        val config =
            StateReserveConfig(
                scope = scope,
                debugMode = false)
        val stateReserve =
            StateReserve(initialState = InitialState.set(TestCounterState(1)),
                reduce = reduce,
                config = config,
                middlewares = null)
        stateReserve.dispatch(TestCounterAction.ForceUpdateAction(4))
        // ensure set operation above is ignored
        val count = stateReserve.state().count
        Assert.assertEquals(1, count)
    }
}