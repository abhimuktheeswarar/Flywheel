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

package com.msabhi.androidApp.counter.domain.middleware

import android.content.Context
import android.widget.Toast
import com.msabhi.androidApp.common.ShowToastAction
import com.msabhi.androidApp.counter.entities.CounterAction
import com.msabhi.androidApp.counter.entities.CounterState
import com.msabhi.flywheel.Action
import com.msabhi.flywheel.Dispatch
import com.msabhi.flywheel.GetState
import com.msabhi.flywheel.attachments.BaseMiddleware
import com.msabhi.flywheel.attachments.DispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

class EventMiddleware(
    private val context: WeakReference<Context>,
    scope: CoroutineScope,
    dispatcherProvider: DispatcherProvider,
) :
    BaseMiddleware<CounterState>(scope, dispatcherProvider) {

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
                    dispatch(ShowToastAction("Counter value is already 0"))
                }
            }

            is ShowToastAction -> {
                scope.launch(dispatcherProvider.Main) {
                    context.get()?.let {
                        Toast.makeText(it, action.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

            else -> next(action)
        }
    }
}