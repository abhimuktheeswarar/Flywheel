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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

@Suppress("EXPERIMENTAL_API_USAGE")
class StateMachineTest {

    internal sealed class MaterialState : State {
        object Solid : MaterialState()
        object Liquid : MaterialState()
        object Gas : MaterialState()
        data class Quantum(val states: MutableMap<Int, MaterialState>) :
            MaterialState()
    }

    internal sealed class MaterialAction : Action {
        object OnMelted : MaterialAction()
        object OnFrozen : MaterialAction()
        object OnVaporized : MaterialAction()
        object OnCondensed : MaterialAction()
        data class OnQuantum(val id: Int, val state: MaterialState) :
            MaterialAction()
    }

    internal sealed class MaterialSideEffect {
        object LogMelted : MaterialSideEffect()
        object LogFrozen : MaterialSideEffect()
        object LogVaporized : MaterialSideEffect()
        object LogCondensed : MaterialSideEffect()
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
            StateReserveConfig(scope = scope,
                debugMode = debugMode,
                enhancedStateMachine = true)

        return StateReserve(config, InitialState.set(initialState), reduce, middlewares)
    }

    @Test
    fun materialStateMachineTest(): Unit = runBlockingTest {
        val scope = TestCoroutineScope(SupervisorJob())
        val stateReserve =
            getStateReserve(scope = scope,
                initialState = MaterialState.Solid,
                debugMode = false,
                reduce = materialReducer)

        var simpleValidTransitions = 0
        var validTransitions = 0
        var inValidTransitions = 0
        var onExit = 0
        var onEnter = 0

        val j0 = launch(CoroutineName("j0")) {
            stateReserve.transitions.validTransitions<MaterialState.Solid, MaterialState.Liquid>()
                .collect {
                    simpleValidTransitions++
                }
        }
        val j1 = launch(CoroutineName("j1")) {
            stateReserve.transitions.validTransitions<MaterialState.Liquid, MaterialState.Gas>()
                .collect {
                    simpleValidTransitions++
                }
        }
        val j2 = launch(CoroutineName("j2")) {
            stateReserve.transitions.validTransitionWithAction<MaterialAction.OnMelted, MaterialState.Solid, MaterialState.Liquid>()
                .collect {
                    validTransitions++
                }
        }
        val j3 = launch(CoroutineName("j3")) {
            stateReserve.transitions.validTransitionWithAction<MaterialAction.OnVaporized, MaterialState.Liquid, MaterialState.Gas>()
                .collect {
                    validTransitions++
                }
        }
        val j4 = launch(CoroutineName("j4")) {
            stateReserve.transitions.inValidTransition<MaterialAction, MaterialState>()
                .collect {
                    inValidTransitions++
                }
        }
        val j5 = launch(CoroutineName("j5")) {
            stateReserve.transitions.onExit<MaterialAction.OnMelted, MaterialState.Solid>()
                .collect {
                    onExit++
                }
        }
        val j6 = launch(CoroutineName("j6")) {
            stateReserve.transitions.onEnter<MaterialAction.OnMelted, MaterialState.Liquid>()
                .collect {
                    onEnter++
                }
        }

        val jobs = setOf(j0, j1, j2, j3, j4, j5, j6)

        stateReserve.dispatch(MaterialAction.OnMelted)
        assertEquals(MaterialState.Liquid, stateReserve.awaitState())

        stateReserve.dispatch(MaterialAction.OnVaporized)
        assertEquals(MaterialState.Gas, stateReserve.awaitState())

        //Invalid transition
        stateReserve.dispatch(MaterialAction.OnMelted)
        assertEquals(MaterialState.Gas, stateReserve.awaitState())

        assertEquals(2, simpleValidTransitions)
        assertEquals(2, validTransitions)
        assertEquals(1, inValidTransitions)
        assertEquals(1, onExit)
        assertEquals(1, onEnter)
        jobs.forEach { it.cancel() }
        scope.cancel()
    }

    @Test(expected = IllegalArgumentException::class)
    fun stateMutabilityTest(): Unit = runBlockingTest {
        val scope = TestCoroutineScope(SupervisorJob())
        val stateReserve =
            getStateReserve(scope = scope,
                initialState = MaterialState.Quantum(mutableMapOf(1 to MaterialState.Solid)),
                debugMode = true,
                reduce = materialReducer)

        assertIs<MaterialState.Quantum>(stateReserve.awaitState())
        stateReserve.dispatch(MaterialAction.OnQuantum(1, MaterialState.Liquid))

        stateReserve.terminate()
    }

    @Test
    fun invalidActionForStateTest(): Unit = runBlockingTest {
        val scope = TestCoroutineScope(SupervisorJob())
        val stateReserve =
            getStateReserve(scope = scope,
                initialState = MaterialState.Solid,
                debugMode = false,
                reduce = materialReducer)

        var inValidTransitions = 0

        val j0 = launch(CoroutineName("j0")) {
            stateReserve.transitions.inValidTransition<MaterialAction, MaterialState>()
                .collect {
                    println(it)
                    inValidTransitions++
                }
        }

        stateReserve.dispatch(MaterialAction.OnFrozen)
        assertEquals(1, inValidTransitions)
        j0.cancel()
        stateReserve.terminate()
    }
}