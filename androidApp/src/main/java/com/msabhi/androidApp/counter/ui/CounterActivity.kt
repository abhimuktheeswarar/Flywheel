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

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.msabhi.androidApp.common.BaseViewModelFactory
import com.msabhi.androidApp.common.ShowToastAction
import com.msabhi.androidApp.counter.entities.CounterAction
import com.msabhi.androidApp.counter.entities.CounterState
import com.msabhi.androidApp.databinding.ActivityCounterBinding
import com.msabhi.flywheel.EventAction
import com.msabhi.flywheel.utilities.name
import kotlinx.coroutines.flow.collect
import java.math.RoundingMode
import java.text.DecimalFormat

class CounterActivity : AppCompatActivity() {

    @Suppress("PrivatePropertyName")
    private val TAG = "CounterActivity"

    private val viewModel by viewModels<CounterViewModel> {
        BaseViewModelFactory {
            CounterViewModel.get(this)
        }
    }

    private lateinit var binding: ActivityCounterBinding

    private var start = 0L
    private var counter = 0

    private val times = arrayListOf<Long>()
    private val decimalFormat = DecimalFormat("#.##").apply {
        roundingMode = RoundingMode.CEILING
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCounterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonDecrement.setOnClickListener {
            start = System.currentTimeMillis()
            viewModel.dispatch(CounterAction.DecrementAction)
        }

        binding.buttonIncrement.setOnClickListener {
            start = System.currentTimeMillis()
            viewModel.dispatch(CounterAction.IncrementAction)
        }

        binding.buttonReset.setOnClickListener {
            start = 0
            times.clear()
            viewModel.dispatch(CounterAction.ResetAction)
        }

        binding.buttonShowToast.setOnClickListener {
            viewModel.dispatch(ShowToastAction("${System.currentTimeMillis()}"))
        }

        lifecycleScope.launchWhenCreated {
            viewModel.states.collect(::setupViews)
        }

        //Can be handled from a Middleware also
        /*lifecycleScope.launchWhenResumed {
            viewModel.eventActions.collect(::processEvents)
        }*/
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews(state: CounterState) {
        Log.d(TAG, "$counter setupViews = ${state.counter} | ${viewModel.state().counter}")
        if (start != 0L) {
            val consumedTime = System.currentTimeMillis() - start
            binding.textTime.text = "${decimalFormat.format(consumedTime)}ms"
            times.add(consumedTime)
            binding.textAverageTime.text = "${decimalFormat.format(times.average())}ms"
        }

        counter++
        binding.textCount.text = state.counter.toString()
    }

    private fun processEvents(action: EventAction) {
        Log.d(TAG, "processEvents = ${action.name()}")
        when (action) {

            is ShowToastAction -> {
                Toast.makeText(this, "A ${action.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}