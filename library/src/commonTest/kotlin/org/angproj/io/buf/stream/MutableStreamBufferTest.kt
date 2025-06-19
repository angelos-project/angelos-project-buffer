/**
 * Copyright (c) 2022 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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
package org.angproj.io.buf.stream

import org.angproj.io.buf.BufferOverflowWarning
import org.angproj.io.buf.MutableBufferTest
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

open class MutableStreamBufferTest : MutableBufferTest() {
    /**
     * Populates mutable buffer with reference values for testing.
     *
     * @param buf
     */
    fun <B : MutableStreamBuffer> populateMutableStreamBuffer(buf: B): B {
        buf.setWriteByte(refByte)
        buf.setWriteUByte(refUByte)
        buf.setWriteChar(refChar)
        buf.setWriteShort(refShort)
        buf.setWriteUShort(refUShort)
        buf.setWriteInt(refInt)
        buf.setWriteUInt(refUInt)
        buf.setWriteLong(refLong)
        buf.setWriteULong(refULong)
        buf.setWriteFloat(refFloat)
        buf.setWriteDouble(refDouble)

        for (idx in buf.position until buf.limit) {
            buf.setWriteByte(refArray[idx])
        }
        buf.clear()
        return buf
    }

    /**
     * Test ReferenceMutableBuffer read from an immutability perspective.
     * This test certifies that the buffer getNext*() methods read values consistently
     * compared to KN ByteArray by utilizing the populateArray method.
     */
    fun testMutableStreamBufferRead(buf: StreamBuffer) {
        assertEquals(buf.getReadByte(), refByte)
        assertEquals(buf.getReadUByte(), refUByte)
        assertEquals(buf.getReadChar(), refChar)
        assertEquals(buf.getReadShort(), refShort)
        assertEquals(buf.getReadUShort(), refUShort)
        assertEquals(buf.getReadInt(), refInt)
        assertEquals(buf.getReadUInt(), refUInt)
        assertEquals(buf.getReadLong(), refLong)
        assertEquals(buf.getReadULong(), refULong)
        assertEquals(buf.getReadFloat().toRawBits(), refFloat.toRawBits())
        assertEquals(buf.getReadDouble(), refDouble)

        assertEquals(buf.position, 44)
        for (idx in buf.position until buf.limit) {
            assertEquals(refArray[buf.position], buf.getReadByte())
        }
        assertEquals(buf.position, refSize)
        assertFailsWith<BufferOverflowWarning> { buf.getReadByte() }
        assertFailsWith<BufferOverflowWarning> { buf.getReadUByte() }
        assertFailsWith<BufferOverflowWarning> { buf.getReadChar() }
        assertFailsWith<BufferOverflowWarning> { buf.getReadShort() }
        assertFailsWith<BufferOverflowWarning> { buf.getReadUShort() }
        assertFailsWith<BufferOverflowWarning> { buf.getReadInt() }
        assertFailsWith<BufferOverflowWarning> { buf.getReadUInt() }
        assertFailsWith<BufferOverflowWarning> { buf.getReadLong() }
        assertFailsWith<BufferOverflowWarning> { buf.getReadULong() }
        assertFailsWith<BufferOverflowWarning> { buf.getReadFloat() }
        assertFailsWith<BufferOverflowWarning> { buf.getReadDouble() }

        if (buf is MutableStreamBuffer) {
            assertFailsWith<BufferOverflowWarning> { buf.setWriteByte(refByte) }
            assertFailsWith<BufferOverflowWarning> { buf.setWriteUByte(refUByte) }
            assertFailsWith<BufferOverflowWarning> { buf.setWriteChar(refChar) }
            assertFailsWith<BufferOverflowWarning> { buf.setWriteShort(refShort) }
            assertFailsWith<BufferOverflowWarning> { buf.setWriteUShort(refUShort) }
            assertFailsWith<BufferOverflowWarning> { buf.setWriteInt(refInt) }
            assertFailsWith<BufferOverflowWarning> { buf.setWriteUInt(refUInt) }
            assertFailsWith<BufferOverflowWarning> { buf.setWriteLong(refLong) }
            assertFailsWith<BufferOverflowWarning> { buf.setWriteULong(refULong) }
            assertFailsWith<BufferOverflowWarning> { buf.setWriteFloat(refFloat) }
            assertFailsWith<BufferOverflowWarning> { buf.setWriteDouble(refDouble) }
        }
        assertEquals(buf.position, refSize)
    }

    /**
     * Test ReferenceMutableBuffer from a write/read perspective with native endianness.
     * This test certifies that the buffer writes and reads values consistently.
     *
     */
    fun testMutableStreamBufferWrite(buf: MutableStreamBuffer) {
        buf.setWriteByte(refByte)
        buf.setWriteUByte(refUByte)
        buf.setWriteChar(refChar)
        buf.setWriteShort(refShort)
        buf.setWriteUShort(refUShort)
        buf.setWriteInt(refInt)
        buf.setWriteUInt(refUInt)
        buf.setWriteLong(refLong)
        buf.setWriteULong(refULong)
        buf.setWriteFloat(refFloat)
        buf.setWriteDouble(refDouble)

        buf.flip()

        assertEquals(buf.getReadByte(), refByte)
        assertEquals(buf.getReadUByte(), refUByte)
        assertEquals(buf.getReadChar(), refChar)
        assertEquals(buf.getReadShort(), refShort)
        assertEquals(buf.getReadUShort(), refUShort)
        assertEquals(buf.getReadInt(), refInt)
        assertEquals(buf.getReadUInt(), refUInt)
        assertEquals(buf.getReadLong(), refLong)
        assertEquals(buf.getReadULong(), refULong)
        assertEquals(buf.getReadFloat().toRawBits(), refFloat.toRawBits())
        assertEquals(buf.getReadDouble(), refDouble)
    }

    /**
     * Test ReferenceMutableBuffer from a write/read perspective with opposite endianness.
     * This test certifies that the buffer writes and reads values consistently.
     *
     */
    fun testMutableStreamBufferWriteReverse(buf: MutableStreamBuffer) {
        buf.endian = reverseEndianness(buf)

        buf.setWriteByte(refByte)
        buf.setWriteUByte(refUByte)
        buf.setWriteChar(refChar)
        buf.setWriteShort(refShort)
        buf.setWriteUShort(refUShort)
        buf.setWriteInt(refInt)
        buf.setWriteUInt(refUInt)
        buf.setWriteLong(refLong)
        buf.setWriteULong(refULong)
        buf.setWriteFloat(refFloat)
        buf.setWriteDouble(refDouble)

        buf.flip()

        assertEquals(buf.getReadByte(), refByte)
        assertEquals(buf.getReadUByte(), refUByte)
        assertEquals(buf.getReadChar(), refChar)
        assertEquals(buf.getReadShort(), refShort)
        assertEquals(buf.getReadUShort(), refUShort)
        assertEquals(buf.getReadInt(), refInt)
        assertEquals(buf.getReadUInt(), refUInt)
        assertEquals(buf.getReadLong(), refLong)
        assertEquals(buf.getReadULong(), refULong)
        assertEquals(buf.getReadFloat().toRawBits(), refFloat.toRawBits())
        assertEquals(buf.getReadDouble(), refDouble)
    }

    fun testImmutableStreamBufferDoubleFlip(buf: ImmutableStreamBuffer) {
        buf.flip(refSize-1)
        assertFailsWith<IllegalStateException> {
            buf.flip(refSize-1)
        }
    }
}