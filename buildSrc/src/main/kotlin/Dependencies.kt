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

object Versions {

    const val kotlin = "1.5.20"
    const val coroutines = "1.5.0-native-mt"
    const val dokka = "1.4.32"

    object Android {

        const val compileSdk = 30
        const val minSdk = 21
        const val targetSdk = 29
        const val buildTools = "30.0.3"
        const val androidGradlePlugin = "4.2.2"

        const val appCompat = "1.3.0"
        const val core = "1.5.0"
        const val activityKtx = "1.2.3"
        const val fragmentKtx = "1.3.5"
        const val constraintLayout = "2.0.4"
        const val lifecycle = "2.3.1"
        const val recyclerView = "1.2.0"
        const val materialComponents = "1.3.0"

        const val navigation = "2.3.5"

        const val test = "1.3.0"
        const val testExt = "1.1.2"
    }

    object Testing {
        const val junit = "4.13.2"
    }
}

object Dependencies {

    const val androidGradlePlugin =
        "com.android.tools.build:gradle:${Versions.Android.androidGradlePlugin}"
    const val dokka = "org.jetbrains.dokka:dokka-gradle-plugin:${Versions.dokka}"

    object KotlinTest {
        const val common = "org.jetbrains.kotlin:kotlin-test-common:${Versions.kotlin}"
        const val annotations =
            "org.jetbrains.kotlin:kotlin-test-annotations-common:${Versions.kotlin}"
        const val jvm = "org.jetbrains.kotlin:kotlin-test:${Versions.kotlin}"
        const val junit = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin}"
        const val js = "org.jetbrains.kotlin:kotlin-test-js:${Versions.kotlin}"
    }

    object Coroutines {
        const val common = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
        const val android =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    }

    object Android {

        const val appCompat = "androidx.appcompat:appcompat:${Versions.Android.appCompat}"
        const val coreKtx = "androidx.core:core-ktx:${Versions.Android.core}"
        const val activityKtx = "androidx.activity:activity-ktx:${Versions.Android.activityKtx}"
        const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.Android.fragmentKtx}"
        const val constraintLayout =
            "androidx.constraintlayout:constraintlayout:${Versions.Android.constraintLayout}"
        const val recyclerView =
            "androidx.recyclerview:recyclerview:${Versions.Android.recyclerView}"

        const val lifecycleRuntime =
            "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.Android.lifecycle}"
        const val lifecycleViewModel =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.Android.lifecycle}"
        const val lifecycleViewModelExtensions =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.Android.lifecycle}"

        const val materialComponents =
            "com.google.android.material:material:${Versions.Android.materialComponents}"

        const val navigationFragment =
            "androidx.navigation:navigation-fragment-ktx${Versions.Android.navigation}"
        const val navigationUi =
            "androidx.navigation:navigation-ui-ktx${Versions.Android.navigation}"
    }

    object AndroidTest {
        const val core = "androidx.test:core:${Versions.Android.test}"
        const val junit = "androidx.test.ext:junit:${Versions.Android.testExt}"
        const val runner = "androidx.test:runner:${Versions.Android.test}"
        const val rules = "androidx.test:rules:${Versions.Android.test}"
    }

    object Test {

        const val junit = "junit:junit:${Versions.Testing.junit}"
    }
}
