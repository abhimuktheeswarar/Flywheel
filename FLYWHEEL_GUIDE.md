# Flywheel Library Guide

This document helps AI coding assistants understand how to use the [Flywheel](https://github.com/abhimuktheeswarar/Flywheel) state management library correctly when working in projects that depend on it. Use this as context when suggesting or editing code that uses Flywheel.

**Reference:** [Flywheel GitHub Wiki](https://github.com/abhimuktheeswarar/Flywheel/wiki)

---

## Document structure

- **Platform-agnostic** — Applies to all platforms (JVM, Android, JS, Native, Apple). Use for shared/common code.
- **Platform-specific** — Marked with platform labels (Android, JVM, Apple, JS). Apply only when editing code for that target.

---

## Overview (platform-agnostic)

Flywheel is a Kotlin Multiplatform state management library inspired by Flux, Elm, and Redux. It uses Kotlin Coroutines and structured concurrency. At its core is a **State Machine** based on the **actor model**: "Do not communicate by sharing memory; instead, share memory by communicating."

**Key principle:** State can only be changed by dispatching **Actions** through a **Reducer**. All impure work (API calls, DB, logging, navigation) goes in **SideEffects**.

---

## Core Concepts (platform-agnostic)

### 1. State

- Implements `State` interface
- **Must be immutable** (use `data class` with `val`, `copy()`)
- All properties should have default values (for initial state)
- No business logic inside State
- Prefer simple, flat properties; nest UI-related state (e.g. `ButtonState`) to avoid logic in the view layer

**Immutability check (JVM/Android):** `MutableStateChecker` compares `hashCode()` before/after the next state. If a state instance is mutated (e.g. shared `MutableMap` modified), validation fails with `"State was mutated"` — even when a different instance shares the same collection. Always create new collections in reducers; never reuse or mutate existing ones.

```kotlin
// ✅ Correct
data class CounterState(val counter: Int = 0) : State

data class TodoState(
    val items: List<TodoItem> = emptyList(),
    val loading: Boolean = false,
    val error: Exception? = null,
) : State
```

### 2. Action

- Implements `Action` interface
- Represents intent (what to do)
- Use `sealed interface` with `data class` or `object`
- **Everything flows through actions** — UI clicks, API responses, navigation

```kotlin
// ✅ Correct
sealed interface CounterAction : Action {
    object Increment : CounterAction
    object Decrement : CounterAction
    data class SetCount(val count: Int) : CounterAction
}
```

**Built-in action types:**
- `SkipReducer` — action never reaches reducer (use with `skipMiddleware`)
- `EventAction` — one-off events (toast, snackbar)
- `NavigateAction` — navigation
- `ErrorAction` — error handling (has `exception` property)

### 3. Reducer

- Pure function: `(Action, State) -> State`
- **Must not** mutate existing state; always return a new state via `copy()` or equivalent
- **Must not** do async work, I/O, logging, or analytics
- Use `reducerForAction<A, S>` for type-safe handling
- Use `combineReducers()` or `reduce1.plus(reduce2)` to split logic

```kotlin
// ✅ Correct
val reduce = reducerForAction<CounterAction, CounterState> { action, state ->
    with(state) {
        when (action) {
            is CounterAction.Increment -> copy(counter = counter + 1)
            is CounterAction.Decrement -> copy(counter = counter - 1)
            is CounterAction.SetCount -> copy(counter = action.count)
            else -> state
        }
    }
}
```

### 4. StateReserve

- Holds state and reducer
- Exposes `states: Flow<S>`, `actions: Flow<Action>`, `actionStates: Flow<ActionState>`
- Methods: `dispatch(action)`, `state()`, `awaitState()`, `terminate()`, `restoreState()`
- **Always call `terminate()`** when the StateReserve is no longer needed (e.g. in Android ViewModel `onCleared()`)

**Flow semantics:** `actions` emits as soon as action reaches `dispatch` (before middleware/reducer) — use for navigation, logging. `actionStates` emits after action passes through middleware and reducer — use when SideEffect needs post-reducer state. `states` emits after reducer produces new state — use for UI. `actionStates` emits even when reducer returns the same state. **Subscription timing:** `actions` and `actionStates` use `SharedFlow` with no replay; subscribe **before** dispatching or you may miss events.

**Scoping:** Unlike Redux's single store, Flywheel allows multiple `StateReserve` instances. Recommended: one per screen on Android/Apple; optionally one app-level for auth/config. No global store.

**InitialState:** `InitialState.set(state)` for immediate init; `InitialState.deferredSet()` for restoring after process death — call `restoreState()` once when ready (Android process death recovery).

**Config:** Use `StateReserveConfig` with `scope`, `debugMode`, `checkMutableState`, `assertStateValues`, `enhancedStateMachine`, `ignoreDuplicateState`. **JVM/Android only:** `checkMutableState` and `assertStateValues` run; on JS/Native/Apple they are no-ops.

**Scope:** Use `CoroutineScope(Dispatchers.Default + SupervisorJob() + CoroutineExceptionHandler)`. Use `getDefaultScope()` or `getDefaultStateReserveConfig()` from Flywheel utilities.

- **Android:** Do **not** use `viewModelScope` (it uses `Dispatchers.Main`); provide your own scope with `Dispatchers.Default`.
- **Apple:** Set `ignoreDuplicateState = false` when State is defined in Swift/Objective-C (e.g. for `equals()`/`hashCode()` interop).

### 5. SideEffect

- Handles **all impure work**: API calls, DB, navigation, logging
- Extends `SideEffect<S>`, receives `StateReserve` and `DispatcherProvider`
- Subscribes to `actions`, `actionStates`, or `transitions`
- Use `actionStates` when you need state after reducer; use `actions` when you don't (e.g. navigation)
- Prefer `awaitState()` over `state()` when you need up-to-date state (waits for pending actions)

```kotlin
class LoadMoviesSideEffect(
    private val repo: MoviesRepository,
    stateReserve: StateReserve<MovieState>,
    dispatchers: DispatcherProvider,
) : SideEffect<MovieState>(stateReserve, dispatchers) {
    init {
        actionStates.onEach(::handle).launchIn(scope)
    }
    private fun handle(actionState: ActionState.Always<Action, MovieState>) {
        if (actionState.action is MovieAction.Load && actionState.state.movies.isEmpty()) {
            scope.launch(dispatchers.IO) {
                repo.getMovies().fold(
                    { dispatch(MovieAction.Loaded(it)) },
                    { dispatch(MovieAction.Error(it)) }
                )
            }
        }
    }
}
```

### 6. Middleware

- Optional; intercepts actions before reducer
- Use for: modifying actions, swallowing actions (e.g. `SkipReducer`), enriching with state
- **Do not** use for async/I/O — use SideEffect instead
- Must call `next(action)` exactly once (or not at all if swallowing)
- Use `skipMiddleware` as the **last** middleware to prevent `SkipReducer` actions from reaching reducer. Swallowed actions still appear in `actions` (before middleware), so you can react in UI or SideEffects.
- **Execution context:** Runs in caller's context — UI dispatch → `Dispatchers.Main`; SideEffect dispatch → `Dispatchers.Default`. Avoid heavy work; use SideEffect for I/O.

---

## Collections: Critical Rules (platform-agnostic)

### ❌ Do NOT use mutable collections in State

```kotlin
// ❌ WRONG — on JVM/Android: fails with IllegalArgumentException when debugMode/checkMutableState is true
// On all platforms: causes shared-mutation bugs and violates immutability
data class BadState(val map: MutableMap<String, String> = mutableMapOf()) : State

// ❌ WRONG — mutating and reusing the same map
val reduce: Reduce<BadState> = { action, state ->
    state.map.put(key, value)  // Mutates shared state!
    state.copy(map = state.map)
}
```

### ✅ Use immutable collections

```kotlin
// ✅ Correct
data class GoodState(val map: Map<String, String> = emptyMap()) : State

val reduce: Reduce<GoodState> = { action, state ->
    if (action is UpdateAction) {
        state.copy(map = state.map + (action.key to action.value))
        // Or: state.copy(map = HashMap(state.map).apply { put(key, value) })
    } else state
}
```

### ❌ Do NOT do heavy iteration in reducers

From the [Reducer wiki](https://github.com/abhimuktheeswarar/Flywheel/wiki/Reducer):

> Ideally, avoid iterating over large collections in a reducer, since it makes other actions wait longer in the queue. Do the iteration in a SideEffect and dispatch an action like `UpdateItemsAction(items)` to set the updated collection.

### ❌ Do NOT use full collection replacement with concurrent SideEffects

When multiple SideEffects react to the same trigger (e.g. `TriggerProcessing`) and each reads state, modifies it, and dispatches a **full replacement** action, they can read the same snapshot. The reducer applies them sequentially, so the last dispatch overwrites earlier ones — lost-update problem. **Fix:** dispatch deltas and merge in the reducer.

```kotlin
// ❌ WRONG — ConcurrentStateModificationTest documents this bug
// SideEffect A: reads empty map, adds keys a-0..a-4, dispatches UpdateItems(fullMap)
// SideEffect B: reads same empty map, adds keys b-0..b-4, dispatches UpdateItems(fullMap)
// Result: Only 5 entries (one SideEffect's changes lost)
reduce = { action, state ->
    when (action) {
        is UpdateItems -> state.copy(items = action.items)  // Full replacement
        else -> state
    }
}
```

### ✅ Use delta/merge actions for concurrent updates

Dispatch only the **delta** (new entries to add/remove), and merge in the reducer:

```kotlin
// ✅ Correct — ConcurrentDeltaDispatchTest
sealed interface CollectionAction : Action {
    data class PutItems(val entries: Map<String, Int>) : CollectionAction  // Delta
    data class RemoveItems(val keys: Set<String>) : CollectionAction       // Delta
}

reduce = reducerForAction<CollectionAction, CollectionState> { action, state ->
    when (action) {
        is PutItems -> state.copy(items = state.items + action.entries)
        is RemoveItems -> state.copy(items = state.items - action.keys)
        else -> state
    }
}
```

**Delta patterns (from `TestCollectionState`):** Map → `PutItems(entries)`, `RemoveItems(keys)`; merge with `state.items + action.entries` or `state.items - action.keys`. List → `AddTags(tags)`, `SetTagAt(updates)`, `RemoveTagAt(indices)`. Set → `AddFavorites(elements)`, `RemoveFavorites(elements)`, `ToggleFavorites(elements)`. Always merge into current state in the reducer.

---

## State Machine (platform-agnostic, optional)

Set `enhancedStateMachine = true` in config. Then:

- Use `transitions` flow (emits on state enter/exit) with `onEnter<S>()`, `onExit<S>()`, `validTransitions<From, To>()`, `inValidTransition<S>()`
- Enforce valid transitions in reducer by checking current state first; use `reduceError()` for invalid transitions

```kotlin
val reducer = reducerForAction<MaterialAction, MaterialState> { action, state ->
    when (state) {
        MaterialState.Solid -> when (action) {
            MaterialAction.OnMelted -> MaterialState.Liquid
            else -> reduceError()
        }
        MaterialState.Liquid -> when (action) {
            MaterialAction.OnFrozen -> MaterialState.Solid
            MaterialAction.OnVaporized -> MaterialState.Gas
            else -> reduceError()
        }
        // ...
    }
}
```

---

## Platform-specific notes

### Kotlin Multiplatform (common / shared)

- Add `implementation("com.msabhi:flywheel:1.1.6")` in `commonMain`
- Core concepts (State, Action, Reducer, SideEffect, Middleware) are platform-agnostic
- Uses multithreaded coroutines; handle threading carefully on non-JVM platforms (JS, Native, Apple)
- **Sample:** nativeApp, jsApp — `getDefaultStateReserveConfig`, shared KMP usage

### Android

- **Dependency:** `implementation("com.msabhi:flywheel-android:1.1.6")` in app module
- Use Flywheel with ViewModel; call `stateReserve.terminate()` in `onCleared()`
- Provide `CoroutineScope` with `Dispatchers.Default + SupervisorJob()` (e.g. `getDefaultScope()`); do **not** use `viewModelScope` for StateReserve
- Collect `states` in `lifecycleScope.launchWhenStarted` or `repeatOnLifecycle(Lifecycle.State.STARTED)`
- `checkMutableState` and `assertStateValues` are enforced when `debugMode` is true
- **Sample pattern:** `BaseViewModel` in androidApp passes `initialState`, `reduce`, optional `middlewares`, `config`; exposes `states`, `eventActions`, `navigateActions`; runs `assertImmutability()` on initial state in debug. See `CounterViewModel` for lifecycle-aware collection.

### JVM (standalone)

- Similar to Android; use with any architecture
- `checkMutableState` and `assertStateValues` are enforced when `debugMode` is true

### Apple (Swift / SwiftUI)

- **Dependency:** Swift Package Manager — `.package(url: "https://github.com/abhimuktheeswarar/Flywheel.git", from: "1.1.6")`
- `StateReserveHolder` helps create `StateReserve` from Swift
- State must implement `Equatable` and `Hashable` for `ignoreDuplicateState` behavior on Objective-C interop
- Set `ignoreDuplicateState = false` when State is defined in Swift/Objective-C
- **Sample:** iosApp — `StateReserveHolder`, `CoroutineHelper` for Flow collection from Swift

### JavaScript / Node.js

- Add Flywheel as dependency in common/shared module of KMP project
- `checkMutableState` and `assertStateValues` are no-ops (not enforced)

---

## Quick Reference: Do's and Don'ts (platform-agnostic unless noted)

| Do | Don't |
|----|-------|
| Use immutable State (data class, `val`, `copy()`) | Use `MutableMap`, `MutableList`, or mutable properties in State |
| Use delta actions for collection updates when concurrency is possible | Replace entire collections from SideEffects when multiple can run concurrently |
| Put API/DB/navigation in SideEffect | Put I/O or async logic in Reducer or Middleware |
| Call `terminate()` when StateReserve is no longer needed | Forget to cancel scope / terminate |
| Use `getDefaultScope()` or `Dispatchers.Default + SupervisorJob()` for StateReserve | **Android:** Use `viewModelScope` or `Dispatchers.Main` for StateReserve scope |
| Use `awaitState()` when you need guaranteed current state | Rely on `state()` in SideEffect when order matters |
| Use `reducerForAction`, `combineReducers`, `plus` for reducers | Mutate state in reducer; do heavy iteration in reducer |
| Use `skipMiddleware` for `SkipReducer` actions | Let one-off actions (toast, etc.) reach reducer unnecessarily |

---

## Further Reading

- [Flywheel Wiki — Core Concepts](https://github.com/abhimuktheeswarar/Flywheel/wiki/Core-Concepts)
- [State](https://github.com/abhimuktheeswarar/Flywheel/wiki/State) | [Action](https://github.com/abhimuktheeswarar/Flywheel/wiki/Action) | [Reducer](https://github.com/abhimuktheeswarar/Flywheel/wiki/Reducer)
- [StateReserve](https://github.com/abhimuktheeswarar/Flywheel/wiki/StateReserve) | [SideEffect](https://github.com/abhimuktheeswarar/Flywheel/wiki/SideEffect) | [Middleware](https://github.com/abhimuktheeswarar/Flywheel/wiki/Middleware)
- [Usage (platform-specific)](https://github.com/abhimuktheeswarar/Flywheel/wiki/Usage)
