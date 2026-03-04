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

package com.msabhi.nativeApp.counter.domain.middleware

import com.msabhi.flywheel.Action
import com.msabhi.flywheel.Dispatch
import com.msabhi.flywheel.GetState
import com.msabhi.flywheel.attachments.BaseMiddleware
import com.msabhi.flywheel.attachments.DispatcherProvider
import com.msabhi.nativeApp.common.ShowNotificationAction
import com.msabhi.nativeApp.counter.entities.CounterAction
import com.msabhi.nativeApp.counter.entities.CounterState
import kotlinx.coroutines.CoroutineScope

class EventMiddleware(
    scope: CoroutineScope,
    dispatcherProvider: DispatcherProvider,
) : BaseMiddleware<CounterState>(scope, dispatcherProvider) {

    override fun handle(
        action: Action,
        state: GetState<CounterState>,
        next: Dispatch,
        dispatch: Dispatch,
    ) {
        when (action) {

            is CounterAction.ResetAction -> {
                if (state().counter != 0) {
                    next(action)
                } else {
                    dispatch(ShowNotificationAction("Counter value is already 0"))
                }
            }

            is ShowNotificationAction -> {
                // ShowNotificationAction is collected from the actions flow in CounterApp.
                // No further propagation needed.
            }

            else -> next(action)
        }
    }
}
