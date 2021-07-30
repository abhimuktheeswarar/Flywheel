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

import com.msabhi.flywheel.Action
import com.msabhi.flywheel.ActionState
import com.msabhi.flywheel.State
import com.msabhi.flywheel.StateReserve
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

abstract class SideEffect<S : State>(
    private val stateReserve: StateReserve<S>,
    protected val dispatchers: DispatcherProvider,
) {

    protected val scope: CoroutineScope = stateReserve.config.scope

    protected val actions: Flow<Action> = stateReserve.actions
    protected val actionStates: Flow<ActionState<Action, S>> = stateReserve.actionStates
    protected val transitions: Flow<Any> = stateReserve.transitions

    fun dispatch(action: Action) {
        stateReserve.dispatch(action)
    }

    suspend fun awaitState(): S = stateReserve.awaitState()
}