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
import kotlinx.coroutines.CoroutineScope

abstract class BaseMiddleware<S : State>(
    protected val scope: CoroutineScope,
    protected val dispatcherProvider: DispatcherProvider,
) {

    protected val TAG: String = this::class.simpleName ?: "Middleware"

    private val middleware: Middleware<S> = { dispatch, getState ->

        { next ->

            { action ->

                handle(action, getState, next, dispatch)
            }
        }
    }

    fun get() = middleware

    abstract fun handle(action: Action, state: GetState<S>, next: Dispatch, dispatch: Dispatch)
}