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

package com.msabhi.androidApp.counter.ui

import android.content.Context
import com.msabhi.androidApp.base.SimpleBaseViewModel
import com.msabhi.androidApp.counter.entities.CounterState
import com.msabhi.flywheel.InitialState

class SimpleCounterViewModel(initialState: CounterState) :
    SimpleBaseViewModel<CounterState>(initialState = initialState) {

    fun increment() {
        setState { copy(counter = counter + 1) }
    }

    fun decrement() {
        setState { copy(counter = counter - 1) }
    }

    fun reset() {
        setState { copy(counter = 0) }
    }

    fun forceUpdate(count: Int) {
        setState { copy(counter = count) }
    }

    companion object {


        fun get(context: Context): SimpleCounterViewModel {
            val initialState = InitialState.set(CounterState())
            return SimpleCounterViewModel(initialState = initialState.state!!)
        }
    }
}