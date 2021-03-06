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
import com.msabhi.flywheel.utilities.name
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.selects.select

/**
 * [Action] are the inputs to the [StateReserve].
 */
interface Action

/**
 * [EventAction] are useful for showing a notification, toast
 * or any one-off events.
 */
interface EventAction : Action

/**
 * [NavigateAction] are specifically used for navigation.
 * Though a [EventAction] can be used for the same purpose,
 * it is provided to better differentiate between a event & a navigation.
 */
interface NavigateAction : Action

/**
 * [ErrorAction] defines the base type to dispatch any error events.
 * By having a [ErrorAction] type, we can easily filter [ErrorAction] and send it for error logging.
 */
interface ErrorAction : Action {
    val exception: Exception
}

/**
 * If a action doesn't change the state or no need to pass through a reducer, we can implement [SkipReducer] to any action.
 * Example: A `ShowToastAction` doesn't have to pass through a reducer, so it can implement [SkipReducer].
 */
interface SkipReducer : Action

/**
 * Internal Action to force distinct actions even when same actions are received in dispatch.
 */
private object ForceDistinctAction : Action

/**
 * Objects holding the state of a application/feature must implement [State].
 */
interface State

/**
 * A [Reduce] is a pure / must be a function that receives the current state and an action object, decides how to update the state if necessary, and returns the new state.
 */
typealias Reduce<S> = (action: Action, state: S) -> S

/**
 * A [Reducer] is provided to allow combining multiple reducers.
 */
internal typealias Reducer<A, S> = (A, S) -> S

/**
 * [Dispatch] is the entry point for all actions.
 * The only way to update the state is to call dispatch() and pass in an action.
 * The [StateReserve] will run its reducer function and save the new state value inside.
 */
typealias Dispatch = (Action) -> Unit

/**
 * [GetState] to retrieve the current state.
 * Please note, the [GetState] may not always give the last updated state.
 * To get the last updated state, use `awaitState()`.
 */
typealias GetState<S> = () -> S

/**
 * [Middleware] helps to intercept actions before reaching the [StateReserve] reduce function.
 * We can use [Middleware] to modify an received action, swallow the action to prevent reaching the reducer.
 * [Middleware] by default runs on caller thread, which can be Main thread also.
 * Though the concept of [Middleware] is similar to Redux. It is not recommended to use middleware for async operations. For that,
 * we use the concept of SideEffects.
 */
typealias Middleware<S> = (Dispatch, GetState<S>) -> (Dispatch) -> Dispatch

/**
 * This exception is thrown when the reducer can't find a match for the given [Action] in the current state.
 * This will work when `enhancedStateMachine` is enabled in [StateReserve] config.
 */
class UnsupportedStateTransition(val msg: String) : Exception(msg)

/**
 * Provides [State] transition data to use [StateReserve] as a proper StateMachine.
 * You can listen to state transitions only when `enhancedStateMachine` boolean is set to true in [StateReserveConfig].
 */
sealed interface Transition<out A : Action, out S : State> {
    val action: A
    val fromState: S

    /**
     * For valid [State] transitions.
     */
    data class Valid<out A : Action, out FS : State, out TS : State>(
        override val action: A,
        override val fromState: FS,
        val toState: TS,
    ) : Transition<A, FS>

    /**
     * When there is no [State] transition for the given [Action] [State] inputs to `reduce` function.
     * i.e when the state doesn't change
     */
    data class Nothing<out A : Action, out S : State>(
        override val action: A,
        override val fromState: S,
    ) : Transition<A, S>

    /**
     * When there is no match found for the given [Action] and current [State].
     * It is recommended to use [reduceError] function in your reducer when no match is found.
     */
    data class InValid<out A : Action, out S : State>(
        override val action: A,
        override val fromState: S,
        val exception: Exception = UnsupportedStateTransition("Unsupported state transition attempted with state ${fromState::class.simpleName} for action ${action.name()}"),
    ) : Transition<A, S>
}

/**
 * Container for [Action] and [State].
 */
sealed interface ActionState<out A : Action, out S : State> {

    val action: A
    val state: S

    /**
     * This will be emitted by `actionStates` Flow always after [Action] passing through reducer, irrespective [State] is changed or not.
     */
    data class Always<out A : Action, out S : State>(
        override val action: A,
        override val state: S,
    ) : ActionState<A, S>

    /**
     * This will be emitted by `transitions` Flow<Any> when a new state is entered.
     * Useful when modeling a StateMachine behaviour.
     */
    data class OnEnter<out A : Action, out S : State>(
        override val action: A,
        override val state: S,
    ) : ActionState<A, S>

    /**
     * This will be emitted by `transitions` Flow<Any> when a state is exited..
     * Useful when modeling a StateMachine behaviour..
     */
    data class OnExit<out A : Action, out S : State>(
        override val action: A,
        override val state: S,
    ) : ActionState<A, S>
}

/**
 * Provides the ability to defer setting the initial state of [StateReserve].
 * Useful for restoring state.
 */
class InitialState<S : State> private constructor(
    val state: S? = null,
    val deferredState: CompletableDeferred<S>? = null,
) {

    companion object {

        /**
         * Set the initial state of [StateReserve]
         */
        fun <S : State> set(state: S): InitialState<S> = InitialState(state)

        /**
         * To set the initial state after initializing [StateReserve].
         * Don't forget to call `restoreState()` function in [StateReserve].
         * Till then, all actions will remain in queue.
         */
        fun <S : State> deferredSet(): InitialState<S> =
            InitialState(null, CompletableDeferred())
    }
}

/**
 * Provides basic configuration for the [StateReserve].
 */
class StateReserveConfig(
    val scope: CoroutineScope,
    val debugMode: Boolean,
    val ignoreDuplicateState: Boolean = true,
    /**
     * Enabling this will allow to listen for transitions when using [StateReserve] as a proper StateMachine.
     */
    val enhancedStateMachine: Boolean = false,
    val assertStateValues: Boolean = debugMode,
    val checkMutableState: Boolean = debugMode,
)

/**
 * The [stateMachine] is the core of flywheel. It holds the [StateReserve]'s state, updates the state and notify the state updates.
 * This is based on the concepts of actors. By confining the state, it satisfies the concurrency rules of Kotlin-Native.
 */
private fun <S : State> CoroutineScope.stateMachine(
    initialState: InitialState<S>,
    inputActions: ReceiveChannel<Action>,
    requestStates: ReceiveChannel<Unit>,
    sendStates: SendChannel<S>,
    setStates: MutableSharedFlow<S>,
    transitions: MutableSharedFlow<Any>,
    reduce: Reduce<S>,
    ignoreDuplicateState: Boolean,
    enhancedStateMachine: Boolean,
) = launch {

    var state =
        initialState.deferredState?.await()?.also { setStates.emit(it) } ?: initialState.state!!

    suspend fun transition(action: Action, fromState: S, toState: S, throwable: Throwable? = null) {
        transitions.emit(ActionState.Always(action, toState))
        when {
            !enhancedStateMachine -> {
                return
            }
            throwable != null -> {
                val transition = Transition.InValid(action, fromState)
                transitions.emit(transition)
            }
            fromState::class.isInstance(toState) || fromState == toState -> {
                val transition = Transition.Nothing(action, state)
                transitions.emit(transition)
            }
            else -> {
                val transition = Transition.Valid(action, fromState, toState)
                transitions.emit(transition)
                transitions.emit(ActionState.OnExit(action, fromState))
                transitions.emit(ActionState.OnEnter(action, toState))
            }
        }
    }

    while (isActive) {

        select<Unit> {

            inputActions.onReceive { action ->
                runCatching { reduce(action, state) }.fold({ newState ->

                    transition(action, state, newState)

                    if (ignoreDuplicateState) {
                        if (newState != state) {
                            state = newState
                            setStates.emit(state)
                        }
                    } else {
                        state = newState
                        setStates.emit(state)
                    }

                }, { error ->
                    transition(action, state, state, error)
                })
            }

            requestStates.onReceive {
                sendStates.send(state)
            }
        }
    }
}

/**
 * The [StateReserve] is a state container which holds the state and orchestrates all the input & outputs for the [stateMachine].
 */
class StateReserve<S : State>(
    val config: StateReserveConfig,
    private val initialState: InitialState<S>,
    private val reduce: Reduce<S>,
    middlewares: List<Middleware<S>>?,
) {

    private val actionsChannel: Channel<Action> =
        Channel(capacity = Channel.UNLIMITED, onBufferOverflow = BufferOverflow.SUSPEND)

    private val inputActionsChannel: Channel<Action> =
        Channel(capacity = Channel.UNLIMITED, onBufferOverflow = BufferOverflow.SUSPEND)

    private val requestStatesChannel: Channel<Unit> =
        Channel(capacity = Channel.UNLIMITED, onBufferOverflow = BufferOverflow.SUSPEND)
    private val sendStatesChannel: Channel<S> =
        Channel(capacity = Channel.UNLIMITED, onBufferOverflow = BufferOverflow.SUSPEND)

    private val mutableActions: MutableSharedFlow<Action> = MutableSharedFlow(
        extraBufferCapacity = Int.MAX_VALUE,
        onBufferOverflow = BufferOverflow.SUSPEND
    )

    /**
     *  [MutableSharedFlow] is used instead of MutableStateFlow, since StateFlow drops the oldest values by default, leading to inconsistency in state output under load.
     */
    private val setStates: MutableSharedFlow<S> = MutableSharedFlow<S>(
        replay = 1,
        extraBufferCapacity = Int.MAX_VALUE,
        onBufferOverflow = BufferOverflow.SUSPEND,
    ).apply { initialState.state?.let { tryEmit(it) } }

    private val transitionsMutable: MutableSharedFlow<Any> = MutableSharedFlow(
        extraBufferCapacity = Int.MAX_VALUE,
        onBufferOverflow = BufferOverflow.SUSPEND
    )

    /**
     * Returns a [Flow] for this StateReserve's state. It will begin by immediately emitting
     * the latest set value and then continue with all subsequent updates.
     * This flow never completes.
     */
    val states: Flow<S> = setStates

    /**
     * Returns a [Flow] of actions that are passed through reducer.
     */
    val actionStates: Flow<ActionState.Always<Action, S>> = transitionsMutable.filterIsInstance()

    /**
     * Returns a [Flow] of actions that are passed through middleware and before reaching reducer.
     * This also captures actions modified by middlewares.
     * It is useful to react immediately to an received action.
     */
    val actions: Flow<Action> =
        mutableActions.distinctUntilChanged().filterNot { it is ForceDistinctAction }

    /**
     * Enable `enhancedStateMachine` config to listen for `transitions`.
     * Use this along with provided extension functions to collect state transitions to get a StateMachine like behaviour.
     */
    val transitions: Flow<Any> = transitionsMutable

    private val middlewares =
        middlewares?.foldRight({ action: Action -> this.dispatcher(action) }) { middleware, dispatcher ->
            middleware(::dispatch, ::state)(dispatcher)
        }

    private val mutableStateChecker by lazy {
        if (config.debugMode && config.checkMutableState) MutableStateChecker(state()) else null
    }

    init {

        config.scope.launch {
            actionsChannel.consumeEach { action ->
                if (isActive) {
                    mutableActions.tryEmit(action)
                    this@StateReserve.middlewares?.invoke(action) ?: dispatcher(action)
                }
            }
        }

        config.scope.stateMachine(
            initialState = initialState,
            inputActions = inputActionsChannel,
            requestStates = requestStatesChannel,
            sendStates = sendStatesChannel,
            setStates = setStates,
            transitions = transitionsMutable,
            reduce = reduce,
            ignoreDuplicateState = config.ignoreDuplicateState,
            enhancedStateMachine = config.enhancedStateMachine
        )
    }

    private fun dispatcher(action: Action) {
        if (config.debugMode && config.assertStateValues) {
            assertStateValues(action, state(), reduce, mutableStateChecker)
        }
        mutableActions.tryEmit(action)
        mutableActions.tryEmit(ForceDistinctAction)
        inputActionsChannel.trySend(action)
    }

    /**
     * It is the entry point for actions to update the StateReserve's state.
     */
    fun dispatch(action: Action) {
        actionsChannel.trySend(action)
    }

    /**
     * Synchronous access to state. Please note, there is no guarantee that the state will be the final expected state.
     * i.e there could be some actions in the queue to update the state. Calling this function will provide the state at that moment, not after all actions have passed through a reducer.
     */
    fun state(): S = setStates.replayCache.lastOrNull()
        ?: throw IllegalStateException("Initial state is not yet set")

    /**
     * This function is guaranteed to provide the final state after all actions are processed by the [stateMachine] reducer.
     * So if your code relies on certain state, use this function.
     */
    suspend fun awaitState(): S {
        requestStatesChannel.send(Unit)
        return sendStatesChannel.receive()
    }

    /**
     * To restore a state. i.e to set the initial state after initializing [StateReserve].
     * Please note, state can be set only once. i.e you can use the restore state feature only once.
     * Trying to call this function more than once will result in IllegalArgumentException.
     * This function can be used only when initial state is set using [InitialState.deferredState].
     * Till this function is called, all actions will be queued. i.e no actions will pass through reducer.
     */
    fun restoreState(state: S): Boolean {
        return when {
            initialState.state != null -> throw IllegalArgumentException("State already set")
            initialState.deferredState == null -> throw IllegalArgumentException("Restoring state is not supported")
            initialState.deferredState.isCompleted -> throw IllegalArgumentException("State already restored")
            initialState.deferredState.isActive -> initialState.deferredState.complete(state)
            else -> false
        }
    }

    /**
     * Call this function to cancel the scope, thus cancelling/stopping all operations going on in StateReserve & in the associated SideEffects.
     * In Android, this can be called on ViewModel's onCleared().
     */
    fun terminate() {
        config.scope.cancel(CancellationException("invoked terminate"))
    }
}

/**
 * Helper function to combine multiple reducers based on action type.
 */
fun <S : State> combineReducers(vararg reducers: Reduce<S>): Reduce<S> =
    { action, state ->
        reducers.fold(state, { s, reducer ->
            reducer(action, s)
        })
    }

/**
 * Helper function to combine multiple reducers based on action type. Similar to [combineReducers]
 */
operator fun <S> Reduce<S>.plus(other: Reduce<S>): Reduce<S> = { action, state ->
    other(action, this(action, state))
}

/**
 * To be used, when using [combineReducers], to help combine reducers.
 */
inline fun <reified A : Action, S : State> reducerForAction(crossinline reducer: Reducer<A, S>): Reduce<S> =
    { action, state ->
        when (action) {
            is A -> reducer(action, state)
            else -> state
        }
    }

/**
 * Use this to notify [StateReserve] in your reducer when no match is found for the given [Action] and [State].
 * Helpful when using [StateReserve] as a proper StateMachine.
 */
fun reduceError(): Nothing = throw IllegalStateException()

/**
 * Returns a flow containing only [State] that are instances of specified type [S].
 */
inline fun <reified S : State, reified T> Flow<S>.specificStates(crossinline transform: suspend (S) -> T): Flow<T> =
    map(transform).distinctUntilChanged()

/**
 * Returns a flow containing only [Action] that are instances of specified type [A].
 */
inline fun <reified A : Action> Flow<Action>.specificActions(): Flow<A> =
    filterIsInstance()

/**
 * Returns a flow containing only [Action] from `actionStates` flow
 */
inline fun <reified A : Action> Flow<ActionState.Always<A, State>>.onlyActions(): Flow<A> =
    map { it.action }

/**
 * Returns a flow containing valid transitions of from state [FS] and to state [TS].
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified FS : State, reified TS : State> Flow<Any>.validTransitions(): Flow<Transition.Valid<Action, FS, TS>> =
    filterIsInstance<Transition<*, *>>()
        .filter { it.fromState is FS && (it is Transition.Valid<*, *, *> && it.toState is TS) } as Flow<Transition.Valid<Action, FS, TS>>

/**
 * Returns a flow containing valid transitions of from state [FS] and to state [TS] with [Action] [A].
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified A : Action, reified FS : State, reified TS : State> Flow<Any>.validTransitionWithAction(): Flow<Transition.Valid<A, FS, TS>> =
    filterIsInstance<Transition<*, *>>()
        .filter { it.action is A && it.fromState is FS && (it is Transition.Valid<*, *, *> && it.toState is TS) } as Flow<Transition.Valid<A, FS, TS>>

/**
 * Returns a flow containing invalid transitions for state [S].
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified S : State> Flow<Any>.inValidTransition(): Flow<Transition.InValid<Action, S>> =
    filterIsInstance<Transition<*, *>>()
        .filter { (it is Transition.InValid<*, *>) && it.fromState is S } as Flow<Transition.InValid<Action, S>>

/**
 * Returns a flow containing invalid transitions for action [A] and state [S].
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified A : Action, reified S : State> Flow<Any>.inValidTransitionWithAction(): Flow<Transition.InValid<A, S>> =
    filterIsInstance<Transition<*, *>>()
        .filter { it.action is A && (it is Transition.InValid<*, *>) && it.fromState is S } as Flow<Transition.InValid<A, S>>

/**
 * Returns a flow when a state [S] is entered for action [A].
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified S : State> Flow<Any>.onEnter(): Flow<ActionState.OnEnter<Action, S>> =
    filterIsInstance<ActionState.OnEnter<*, *>>()
        .filter { it.state is S } as Flow<ActionState.OnEnter<Action, S>>

/**
 * Returns a flow when a state [S] is exited for action [A].
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified S : State> Flow<Any>.onExit(): Flow<ActionState.OnExit<Action, S>> =
    filterIsInstance<ActionState.OnExit<*, *>>()
        .filter { it.state is S } as Flow<ActionState.OnExit<Action, S>>

