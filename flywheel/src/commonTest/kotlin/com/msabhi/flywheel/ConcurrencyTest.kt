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

import com.msabhi.flywheel.base.BaseTest
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ConcurrencyTest : BaseTest() {

    /**
     * This doesn't test any actual code, but is a sanity check that our tests can handle main thread
     * coroutines without hanging
     */
    @Test
    fun testMain() {
        runTest {
            withTimeout(2000) {
                var complete = false
                val scope = MainScope()
                val job = scope.launch {
                    delay(300)
                    complete = true
                }

                assertFalse(job.isCompleted)
                assertFalse(complete)

                job.join()
                assertTrue(complete)
            }
        }
    }
}