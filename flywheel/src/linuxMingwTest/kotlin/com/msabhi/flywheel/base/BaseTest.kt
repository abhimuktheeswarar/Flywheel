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

package com.msabhi.flywheel.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.runBlocking

actual abstract class BaseTest {

    actual fun <T> runTest(block: suspend CoroutineScope.() -> T) {
        runBlocking {
            // Use a child scope so the state machine can be cancelled when the test is done,
            // allowing runBlocking to complete without hanging.
            val childJob = SupervisorJob()
            val childScope = CoroutineScope(coroutineContext + childJob)
            try {
                block(childScope)
            } finally {
                childJob.cancelAndJoin()
            }
        }
    }
}
