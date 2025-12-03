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
import org.angproj.io.buf.seg.Bytes

class DefaultTest : AbstractFixedPoolTest<Bytes, Default>() {

    override val totalSize = Default.totalSize
    override val minSize = Default.segmentSize
    override val maxSize = Default.segmentSize
    override val aboveSize = Int.MAX_VALUE

    override fun createPool(): Default = Default

    override fun allocate(pool: Default, size: Int): Bytes = pool.allocate(size)

    override fun getSize(obj: Bytes): Int = obj.size

    override fun recycle(pool: Default, obj: Bytes) {
        pool.recycle(obj)
    }

    @Test
    fun testAllocateSeveralTimes() {
        val pool = createPool()
        val bytes = pool.allocate(minSize.toInt())
        pool.recycle(bytes)
        val bytes2 = pool.allocate(minSize.toInt())
        pool.recycle(bytes2)
        val bytes3 = pool.allocate()
        pool.recycle(bytes3)
    }
}