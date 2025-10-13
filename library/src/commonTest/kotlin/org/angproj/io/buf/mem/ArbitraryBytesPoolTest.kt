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
import kotlin.test.assertFailsWith
import org.angproj.io.buf.seg.Bytes

class ArbitraryBytesPoolTest : AbstractArbitraryPoolTest<Bytes, ArbitraryBytesPool>() {

    override fun createPool(): ArbitraryBytesPool =
        ArbitraryBytesPool(totalSize, minSize, maxSize)

    override fun allocate(pool: ArbitraryBytesPool, size: Int): Bytes =
        pool.allocate(size)

    override fun getSize(obj: Bytes): Int = obj.size

    override fun recycle(pool: ArbitraryBytesPool, obj: Bytes) {
        pool.recycle(obj)
    }

    @Test
    fun testAllocateMaxSmallerThanMin() {
        assertFailsWith<MemoryException> {
            ArbitraryBytesPool(totalSize, maxSize, minSize)
        }
    }

    @Test
    fun testAllocateTotalSmallerThanMax() {
        assertFailsWith<MemoryException> {
            ArbitraryBytesPool(maxSize, minSize, totalSize)
        }
    }
}