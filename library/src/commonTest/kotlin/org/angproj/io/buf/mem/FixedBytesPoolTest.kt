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


import org.angproj.io.buf.util.DataSize
import org.angproj.io.buf.seg.Bytes
import kotlin.test.Test

class FixedBytesPoolTest : AbstractFixedPoolTest<Bytes, FixedBytesPool>() {

    override val totalSize = DataSize._256B
    override val minSize = DataSize._32B
    override val maxSize = DataSize._32B // Fixed pool: minSize == maxSize

    override fun createPool(): FixedBytesPool =
        FixedBytesPool(totalSize, minSize)

    override fun allocate(pool: FixedBytesPool, size: Int): Bytes =
        pool.allocate(size)

    override fun getSize(obj: Bytes): Int = obj.size

    override fun recycle(pool: FixedBytesPool, obj: Bytes) {
        pool.recycle(obj)
    }

    @Test
    fun testAllocateSeveralTimes() {
        val pool = createPool()
        val bytes = pool.allocate(minSize.toInt())
        pool.recycle(bytes)
        val bytes2 = pool.allocate(minSize.toInt())
        pool.recycle(bytes2)
        pool.dispose()
    }
}