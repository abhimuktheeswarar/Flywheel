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

package com.msabhi.flywheel.common

import com.msabhi.flywheel.Action
import com.msabhi.flywheel.State

sealed interface CollectionAction : Action {

    object TriggerProcessing : CollectionAction

    data class UpdateItems(val items: Map<String, Int>) : CollectionAction

    // Delta actions for Map
    data class PutItems(val entries: Map<String, Int>) : CollectionAction
    data class RemoveItems(val keys: Set<String>) : CollectionAction

    // Delta actions for List
    data class AddTags(val tags: List<String>) : CollectionAction
    data class SetTagAt(val updates: Map<Int, String>) : CollectionAction
    data class RemoveTagAt(val indices: Set<Int>) : CollectionAction

    // Delta actions for Set
    data class AddFavorites(val elements: Set<String>) : CollectionAction
    data class RemoveFavorites(val elements: Set<String>) : CollectionAction
    data class ToggleFavorites(val elements: Set<String>) : CollectionAction

    // Scalar action
    data class SetLabel(val label: String) : CollectionAction
}

data class CollectionState(
    val items: Map<String, Int> = emptyMap(),
    val tags: List<String> = emptyList(),
    val favorites: Set<String> = emptySet(),
    val label: String = "",
) : State
