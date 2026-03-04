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

package com.msabhi.nativeApp.counter.ui

import com.msabhi.flywheel.InitialState
import com.msabhi.flywheel.StateReserve
import com.msabhi.flywheel.attachments.DispatcherProviderImpl
import com.msabhi.flywheel.reducerForAction
import com.msabhi.flywheel.utilities.getDefaultStateReserveConfig
import com.msabhi.flywheel.utilities.skipMiddleware
import com.msabhi.nativeApp.common.ShowNotificationAction
import com.msabhi.nativeApp.counter.domain.middleware.EventMiddleware
import com.msabhi.nativeApp.counter.domain.sideeffects.CounterSideEffect
import com.msabhi.nativeApp.counter.entities.CounterAction
import com.msabhi.nativeApp.counter.entities.CounterState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.math.roundToInt
import kotlin.time.DurationUnit
import kotlin.time.TimeSource

private const val LINE = "─────────────────────────────────────────"
private const val TITLE = "  FLYWHEEL COUNTER  •  Native CLI"

class CounterApp {

    private val appScope = CoroutineScope(SupervisorJob())

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

    private var dispatchCount = 0
    private val times = mutableListOf<Double>()

    init {
        val middlewares = listOf(
            EventMiddleware(appScope, DispatcherProviderImpl).get(),
            skipMiddleware,
        )

        stateReserve = StateReserve(
            config = getDefaultStateReserveConfig(appScope),
            initialState = InitialState.set(CounterState()),
            reduce = reducer,
            middlewares = middlewares,
        )

        CounterSideEffect(stateReserve, DispatcherProviderImpl)

        // Collect notifications from the actions flow and print them to the terminal.
        // Because mutableActions.tryEmit() happens before middleware processing (Flywheel.kt:374),
        // ShowNotificationAction is visible here even though EventMiddleware does not call next().
        // UNDISPATCHED ensures the collector is subscribed before run() can dispatch any actions.
        appScope.launch(start = CoroutineStart.UNDISPATCHED) {
            stateReserve.actions.filterIsInstance<ShowNotificationAction>().collect { action ->
                println("\n  >> ${action.message}")
            }
        }
    }

    fun run() {
        printBanner()

        while (true) {
            printScreen(stateReserve.state())
            print("  Command [i/d/r/n/q]: ")

            val input = readlnOrNull()?.trim()?.lowercase() ?: break
            val mark = TimeSource.Monotonic.markNow()

            when (input) {
                "i" -> {
                    stateReserve.dispatch(CounterAction.IncrementAction)
                    waitForUpdate()
                    recordTime(mark)
                    dispatchCount++
                }

                "d" -> {
                    stateReserve.dispatch(CounterAction.DecrementAction)
                    waitForUpdate()
                    recordTime(mark)
                    dispatchCount++
                }

                "r" -> {
                    times.clear()
                    stateReserve.dispatch(CounterAction.ResetAction)
                    waitForUpdate(extraMs = 100) // allow CounterSideEffect to react
                    recordTime(mark)
                    dispatchCount++
                }

                "n" -> {
                    stateReserve.dispatch(ShowNotificationAction("Hello from Flywheel Native!"))
                    waitForUpdate()
                }

                "q" -> break

                else -> println("  Unknown command. Use i, d, r, n, or q.")
            }
        }

        println("\n  Goodbye!\n")
        appScope.cancel()
    }

    private fun waitForUpdate(extraMs: Long = 0L) {
        runBlocking { delay(150 + extraMs) }
    }

    private fun recordTime(mark: TimeSource.Monotonic.ValueTimeMark) {
        times.add(mark.elapsedNow().toDouble(DurationUnit.MILLISECONDS))
    }

    private fun printBanner() {
        println()
        println(LINE)
        println(TITLE)
        println(LINE)
        println()
        println("  Flywheel is a KMP state management library.")
        println("  This demo runs as a native CLI on macOS and Linux.")
        println()
    }

    private fun printScreen(state: CounterState) {
        val lastMs = if (times.isNotEmpty()) "${times.last().roundToInt()}ms" else "—"
        val avgMs = if (times.isNotEmpty()) "${times.average().roundToInt()}ms" else "—"

        println()
        println(LINE)
        println("  Count: ${state.counter.toString().padStart(6)}   |   Operations: $dispatchCount")
        println("  Last:  ${lastMs.padStart(6)}   |   Average:    $avgMs")
        println(LINE)
        println("  [i] Increment   [d] Decrement")
        println("  [r] Reset       [n] Notification")
        println("  [q] Quit")
        println(LINE)
    }
}
