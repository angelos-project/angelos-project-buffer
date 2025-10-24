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

import org.angproj.io.buf.util.DataSize
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

abstract class AbstractFlowBufferTest<E: AbstractFlowBuffer>: AbstractBufferTest<E>() {

    protected abstract val posValue: Int

    abstract override fun setInput(): E


    @Test
    fun getCapacity() {
        assertEquals(setInput().capacity, DataSize._1K.toInt())
    }

    @Test
    fun getPosition() {
        assertEquals(setInput().position, posValue)
    }

    @Test
    fun positionAt() {
        val buf = setInput()

        buf.positionAt(posValue - 5)
        assertEquals(buf.position, posValue - 5)

        assertFailsWith<BufferException> { buf.positionAt(buf.mark-1) }
        assertFailsWith<BufferException> { buf.positionAt(buf.limit+1) }


    }

    @Test
    fun getLimit() {
        assertEquals(setInput().limit, DataSize._1K.toInt())
    }

    @Test
    fun limitAt() {
        val buf = setInput()
        assertEquals(buf.position, posValue)

        buf.limitAt(posValue - 5)
        assertEquals(buf.limit, posValue - 5)
        assertEquals(buf.position, posValue - 5)

        assertFailsWith<BufferException> { buf.limitAt(buf.mark-1) }
        assertFailsWith<BufferException> { buf.limitAt(buf.capacity+1) }

        buf.clear()
        buf.positionAt(buf.capacity / 2)
        buf.markAt()
        buf.limitAt(buf.mark)
        assertEquals(buf.mark, buf.limit)

        buf.clear()
        buf.positionAt(buf.capacity / 2)
        buf.markAt()
        buf.limitAt(buf.capacity)
        assertEquals(buf.capacity, buf.limit)
    }

    @Test
    fun getMark() {
        assertEquals(setInput().mark, 0)
    }

    @Test
    fun markAt() {
        val buf = setInput()

        assertEquals(buf.mark, 0)
        assertEquals(buf.position, posValue)

        buf.markAt()
        assertEquals(buf.mark, buf.position)
    }

    @Test
    fun reset() {
        val buf = setInput()

        assertEquals(buf.mark, 0)
        assertEquals(buf.position, posValue)

        buf.reset()
        assertEquals(buf.mark, 0)
        assertEquals(buf.position, 0)
    }

    @Test
    fun clear() {
        val buf = setInput()
        assertEquals(buf.limit, buf.capacity)

        buf.markAt()
        buf.limitAt(posValue)

        assertEquals(buf.position, posValue)
        assertEquals(buf.mark, posValue)
        assertEquals(buf.limit, posValue)

        buf.clear()
        assertEquals(buf.limit, buf.capacity)
        assertEquals(buf.mark, 0)
        assertEquals(buf.position, 0)
    }

    @Test
    fun flip() {
        val buf = setInput()
        assertEquals(buf.limit, buf.capacity)
        assertEquals(buf.position, posValue)

        buf.markAt()
        assertEquals(buf.mark, posValue)

        buf.flip()
        assertEquals(buf.limit, posValue)
        assertEquals(buf.mark, 0)
        assertEquals(buf.position, 0)
    }

    @Test
    fun rewind() {
        val buf = setInput()

        assertEquals(buf.mark, 0)
        assertEquals(buf.position, posValue)

        buf.markAt()
        assertEquals(buf.mark, posValue)

        buf.rewind()
        assertEquals(buf.mark, 0)
        assertEquals(buf.position, 0)
    }

    @Test
    fun getRemaining() {
        assertEquals(setInput().remaining, DataSize._1K.toInt() - posValue)
    }

    @Test
    fun testNullBuffer() {
        assertTrue{ AbstractFlowBuffer.nullBuffer.isNull() }
        assertEquals(AbstractFlowBuffer.nullBuffer.size, 0)
    }
}