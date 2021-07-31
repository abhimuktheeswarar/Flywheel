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

package com.msabhi.androidApp.counter.domain.sideeffects

import com.msabhi.androidApp.common.ShowToastAction
import com.msabhi.androidApp.counter.entities.CounterAction
import com.msabhi.androidApp.counter.entities.CounterState
import com.msabhi.flywheel.Action
import com.msabhi.flywheel.ActionState
import com.msabhi.flywheel.StateReserve
import com.msabhi.flywheel.attachments.DispatcherProvider
import com.msabhi.flywheel.attachments.SideEffect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CounterSideEffect(stateReserve: StateReserve<CounterState>, dispatchers: DispatcherProvider) :
    SideEffect<CounterState>(stateReserve, dispatchers) {

    init {
        actionStates.onEach(::handle).launchIn(scope)
    }

    private fun handle(actionState: ActionState<Action, CounterState>) {
        when (actionState.action) {

            is CounterAction.ResetAction -> {
                if (actionState.state.counter == 0) {
                    dispatch(ShowToastAction("Reset complete"))
                }
            }
        }
    }
}