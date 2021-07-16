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

import com.msabhi.flywheel.common.TestCounterAction
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@Suppress("EXPERIMENTAL_API_USAGE")
class TrialTest {

    @Test
    fun flowBehaviourTest() = runBlockingTest {
        val mutableSharedFlow = MutableSharedFlow<Int>(
            replay = 1,
            onBufferOverflow = BufferOverflow.SUSPEND)

        repeat(10) {
            mutableSharedFlow.emit(it)
        }
        val items = mutableSetOf<Int>()

        val job = launch {
            mutableSharedFlow.collect {
                items.add(it)
            }
        }
        job.cancel()

        assertEquals(1, items.size)
    }

    @Test
    fun comparisonTest() {
        val a1 = TestCounterAction.IncrementAction
        val a2 = TestCounterAction.IncrementAction
        assertTrue { a1 === a2 }
        val b1 = TestCounterAction.ForceUpdateAction(1)
        val b2 = TestCounterAction.ForceUpdateAction(1)
        assertFalse { b1 === b2 }
    }
}