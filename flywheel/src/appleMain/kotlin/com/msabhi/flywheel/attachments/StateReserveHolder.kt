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

package com.msabhi.flywheel.attachments

import com.msabhi.flywheel.*
import com.msabhi.flywheel.utilities.getDefaultScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance

/**
 * A helper class to make it easier to use Flywheel's StateReserve in Swift.
 */
open class StateReserveHolder<S : State>(
    initialState: S,
    reduce: Reduce<S>,
    config: StateReserveConfig?,
) {

    val stateReserve = StateReserve(
        initialState = InitialState.Companion.set(initialState),
        reduce = reduce,
        middlewares = null,
        config = config ?: StateReserveConfig(scope = getDefaultScope(),
            debugMode = Platform.isDebugBinary,
            ignoreDuplicateState = false,
            assertStateValues = false,
            checkMutableState = false)
    )

    protected val TAG by lazy { this::class.simpleName ?: "StateReserveHolder" }
    protected val hotActions: Flow<Action> = this.stateReserve.hotActions
    protected val coldActions: Flow<Action> = this.stateReserve.coldActions
    protected val scope = this.stateReserve.config.scope

    val states: Flow<S> = this.stateReserve.states
    val eventActions: Flow<EventAction> = hotActions.filterIsInstance()
    val navigateActions: Flow<NavigateAction> = hotActions.filterIsInstance()

    fun state() = stateReserve.state()

    suspend fun awaitState() = stateReserve.awaitState()

    fun dispatch(action: Action) {
        stateReserve.dispatch(action)
    }

    fun onCleared() {
        stateReserve.terminate()
    }
}