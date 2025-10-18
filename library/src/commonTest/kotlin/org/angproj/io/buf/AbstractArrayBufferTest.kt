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

import org.angproj.io.buf.seg.SegmentException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

abstract class AbstractArrayBufferTest<T>: AbstractBufferTest<ArrayBuffer<T>>() {

    protected val capValue: Int = 128
    protected abstract val refValue: T

    abstract override fun setInput(): ArrayBuffer<T>

    @Test
    fun getCapacity() {
        assertEquals(setInput().capacity, capValue)
    }

    @Test
    fun getLimit() {
        val buf = setInput()
        assertEquals(buf.limit, buf.size)
    }

    @Test
    fun getIndices() {
        val lb = setInput()
        val i = lb.indices
        assertEquals(i.first, 0)
        assertEquals(i.last, lb.limit - 1)
    }

    @Test
    fun getLastIndex() {
        val lb = setInput()
        assertEquals(lb.lastIndex, lb.limit - 1)
    }

    @Test
    fun testGetSet() {
        val lb = setInput()
        lb.forEach { assertEquals(it, refValue) }

        assertFailsWith<SegmentException> { lb[-1] }
        assertFailsWith<SegmentException> { lb[-1] = refValue }

        assertFailsWith<SegmentException> { lb[lb.limit] }
        assertFailsWith<SegmentException> { lb[lb.limit] = refValue }
    }

    @Test
    fun testNullBuffer() {
        assertTrue { FlowBuffer.nullBuffer.isNull() }
        assertEquals(FlowBuffer.nullBuffer.size, 0)
    }

    @Test
    fun testIterator() {
        setInput().forEach { assertEquals(it, refValue) }
    }
}