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

import com.msabhi.flywheel.utilities.isData
import org.junit.Assert
import org.junit.Test

class MutabilityHelperTest {

    @Test
    fun isData() {
        Assert.assertTrue(TestDataClass::class.java.isData)
        Assert.assertFalse(String::class.java.isData)
    }

    data class TestDataClass(
        internal val foo: Int,
    )
}

