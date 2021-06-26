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
import com.msabhi.flywheel.State
import com.msabhi.flywheel.StateReserve
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

interface SideEffect {

    fun handle(action: Action)
}

abstract class BaseSideEffectHot(
    private val stateReserve: StateReserve<*>,
    protected val dispatchers: DispatcherProvider,
) : SideEffect {

    protected val TAG: String = this::class.simpleName ?: "SideEffectHot"
    protected val scope: CoroutineScope = stateReserve.config.scope

    init {
        stateReserve.hotActions.onEach(::handle).launchIn(scope)
    }

    fun dispatch(action: Action) {
        stateReserve.dispatch(action)
    }

    @Suppress("UNCHECKED_CAST")
    fun <S : State> state(): S = stateReserve.state() as S

    @Suppress("UNCHECKED_CAST")
    suspend fun <S : State> awaitState(): S = stateReserve.awaitState() as S
}

abstract class BaseSideEffectCold(
    private val stateReserve: StateReserve<*>,
    protected val dispatchers: DispatcherProvider,
) : SideEffect {

    protected val TAG: String = this::class.simpleName ?: "SideEffectCold"
    protected val scope: CoroutineScope = stateReserve.config.scope

    init {
        stateReserve.coldActions.onEach(::handle).launchIn(scope)
    }

    fun dispatch(action: Action) {
        stateReserve.dispatch(action)
    }

    @Suppress("UNCHECKED_CAST")
    fun <S : State> state(): S = stateReserve.state() as S

    @Suppress("UNCHECKED_CAST")
    suspend fun <S : State> awaitState(): S = stateReserve.awaitState() as S
}

abstract class BaseSideEffectHotCold(
    private val stateReserve: StateReserve<*>,
    protected val dispatchers: DispatcherProvider,
) {

    protected val TAG: String = this::class.simpleName ?: "SideEffectHotCold"
    protected val scope: CoroutineScope = stateReserve.config.scope

    init {
        stateReserve.hotActions.onEach(::handleHot).launchIn(scope)
        stateReserve.coldActions.onEach(::handleCold).launchIn(scope)
    }

    fun dispatch(action: Action) {
        stateReserve.dispatch(action)
    }

    @Suppress("UNCHECKED_CAST")
    fun <S : State> state(): S = stateReserve.state() as S

    @Suppress("UNCHECKED_CAST")
    suspend fun <S : State> awaitState(): S = stateReserve.awaitState() as S

    abstract fun handleHot(action: Action)

    abstract fun handleCold(action: Action)
}