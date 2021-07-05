# **Flywheel**

![Maven Central](https://img.shields.io/maven-central/v/com.msabhi/flywheel?style=flat)
[![GitHub License](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)
[![Kotlin](https://img.shields.io/badge/kotlin-1.5.0-blue.svg?logo=kotlin)](http://kotlinlang.org)
![badge-android](https://img.shields.io/badge/platform-android-3DDC84.svg?style=flat)
![badge-jvm](https://img.shields.io/badge/platform-jvm-red.svg?style=flat)
![badge-apple](https://img.shields.io/badge/platform-ios%20%7C%20macos%20%7C%20watchos%20%7C%20tvos-lightgrey?style=flat)
![badge-js](https://img.shields.io/badge/platform-js-yellow.svg?style=flat)
![badge-linux](https://img.shields.io/badge/platform-linux-important.svg?style=flat)
![badge-windows](https://img.shields.io/badge/platform-windows-informational.svg?style=flat)
![badge-native](https://img.shields.io/badge/platform-native-lightgrey.svg?style=flat)

A simple & predictable state management library inspired by [Redux](https://github.com/reactjs/redux). Flywheel is built on top of [Corotuines](https://kotlinlang.org/docs/coroutines-overview.html) using the concepts of [structured concurrency](https://kotlinlang.org/docs/coroutines-basics.html#structured-concurrency). At the core, lies the [State Machine](https://en.wikipedia.org/wiki/Finite-state_machine) which is based on [actor model](https://en.wikipedia.org/wiki/Actor_model).
\
&nbsp;

## **Why Flywheel?**

The goal was to make Redux concept simple, understandable & easy to use in Kotlin based projects. In order to achieve that, we adapted only the core concepts from Redux and slightly modifed excluded Android, Apple or any platform specific dependencies. It is just pure Kotlin. By doing so, you are free to choose your architecture that best suits your codebase, no need to make any big refactor to fit in Flywheel. Don't be fooled by its simplicity, Flywheel got you covered for all practical usecases. Even if we missed anything, it can be easily extended to support your usecases.

- It is a library, not a framework. So you are free to choose your architecture.
- Write code the usual structured way. Thanks to Kotlin Coroutines structured concurrency.
- Do not communicate by sharing memory; instead, share memory by communicating.
\
&nbsp;

## **Getting started**

In Kotlin Multiplatfrom project:

```Kotlin
kotlin {
  sourceSets {
      val commonMain by getting {
          dependencies {
              implementation("com.msabhi:flywheel:1.0.0-RC")
          }
      }
  }
}
```

In Android / Gradle project:

```Kotlin
dependencies {

    implementation("com.msabhi:flywheel:1.0.0-RC")
}
```

## **Usage**

This is how a simple counter example looks like.

```Kotlin
// 1. Define a state.
data class CounterState(val counter: Int = 0) : State

// 2. Define actions that can change the state.
sealed interface CounterAction : Action {

    object IncrementAction : CounterAction

    object DecrementAction : CounterAction
}

// 3. Define a reducer that updates the state based on the action & current state.
val reduce = reducerForAction<CounterAction, CounterState> { action, state ->
    with(state) {
        when (action) {
            is CounterAction.IncrementAction -> copy(counter = counter + 1)
            is CounterAction.DecrementAction -> copy(counter = counter - 1)
        }
    }
}

// 4. Create a StateReserve.
val stateReserve = StateReserve(
    initialState = CounterState(),
    reduce = reduce)

// 5. Listen for state changes
stateReserve.states.collect { state -> println(state.counter) }

// 6. Send actions to StateReserve to update the state.
stateReserve.dispatch(IncrementAction)

```

This covers the fundamentals of Flywheel. To learn more, head on over to our wiki.

## **License**

```License
Copyright (C) 2021 Abhi Muktheeswarar

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
