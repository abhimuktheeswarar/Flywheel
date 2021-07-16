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

package com.msabhi.flywheel.utilities

import com.msabhi.flywheel.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.SupervisorJob

fun Action.name(): String = this::class.simpleName ?: "Action"

/**
 * Middleware to prevent actions from reaching reducer which implement [SkipReducer].
 * This should be last middleware.
 */
val skipMiddleware: Middleware<State> = { _, _ ->
    { next ->

        {
            if (it !is SkipReducer) {
                next(it)
            }
        }
    }
}

fun getDefaultScope() =
    CoroutineScope(SupervisorJob() + CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    })

fun getMainScope() = MainScope()

fun getDefaultStateReserveConfig(
    scope: CoroutineScope = getDefaultScope(),
    debugMode: Boolean = false,
) =
    StateReserveConfig(scope = scope,
        debugMode = debugMode)

