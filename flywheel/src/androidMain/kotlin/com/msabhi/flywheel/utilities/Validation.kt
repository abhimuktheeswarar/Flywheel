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

package com.msabhi.flywheel.utilities

import android.util.ArrayMap
import android.util.LongSparseArray
import android.util.SparseArray
import com.msabhi.flywheel.Action
import com.msabhi.flywheel.Reduce
import com.msabhi.flywheel.State
import java.lang.reflect.Field
import java.lang.reflect.Modifier
import java.lang.reflect.ParameterizedType
import kotlin.reflect.KCallable
import kotlin.reflect.KClass


actual fun <S : State> assertStateValues(
    action: Action,
    currentState: S,
    reduce: Reduce<S>,
    mutableStateChecker: MutableStateChecker<S>?,
) {
    val firstState = reduce(action, currentState)
    val secondState = reduce(action, currentState)

    if (firstState != secondState) {
        @Suppress("UNCHECKED_CAST")
        val changedProp = firstState::class.java.declaredFields.asSequence()
            .onEach { it.isAccessible = true }
            .firstOrNull { property ->
                @Suppress("Detekt.TooGenericExceptionCaught")
                try {
                    property.get(firstState) != property.get(secondState)
                } catch (e: Throwable) {
                    false
                }
            }
        if (changedProp != null) {
            throw IllegalArgumentException(
                "Impure reducer used! " +
                        "${changedProp.name} changed from ${changedProp.get(firstState)} " +
                        "to ${changedProp.get(secondState)}. " +
                        "Ensure that your state properties properly implement hashCode."
            )
        } else {
            throw IllegalArgumentException(
                "Impure reducer used! Differing states were provided by the same reducer." +
                        "Ensure that your state properties properly implement hashCode. First state: $firstState -> Second state: $secondState"
            )
        }
    }

    mutableStateChecker?.onStateChanged(firstState)
}


private const val IMMUTABLE_LIST_MESSAGE =
    "Use the immutable listOf(...) method instead. You can append it with `val newList = listA + listB`"
private const val IMMUTABLE_MAP_MESSAGE =
    "Use the immutable mapOf(...) method instead. You can append it with `val newMap = mapA + mapB`"

/**
 * Ensures that the state class is immutable.
 * NOTE: Kotlin collections immutability is a compile-time check only and the underlying classes are
 * mutable so it is impossible to detect them here.
 * Kotlin mutability: https://stackoverflow.com/a/33732403/715633
 *
 * As a result, you may not use MutableList, mutableListOf(...) or the map variants by convention only.
 */
internal fun KClass<*>.assertImmutability() {
    require(java.isData) { "State must be a data class! - ${this::class.simpleName}" }

    fun Field.isSubtype(vararg classes: KClass<*>): Boolean {
        return classes.any { klass ->
            return when (val returnType = this.type) {
                is ParameterizedType -> klass.java.isAssignableFrom(returnType.rawType as Class<*>)
                else -> klass.java.isAssignableFrom(returnType)
            }
        }
    }

    java.declaredFields
        // During tests, jacoco can add a transient field called jacocoData.
        .filterNot { Modifier.isTransient(it.modifiers) }
        .forEach { prop ->
            when {
                !Modifier.isFinal(prop.modifiers) -> "State property ${prop.name} must be a val, not a var."
                prop.isSubtype(ArrayList::class) -> "You cannot use ArrayList for ${prop.name}.\n$IMMUTABLE_LIST_MESSAGE"
                prop.isSubtype(SparseArray::class) -> "You cannot use SparseArray for ${prop.name}.\n$IMMUTABLE_LIST_MESSAGE"
                prop.isSubtype(LongSparseArray::class) -> "You cannot use LongSparseArray for ${prop.name}.\n$IMMUTABLE_LIST_MESSAGE"
                //prop.isSubtype(SparseArrayCompat::class) -> "You cannot use SparseArrayCompat for ${prop.name}.\n$IMMUTABLE_LIST_MESSAGE"
                prop.isSubtype(ArrayMap::class) -> "You cannot use ArrayMap for ${prop.name}.\n$IMMUTABLE_MAP_MESSAGE"
                prop.isSubtype(HashMap::class) -> "You cannot use HashMap for ${prop.name}.\n$IMMUTABLE_MAP_MESSAGE"
                prop.isSubtype(Function::class, KCallable::class) -> {
                    "You cannot use functions inside state. Only pure data should be represented: ${prop.name}"
                }
                else -> null
            }?.let { throw IllegalArgumentException("Invalid property in state ${this@assertImmutability::class.simpleName}: $it") }
        }
}

/**
 * Since we can only use java reflection, this basically duck types a data class.
 * componentN methods are also used for @PersistState.
 */
internal val Class<*>.isData: Boolean
    get() {
        if (!declaredMethods.any { it.name == "copy\$default" && it.isSynthetic }) {
            return false
        }

        // if the data class property is internal then kotlin appends '$module_name_debug' to the
        // expected function name.
        declaredMethods.firstOrNull { it.name.startsWith("component1") } ?: return false

        declaredMethods.firstOrNull { it.name == "equals" } ?: return false
        declaredMethods.firstOrNull { it.name == "hashCode" } ?: return false
        return true
    }