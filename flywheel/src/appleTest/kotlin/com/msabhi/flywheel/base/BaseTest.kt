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
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import platform.CoreFoundation.CFRunLoopGetCurrent
import platform.CoreFoundation.CFRunLoopRun
import platform.CoreFoundation.CFRunLoopStop

actual abstract class BaseTest {

    actual fun <T> runTest(block: suspend CoroutineScope.() -> T) {
        var error: Throwable? = null
        val scope = MainScope()
        scope.launch {
            try {
                block()
            } catch (t: Throwable) {
                error = t
            } finally {
                CFRunLoopStop(CFRunLoopGetCurrent())
            }
        }
        CFRunLoopRun()
        error?.also { throw it }
    }
}