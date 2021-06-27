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

package com.msabhi.androidApp.counter.ui

import android.content.Context
import com.msabhi.androidApp.counter.domain.middleware.EventMiddleware
import com.msabhi.androidApp.counter.domain.sideeffects.CounterColdSideEffect
import com.msabhi.androidApp.counter.entities.CounterAction
import com.msabhi.androidApp.counter.entities.CounterState
import com.msabhi.flywheel.StateReserve
import com.msabhi.flywheel.attachments.DispatcherProviderImpl
import com.msabhi.flywheel.attachments.FlywheelViewModel
import com.msabhi.flywheel.reducerForAction
import com.msabhi.flywheel.utilities.getDefaultScope
import com.msabhi.flywheel.utilities.getDefaultStateReserveConfig
import com.msabhi.flywheel.utilities.skipMiddleware
import java.lang.ref.WeakReference

class CounterViewModel(initialState: CounterState, stateReserve: StateReserve<CounterState>) :
    FlywheelViewModel<CounterState>(
        initialState = initialState,
        stateReserve = stateReserve) {

    companion object {

        private val reducer = reducerForAction<CounterAction, CounterState> { action, state ->
            with(state) {
                when (action) {
                    is CounterAction.IncrementAction -> copy(counter = counter + 1)
                    is CounterAction.DecrementAction -> copy(counter = counter - 1)
                    is CounterAction.ResetAction -> copy(counter = 0)
                    is CounterAction.ForceUpdateAction -> copy(counter = action.count)
                }
            }
        }

        fun get(context: Context): CounterViewModel {

            val scope = getDefaultScope()

            val middlewares =
                listOf(EventMiddleware(WeakReference(context), scope, DispatcherProviderImpl).get(),
                    skipMiddleware)

            val initialState = CounterState()

            val stateReserve = StateReserve(
                config = getDefaultStateReserveConfig(scope),
                initialState = initialState,
                reduce = reducer,
                middlewares = middlewares)

            CounterColdSideEffect(stateReserve, DispatcherProviderImpl)

            return CounterViewModel(initialState = initialState, stateReserve = stateReserve)
        }
    }
}