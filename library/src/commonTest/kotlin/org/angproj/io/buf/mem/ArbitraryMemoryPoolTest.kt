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

import org.angproj.io.buf.seg.Memory


class ArbitraryMemoryPoolTest : AbstractArbitraryPoolTest<Memory, ArbitraryMemoryPool>() {

    override fun createPool(): ArbitraryMemoryPool =
        ArbitraryMemoryPool(totalSize, minSize, maxSize)

    override fun allocate(pool: ArbitraryMemoryPool, size: Int): Memory =
        pool.allocate(size)

    override fun getSize(obj: Memory): Int = obj.size

    override fun recycle(pool: ArbitraryMemoryPool, obj: Memory) {
        pool.recycle(obj)
    }
}