/**
 * Copyright (c) 2024-2025 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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
package org.angproj.io.buf

import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.test.assertEquals

class MemoryBlockTest {

    @Test
    fun testNullBlock() {
        val nullBlock = MemoryBlock.nullBlock
        assertTrue { nullBlock.isNull() }
        assertEquals(nullBlock.size, 0)
        assertEquals(nullBlock.limit, 0)
        assertEquals(nullBlock.getPointer().ptr, 0L)
        assertEquals(nullBlock.parentBlock, MemoryBlock.nullBlock)
    }
}