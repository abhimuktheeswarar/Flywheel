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

package com.msabhi.jsApp

import com.msabhi.flywheel.utilities.getMainScope
import com.msabhi.jsApp.counter.ui.CounterApp
import kotlinx.browser.document

fun main() {
    val scope = getMainScope()

    document.addEventListener("DOMContentLoaded", {
        setupNavigation()
        CounterApp(scope).initialize()
    })
}

private fun setupNavigation() {
    val homeScreen = document.getElementById("home-screen")
    val counterScreen = document.getElementById("counter-screen")

    document.getElementById("counter-example-btn")?.addEventListener("click", {
        homeScreen?.classList?.add("hidden")
        counterScreen?.classList?.remove("hidden")
    })

    document.getElementById("back-btn")?.addEventListener("click", {
        counterScreen?.classList?.add("hidden")
        homeScreen?.classList?.remove("hidden")
    })
}
