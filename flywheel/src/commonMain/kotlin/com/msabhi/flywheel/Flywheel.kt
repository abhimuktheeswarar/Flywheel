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

package com.msabhi.flywheel

import com.msabhi.flywheel.utilities.MutableStateChecker
import com.msabhi.flywheel.utilities.assertStateValues
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.selects.select

interface Action

interface EventAction : Action

interface NavigateAction : Action

interface ErrorAction : Action {
    val exception: Exception
}

interface SkipReducer : Action

interface State

typealias Reduce<S> = (action: Action, state: S) -> S

typealias Reducer<A, S> = (A, S) -> S

typealias Dispatch = (Action) -> Unit

typealias GetState<S> = () -> S

typealias Middleware<S> = (Dispatch, GetState<S>) -> (Dispatch) -> Dispatch

class StateReserveConfig(
    val scope: CoroutineScope,
    val debugMode: Boolean,
    val ignoreDuplicateState: Boolean = true,
    val assertStateValues: Boolean = debugMode,
    val checkMutableState: Boolean = debugMode,
)

private fun <S : State> CoroutineScope.stateMachine(
    initialState: S,
    inputActions: ReceiveChannel<Action>,
    requestStates: ReceiveChannel<Unit>,
    sendStates: SendChannel<S>,
    setStates: MutableSharedFlow<S>,
    coldActions: MutableSharedFlow<Action>,
    reduce: Reduce<S>,
    ignoreDuplicateState: Boolean,
) = launch {

    var state = initialState

    while (isActive) {

        select<Unit> {

            inputActions.onReceive { action ->
                val newState = reduce(action, state)
                if (ignoreDuplicateState) {
                    if (newState != state) {
                        state = newState
                        setStates.emit(state)
                    }
                } else {
                    state = newState
                    setStates.emit(state)
                }
                coldActions.emit(action)
            }

            requestStates.onReceive {
                sendStates.send(state)
            }
        }
    }
}

class StateReserve<S : State>(
    val config: StateReserveConfig,
    private val initialState: S,
    private val reduce: Reduce<S>,
    middlewares: List<Middleware<S>>?,
) {

    private val inputActionsChannel: Channel<Action> =
        Channel(capacity = Channel.UNLIMITED, onBufferOverflow = BufferOverflow.SUSPEND)

    private val requestStatesChannel: Channel<Unit> =
        Channel(capacity = Channel.UNLIMITED, onBufferOverflow = BufferOverflow.SUSPEND)
    private val sendStatesChannel: Channel<S> =
        Channel(capacity = Channel.UNLIMITED, onBufferOverflow = BufferOverflow.SUSPEND)

    private val mutableHotActions: MutableSharedFlow<Action> = MutableSharedFlow(
        extraBufferCapacity = Int.MAX_VALUE,
        onBufferOverflow = BufferOverflow.SUSPEND
    )

    private val mutableColdActions: MutableSharedFlow<Action> = MutableSharedFlow(
        extraBufferCapacity = Int.MAX_VALUE,
        onBufferOverflow = BufferOverflow.SUSPEND
    )

    private val setStates: MutableSharedFlow<S> = MutableSharedFlow<S>(
        replay = 1,
        extraBufferCapacity = Int.MAX_VALUE,
        onBufferOverflow = BufferOverflow.SUSPEND,
    ).apply { tryEmit(initialState) }

    val states: Flow<S> = setStates
    val hotActions: Flow<Action> = mutableHotActions
    val coldActions: Flow<Action> = mutableColdActions

    private val middlewares =
        middlewares?.foldRight({ action: Action -> this.dispatcher(action) }) { middleware, dispatcher ->
            middleware(::dispatch, ::state)(dispatcher)
        }

    private val mutableStateChecker =
        if (config.debugMode && config.checkMutableState) MutableStateChecker(initialState) else null

    init {

        config.scope.stateMachine(
            initialState = initialState,
            inputActions = inputActionsChannel,
            requestStates = requestStatesChannel,
            sendStates = sendStatesChannel,
            setStates = setStates,
            coldActions = mutableColdActions,
            reduce = reduce,
            ignoreDuplicateState = config.ignoreDuplicateState
        )
    }

    private fun dispatcher(action: Action) {
        if (config.debugMode && config.assertStateValues) {
            assertStateValues(action, state(), reduce, mutableStateChecker)
        }
        inputActionsChannel.trySend(action)
    }

    fun dispatch(action: Action) {
        mutableHotActions.tryEmit(action)
        middlewares?.invoke(action) ?: dispatcher(action)
    }

    fun state(): S = setStates.replayCache.last()

    suspend fun awaitState(): S {
        requestStatesChannel.send(Unit)
        return sendStatesChannel.receive()
    }

    fun terminate() {
        config.scope.cancel(CancellationException("invoked terminate"))
    }
}

fun <S : State> combineReducers(vararg reducers: Reduce<S>): Reduce<S> =
    { action, state ->
        reducers.fold(state, { s, reducer ->
            reducer(action, s)
        })
    }

operator fun <S> Reduce<S>.plus(other: Reduce<S>): Reduce<S> = { action, state ->
    other(action, this(action, state))
}

inline fun <reified A : Action, S> reducerForAction(crossinline reducer: Reducer<A, S>): Reduce<S> =
    { action, state ->
        when (action) {
            is A -> reducer(action, state)
            else -> state
        }
    }