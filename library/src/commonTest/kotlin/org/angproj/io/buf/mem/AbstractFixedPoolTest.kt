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


import org.angproj.io.buf.seg.Segment
import org.angproj.io.buf.util.DataSize
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

abstract class AbstractFixedPoolTest<S : Segment<S>, M : MemoryManager<S>> {

    open val totalSize = DataSize._256B
    open val minSize = DataSize._32B
    open val maxSize = DataSize._128B
    open val aboveSize = DataSize._256B.toInt()

    abstract fun createPool(): M
    abstract fun allocate(pool: M, size: Int): S
    abstract fun getSize(obj: S): Int
    abstract fun recycle(pool: M, obj: S)

    @Test
    fun testConstruction() {
        val pool = createPool()
        assertNotNull(pool)
    }

    @Test
    fun testAllocateWithinBounds() {
        val pool = createPool()
        val obj = allocate(pool, DataSize._32B.toInt())
        assertNotNull(obj)
        assertEquals(DataSize._32B.toInt(), getSize(obj))
    }

    @Test
    fun testAllocateBelowMinThrows() {
        val pool = createPool()
        assertFailsWith<MemoryException> {
            allocate(pool, DataSize.UNKNOWN.toInt())
        }
    }

    @Test
    fun testAllocateAboveMaxThrows() {
        val pool = createPool()
        assertFailsWith<MemoryException> {
            allocate(pool, aboveSize)
        }
    }

    @Test
    fun testRecycle() {
        val pool = createPool()
        val obj = allocate(pool, DataSize._32B.toInt())
        recycle(pool, obj)
    }
}