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
import com.msabhi.flywheel.utilities.getDefaultStateReserveConfig
import com.msabhi.flywheel.utilities.skipMiddleware
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch

open class SimpleBaseViewModel<S : State>(
    private val initialState: S
) : ViewModel() {

    private val stateReserve = createStateReserve()

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
                this@SimpleBaseViewModel.initialState::class.assertImmutability()
            }
        }
    }

    private fun createStateReserve() = StateReserve(
        initialState = InitialState.set(initialState),
        reduce = ::reduce,
        middlewares = listOf(skipMiddleware),
        config = getDefaultStateReserveConfig()
    )

    fun state() = stateReserve.state()

    suspend fun awaitState() = stateReserve.awaitState()

    protected fun setState(reducer: S.() -> S) {
        dispatch(SetStateAction(reducer))
    }

    protected fun withState(action: (state: S) -> Unit) {
        stateReserve.state()
    }

    private fun dispatch(action: Action) {
        stateReserve.dispatch(action)
    }

    @Suppress("UNCHECKED_CAST")
    protected open fun reduce(action: Action, state: S): S {
        return (action as SetStateAction<S>).reducer(state)
    }

    override fun onCleared() {
        super.onCleared()
        stateReserve.terminate()
    }
}

data class SetStateAction<S : State>(val reducer: S.() -> S) : Action

