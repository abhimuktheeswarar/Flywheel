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

import kotlinx.coroutines.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class StateMachineTest {

    internal sealed class MaterialState : State {
        object Solid : MaterialState()
        object Liquid : MaterialState()
        object Gas : MaterialState()
        data class Quantum(val states: MutableMap<Int, MaterialState>) : MaterialState()
    }

    internal sealed class MaterialAction : Action {
        object OnMelted : MaterialAction()
        object OnFrozen : MaterialAction()
        object OnVaporized : MaterialAction()
        object OnCondensed : MaterialAction()
        data class OnQuantum(val id: Int, val state: MaterialState) : MaterialAction()
    }

    private val materialReducer =
        reducerForAction<MaterialAction, MaterialState> { action, state ->
            when (state) {
                MaterialState.Solid -> when (action) {
                    MaterialAction.OnMelted -> MaterialState.Liquid
                    else -> reduceError()
                }
                MaterialState.Liquid -> when (action) {
                    MaterialAction.OnFrozen -> MaterialState.Solid
                    MaterialAction.OnVaporized -> MaterialState.Gas
                    else -> reduceError()
                }
                MaterialState.Gas -> when (action) {
                    MaterialAction.OnCondensed -> MaterialState.Liquid
                    else -> reduceError()
                }
                is MaterialState.Quantum -> when (action) {
                    is MaterialAction.OnQuantum -> state.apply {
                        this.states[action.id] = action.state
                    }
                    else -> reduceError()
                }
            }
        }

    private fun <S : State> getStateReserve(
        scope: CoroutineScope,
        initialState: S,
        reduce: Reduce<S>,
        debugMode: Boolean,
        middlewares: List<Middleware<S>>? = null,
    ): StateReserve<S> {
        val config =
            StateReserveConfig(
                scope = scope,
                debugMode = debugMode,
                enhancedStateMachine = true
            )
        return StateReserve(config, InitialState.set(initialState), reduce, middlewares)
    }

    @Test
    fun materialStateMachineTest() = runBlocking {
        val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
        val stateReserve =
            getStateReserve(
                scope = scope,
                initialState = MaterialState.Solid,
                debugMode = false,
                reduce = materialReducer
            )

        var simpleValidTransitions = 0
        var validTransitions = 0
        var inValidTransitions = 0
        var onExit = 0
        var onEnter = 0

        val j0 = scope.launch {
            stateReserve.transitions.validTransitions<MaterialState.Solid, MaterialState.Liquid>()
                .collect { simpleValidTransitions++ }
        }
        val j1 = scope.launch {
            stateReserve.transitions.validTransitions<MaterialState.Liquid, MaterialState.Gas>()
                .collect { simpleValidTransitions++ }
        }
        val j2 = scope.launch {
            stateReserve.transitions
                .validTransitionWithAction<MaterialAction.OnMelted, MaterialState.Solid, MaterialState.Liquid>()
                .collect { validTransitions++ }
        }
        val j3 = scope.launch {
            stateReserve.transitions
                .validTransitionWithAction<MaterialAction.OnVaporized, MaterialState.Liquid, MaterialState.Gas>()
                .collect { validTransitions++ }
        }
        val j4 = scope.launch {
            stateReserve.transitions.inValidTransition<MaterialState>()
                .collect { inValidTransitions++ }
        }
        val j5 = scope.launch {
            stateReserve.transitions.onExit<MaterialState.Solid>()
                .collect { onExit++ }
        }
        val j6 = scope.launch {
            stateReserve.transitions.onEnter<MaterialState.Liquid>()
                .collect { onEnter++ }
        }

        delay(50) // allow transition collectors to subscribe before dispatching
        stateReserve.dispatch(MaterialAction.OnMelted)
        delay(10) // allow background thread to process before awaitState
        assertEquals(MaterialState.Liquid, stateReserve.awaitState())

        stateReserve.dispatch(MaterialAction.OnVaporized)
        delay(10)
        assertEquals(MaterialState.Gas, stateReserve.awaitState())

        stateReserve.dispatch(MaterialAction.OnMelted)
        delay(10)
        assertEquals(MaterialState.Gas, stateReserve.awaitState())

        delay(50) // allow transition collectors to receive all emissions

        assertEquals(2, simpleValidTransitions)
        assertEquals(2, validTransitions)
        assertEquals(1, inValidTransitions)
        assertEquals(1, onExit)
        assertEquals(1, onEnter)

        setOf(j0, j1, j2, j3, j4, j5, j6).forEach { it.cancel() }
        scope.cancel()
    }

    @Test
    fun stateMutabilityTest() = runBlocking {
        var caughtException: Throwable? = null
        val handler = CoroutineExceptionHandler { _, throwable -> caughtException = throwable }
        val scope = CoroutineScope(Dispatchers.Default + SupervisorJob() + handler)
        val stateReserve =
            getStateReserve(
                scope = scope,
                initialState = MaterialState.Quantum(mutableMapOf(1 to MaterialState.Solid)),
                debugMode = true,
                reduce = materialReducer
            )

        assertIs<MaterialState.Quantum>(stateReserve.awaitState())
        stateReserve.dispatch(MaterialAction.OnQuantum(1, MaterialState.Liquid))

        delay(100) // wait for the exception to be caught
        assertIs<IllegalArgumentException>(caughtException)
        stateReserve.terminate()
    }

    @Test
    fun invalidActionForStateTest() = runBlocking {
        val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
        val stateReserve =
            getStateReserve(
                scope = scope,
                initialState = MaterialState.Solid,
                debugMode = false,
                reduce = materialReducer
            )

        var inValidTransitions = 0

        val j0 = scope.launch {
            stateReserve.transitions.inValidTransitionWithAction<MaterialAction, MaterialState>()
                .collect { inValidTransitions++ }
        }

        stateReserve.dispatch(MaterialAction.OnFrozen)
        delay(50) // wait for transition to be collected
        assertEquals(1, inValidTransitions)

        j0.cancel()
        stateReserve.terminate()
    }
}
