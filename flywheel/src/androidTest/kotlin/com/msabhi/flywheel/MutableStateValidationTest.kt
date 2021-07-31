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

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@Suppress("EXPERIMENTAL_API_USAGE")
class MutableStateValidationTest {

    internal data class StateWithMutableMap(val map: MutableMap<String, String> = mutableMapOf()) :
        State

    internal data class StateWithImmutableMap(val map: Map<String, String> = mapOf()) : State

    internal data class UpdateDataAction(val item: Pair<String, String>) : Action

    private fun <S : State> getStateReserve(
        scope: CoroutineScope = TestCoroutineScope(TestCoroutineDispatcher()),
        initialState: S,
        reduce: Reduce<S>,
    ): StateReserve<S> {
        val config =
            StateReserveConfig(scope = scope,
                debugMode = true,
                enhancedStateMachine = false)

        return StateReserve(config, InitialState.set(initialState), reduce, null)
    }

    @ExperimentalCoroutinesApi
    @Test(expected = IllegalArgumentException::class)
    fun mutableStateShouldFail() = runBlockingTest {
        val initialState = StateWithMutableMap(map = mutableMapOf("1" to "one"))
        val reduce: Reduce<StateWithMutableMap> =
            { action, state ->
                if (action is UpdateDataAction) {
                    val map = state.map.apply { put(action.item.first, action.item.second) }
                    state.copy(map = map)
                } else state
            }
        val stateReserve = getStateReserve(initialState = initialState, reduce = reduce)
        stateReserve.dispatch(UpdateDataAction(Pair("1", "two")))
    }

    @Test
    fun immutableStateShouldNotFail() {
        val initialState = StateWithImmutableMap(map = mapOf("1" to "two"))
        val reduce: Reduce<StateWithImmutableMap> =
            { action, state ->
                if (action is UpdateDataAction) {
                    val map =
                        HashMap(state.map).apply { put(action.item.first, action.item.second) }
                    state.copy(map = map)
                } else state
            }
        val stateReserve = getStateReserve(initialState = initialState, reduce = reduce)
        stateReserve.dispatch(UpdateDataAction(Pair("1", "two")))
    }
}
