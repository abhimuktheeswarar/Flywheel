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
import com.msabhi.flywheel.utilities.getDefaultStateReserveConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance

/**
 * Please note: [FlywheelViewModel] is provided for convenience.
 * Flywheel's StateReserve can be used without [FlywheelViewModel]
 * using your own ViewModel or with any other architecture patterns.
 */
open class FlywheelViewModel<S : State>(
    initialState: S,
    reduce: Reduce<S>? = null,
    config: StateReserveConfig = getDefaultStateReserveConfig(),
) {

    private val store = StateReserve(
        initialState = initialState,
        reduce = reduce ?: ::reduce,
        middlewares = null,
        config = config
    )

    protected val TAG by lazy { this::class.simpleName ?: "FlywheelViewModel" }
    protected val hotActions: Flow<Action> = this.store.hotActions
    protected val coldActions: Flow<Action> = this.store.coldActions
    protected val scope = this.store.config.scope

    val states: Flow<S> = this.store.states
    val eventActions: Flow<EventAction> = hotActions.filterIsInstance()
    val navigateActions: Flow<NavigateAction> = hotActions.filterIsInstance()


    fun state() = store.state()

    suspend fun awaitState() = store.awaitState()

    fun dispatch(action: Action) {
        store.dispatch(action)
    }

    protected open fun reduce(action: Action, state: S): S {
        throw NotImplementedError("Either provide a reducer in constructor or override this function")
    }

    fun onCleared() {
        store.terminate()
    }
}