/**
 * Copyright (c) 2025 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
 *
 * This software is available under the terms of the MIT license. Parts are licensed
 * under different terms if stated. The legal terms are attached to the LICENSE file
 * and are made available on:
 *
 *      https://opensource.org/licenses/MIT
 *
 * SPDX-License-Identifier: MIT
 *
 * Contributors:
 *      Kristoffer Paulsson - initial implementation
 */
package org.angproj.io.buf.mem

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertNotNull

class MemoryManagerTest {

    @Test
    fun testMemoryManagerNullManager() {
        assertNotNull(MemoryManager.nullManager)
    }

    @Test
    fun testBytesPoolIsNull() {
        assertFalse { BytesPool.nullManager == MemoryManager.nullManager }
    }

    @Test
    fun testMemoryPoolIsNull() {
        assertFalse { MemoryPool.nullManager == MemoryManager.nullManager }
    }

    @Test
    fun testModelPoolIsNull() {
        assertFalse { ModelPool.nullManager == MemoryManager.nullManager }
    }
}