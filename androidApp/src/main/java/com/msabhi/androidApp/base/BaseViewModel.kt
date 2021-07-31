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

package com.msabhi.androidApp.base

import androidx.lifecycle.ViewModel
import com.msabhi.androidApp.utilities.assertImmutability
import com.msabhi.flywheel.*
import com.msabhi.flywheel.utilities.getDefaultScope
import com.msabhi.flywheel.utilities.getDefaultStateReserveConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch

open class BaseViewModel<S : State>(
    private val initialState: S,
    private val reduce: Reduce<S>? = null,
    private val middlewares: List<Middleware<S>>? = null,
    stateReserve: StateReserve<S>? = null,
    scope: CoroutineScope = getDefaultScope(),
    private val config: StateReserveConfig = getDefaultStateReserveConfig(scope, BuildConfig.DEBUG),
) : ViewModel() {

    private val stateReserve = stateReserve ?: createStateReserve()

    protected val TAG by lazy { this::class.simpleName ?: "FlywheelViewModel" }
    protected val actions: Flow<Action> = this.stateReserve.actions
    protected val actionStates: Flow<ActionState.Always<Action, S>> = this.stateReserve.actionStates
    protected val scope = this.stateReserve.config.scope

    val states: Flow<S> = this.stateReserve.states
    val eventActions: Flow<EventAction> = actions.filterIsInstance()
    val navigateActions: Flow<NavigateAction> = actions.filterIsInstance()

    init {

        if (this.stateReserve.config.debugMode) {
            this.stateReserve.config.scope.launch(Dispatchers.Default) {
                this@BaseViewModel.initialState::class.assertImmutability()
            }
        }
    }

    private fun createStateReserve() = StateReserve(
        initialState = InitialState.set(initialState),
        reduce = reduce ?: ::reduce,
        middlewares = middlewares,
        config = config
    )

    fun state() = stateReserve.state()

    suspend fun awaitState() = stateReserve.awaitState()

    fun dispatch(action: Action) {
        stateReserve.dispatch(action)
    }

    protected open fun reduce(action: Action, state: S): S {
        throw NotImplementedError("Either provide a reducer in constructor or override this function")
    }

    override fun onCleared() {
        super.onCleared()
        stateReserve.terminate()
    }
}

