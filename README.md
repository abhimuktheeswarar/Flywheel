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

\
&nbsp;
A simple and predictable state management library inspired by [Flux](https://facebook.github.io/flux/docs/in-depth-overview) + [Elm](https://guide.elm-lang.org/architecture/) = [Redux](https://github.com/reactjs/redux). Flywheel is built on top of [Corotuines](https://kotlinlang.org/docs/coroutines-overview.html) using the concepts of [structured concurrency](https://kotlinlang.org/docs/coroutines-basics.html#structured-concurrency). At the core, lies the [State Machine](https://en.wikipedia.org/wiki/Finite-state_machine) which is based on [actor model](https://en.wikipedia.org/wiki/Actor_model).
\
&nbsp;

## **Why Flywheel?**

The goal was to make the state management concept of Redux simple, understandable & easy to use in Kotlin based projects. To achieve that, we adapted only the core concepts from Redux and slightly modified them. We excluded Android, Apple or any platform-specific dependencies. It is just pure Kotlin. By doing so, you are free to choose your architecture that best suits your codebase, no need to make any big refactor to fit in Flywheel. Don't be fooled by its simplicity, Flywheel got you covered for all practical use-cases. Even if we missed anything, it can be easily extended to support your use cases.
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

1. Define a state.

    ```Kotlin
    data class CounterState(val counter: Int = 0) : State
    ```

2. Define actions that can change the state.

    ```Kotlin
    sealed interface CounterAction : Action {

        object IncrementAction : CounterAction

        object DecrementAction : CounterAction
    }
    ```

3. Define a reducer that updates the state based on the action & current state.

    ```Kotlin
    val reduce = reducerForAction<CounterAction, CounterState> { action, state ->
        with(state) {
            when (action) {
                is CounterAction.IncrementAction -> copy(counter = counter + 1)
                is CounterAction.DecrementAction -> copy(counter = counter - 1)
            }
        }
    }
    ```

4. Create a StateReserve.

    ```Kotlin
    val stateReserve = StateReserve(
        initialState = CounterState(),
        reduce = reduce)
    ```

5. Listen for state changes

    ```Kotlin
    stateReserve.states.collect { state -> println(state.counter) }
    ```

6. Send actions to StateReserve to update the state.

    ```Kotlin
    stateReserve.dispatch(IncrementAction)
    ```

To learn more about Flywheel, head on over to our [wiki](https://github.com/abhimuktheeswarar/Flywheel/wiki).
\
&nbsp;

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
