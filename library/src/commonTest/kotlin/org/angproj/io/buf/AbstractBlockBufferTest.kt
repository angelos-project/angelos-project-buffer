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

import kotlin.test.*


abstract class AbstractBlockBufferTest<E: BlockBuffer>: AbstractBufferTest<E>() {

    protected abstract val txtLen: Int

    abstract override fun setInput(): E

    @Test
    fun testEquals() {
        val diff = setInput()
        diff.segment.setByte(txtLen-1, (diff.segment.getByte(txtLen-1)+1).toByte())
        assertNotEquals(diff, setInput())
        assertTrue{setInput().contentEquals(setInput())}
    }

    @Test
    fun getCapacity() {
        assertEquals(setInput().capacity, txtLen)
    }

    @Test
    fun getLimit() {
        assertEquals(setInput().limit, txtLen)
    }

    @Test
    fun limitAt() {
        val buf = setInput()

        assertFailsWith<IllegalArgumentException> { buf.limitAt(-1) }
        assertFailsWith<IllegalArgumentException> { buf.limitAt(buf.limit + 1) }

        buf.limitAt(txtLen - 5)
        assertEquals(buf.limit, txtLen - 5)
    }

    @Test
    fun clear() {
        val buf = setInput()
        assertEquals(buf.limit, buf.capacity)

        buf.limitAt(txtLen - 5)
        assertEquals(buf.limit, txtLen - 5)

        buf.clear()
        assertEquals(buf.limit, txtLen)
    }

    @Test
    fun asBinary() {
        assertIs<Binary>(setInput().asBinary())
    }
}