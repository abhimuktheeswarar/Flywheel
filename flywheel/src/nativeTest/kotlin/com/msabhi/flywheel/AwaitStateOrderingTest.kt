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

import com.msabhi.flywheel.base.BaseTest
import com.msabhi.flywheel.common.TestCounterAction
import com.msabhi.flywheel.common.TestCounterState
import kotlinx.coroutines.*
import kotlin.test.Test
import kotlin.test.assertEquals

class AwaitStateOrderingTest : BaseTest() {

    private val reduce: Reduce<TestCounterState> = { action, state ->
        when (action) {
            is TestCounterAction.IncrementAction -> state.copy(count = state.count + 1)
            else -> state
        }
    }

    @Test
    fun testAwaitStateReflectsAllPrecedingDispatches() = runTest {
        val n = 100
        val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
        val config = StateReserveConfig(scope = scope, debugMode = false)
        val stateReserve = StateReserve(
            initialState = InitialState.set(TestCounterState(0)),
            reduce = reduce,
            config = config,
            middlewares = null
        )

        repeat(n) {
            stateReserve.dispatch(TestCounterAction.IncrementAction)
        }

        val state = stateReserve.awaitState()
        assertEquals(n, state.count)

        scope.cancel()
    }

    @Test
    fun testAwaitStateWithConcurrentDispatches() = runTest {
        val n = 50
        val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
        val config = StateReserveConfig(scope = scope, debugMode = false)
        val stateReserve = StateReserve(
            initialState = InitialState.set(TestCounterState(0)),
            reduce = reduce,
            config = config,
            middlewares = null
        )

        coroutineScope {
            (1..4).map {
                launch(Dispatchers.Default) {
                    repeat(n) {
                        stateReserve.dispatch(TestCounterAction.IncrementAction)
                    }
                }
            }.forEach { it.join() }
        }

        val state = stateReserve.awaitState()
        assertEquals(n * 4, state.count)

        scope.cancel()
    }
}
