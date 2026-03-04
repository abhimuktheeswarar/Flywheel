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

package com.msabhi.jsApp.counter.ui

import com.msabhi.flywheel.EventAction
import com.msabhi.flywheel.InitialState
import com.msabhi.flywheel.StateReserve
import com.msabhi.flywheel.attachments.DispatcherProviderImpl
import com.msabhi.flywheel.reducerForAction
import com.msabhi.flywheel.utilities.getDefaultScope
import com.msabhi.flywheel.utilities.getDefaultStateReserveConfig
import com.msabhi.flywheel.utilities.skipMiddleware
import com.msabhi.jsApp.common.ShowNotificationAction
import com.msabhi.jsApp.counter.domain.middleware.EventMiddleware
import com.msabhi.jsApp.counter.domain.sideeffects.CounterSideEffect
import com.msabhi.jsApp.counter.entities.CounterAction
import com.msabhi.jsApp.counter.entities.CounterState
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import kotlin.js.Date
import kotlin.math.roundToInt

class CounterApp(private val scope: CoroutineScope) {

    private val reducer = reducerForAction<CounterAction, CounterState> { action, state ->
        with(state) {
            when (action) {
                is CounterAction.IncrementAction -> copy(counter = counter + 1)
                is CounterAction.DecrementAction -> copy(counter = counter - 1)
                is CounterAction.ResetAction -> copy(counter = 0)
                is CounterAction.ForceUpdateAction -> copy(counter = action.count)
            }
        }
    }

    private val stateReserve: StateReserve<CounterState>

    private var startTime = 0.0
    private val times = mutableListOf<Double>()

    init {
        val middlewares = listOf(
            EventMiddleware(scope, DispatcherProviderImpl).get(),
            skipMiddleware
        )

        stateReserve = StateReserve(
            config = getDefaultStateReserveConfig(scope),
            initialState = InitialState.set(CounterState()),
            reduce = reducer,
            middlewares = middlewares
        )

        CounterSideEffect(stateReserve, DispatcherProviderImpl)
    }

    fun initialize() {
        bindButtons()
        collectState()
        collectEvents()
    }

    private fun bindButtons() {
        document.getElementById("increment-btn")?.addEventListener("click", {
            startTime = Date.now()
            stateReserve.dispatch(CounterAction.IncrementAction)
        })

        document.getElementById("decrement-btn")?.addEventListener("click", {
            startTime = Date.now()
            stateReserve.dispatch(CounterAction.DecrementAction)
        })

        document.getElementById("reset-btn")?.addEventListener("click", {
            startTime = 0.0
            times.clear()
            resetMetrics()
            stateReserve.dispatch(CounterAction.ResetAction)
        })

        document.getElementById("show-notification-btn")?.addEventListener("click", {
            stateReserve.dispatch(ShowNotificationAction("${Date.now().toLong()}"))
        })
    }

    private fun collectState() {
        scope.launch {
            stateReserve.states.collect { state ->
                updateCountDisplay(state)
                updateMetrics()
            }
        }
    }

    private fun collectEvents() {
        scope.launch {
            stateReserve.actions.filterIsInstance<EventAction>().collect { action ->
                if (action is ShowNotificationAction) {
                    showNotification(action.message)
                }
            }
        }
    }

    private fun updateCountDisplay(state: CounterState) {
        document.getElementById("count-display")?.textContent = state.counter.toString()

        if (startTime > 0.0) {
            val elapsed = Date.now() - startTime
            times.add(elapsed)
            startTime = 0.0
        }
    }

    private fun updateMetrics() {
        if (times.isNotEmpty()) {
            val last = times.last()
            val avg = times.average()
            document.getElementById("time-taken")?.textContent = "${last.roundToInt()}ms"
            document.getElementById("average-time")?.textContent = "${avg.roundToInt()}ms"
        }
    }

    private fun resetMetrics() {
        document.getElementById("time-taken")?.textContent = "—"
        document.getElementById("average-time")?.textContent = "—"
    }

    private fun showNotification(message: String) {
        val el = document.getElementById("notification") ?: return
        el.textContent = message
        el.classList.remove("hidden")
        el.classList.add("visible")
        window.setTimeout({
            el.classList.remove("visible")
            el.classList.add("hidden")
        }, 3000)
    }
}
