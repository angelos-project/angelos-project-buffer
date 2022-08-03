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
package org.angproj.io.buf.data

import org.angproj.io.buf.BufferOverflowWarning
import org.angproj.io.buf.MutableBufferTest
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

open class MutableDataBufferTest : MutableBufferTest() {
    /**
     * Populates mutable buffer with reference values for testing.
     *
     * @param buf
     */
    fun <B : MutableDataBuffer> populateMutableDataBuffer(buf: B): B {
        buf.setStoreByte(0, refByte)
        buf.setStoreUByte(1, refUByte)
        buf.setStoreChar(2, refChar)
        buf.setStoreShort(4, refShort)
        buf.setStoreUShort(6, refUShort)
        buf.setStoreInt(8, refInt)
        buf.setStoreUInt(12, refUInt)
        buf.setStoreLong(16, refLong)
        buf.setStoreULong(24, refULong)
        buf.setStoreFloat(32, refFloat)
        buf.setStoreDouble(36, refDouble)

        for (idx in 44 until buf.limit) {
            buf.setStoreByte(idx, refArray[idx])
        }
        return buf
    }

    /**
     * Test ReferenceMutableBuffer read from an immutability perspective.
     * This test certifies that the buffer getNext*() methods read values consistently
     * compared to KN ByteArray by utilizing the populateArray method.
     */
    fun testMutableDataBufferRead(buf: DataBuffer) {
        assertEquals(buf.getRetrieveByte(0), refByte)
        assertEquals(buf.getRetrieveUByte(1), refUByte)
        assertEquals(buf.getRetrieveChar(2), refChar)
        assertEquals(buf.getRetrieveShort(4), refShort)
        assertEquals(buf.getRetrieveUShort(6), refUShort)
        assertEquals(buf.getRetrieveInt(8), refInt)
        assertEquals(buf.getRetrieveUInt(12), refUInt)
        assertEquals(buf.getRetrieveLong(16), refLong)
        assertEquals(buf.getRetrieveULong(24), refULong)
        assertEquals(buf.getRetrieveFloat(32).toRawBits(), refFloat.toRawBits())
        assertEquals(buf.getRetrieveDouble(36), refDouble)

        for (idx in 44 until buf.limit) {
            assertEquals(refArray[idx], buf.getRetrieveByte(idx))
        }

        assertFailsWith<BufferOverflowWarning> { buf.getRetrieveByte(refSize) }
        assertFailsWith<BufferOverflowWarning> { buf.getRetrieveUByte(refSize) }
        assertFailsWith<BufferOverflowWarning> { buf.getRetrieveChar(refSize) }
        assertFailsWith<BufferOverflowWarning> { buf.getRetrieveShort(refSize) }
        assertFailsWith<BufferOverflowWarning> { buf.getRetrieveUShort(refSize) }
        assertFailsWith<BufferOverflowWarning> { buf.getRetrieveInt(refSize) }
        assertFailsWith<BufferOverflowWarning> { buf.getRetrieveUInt(refSize) }
        assertFailsWith<BufferOverflowWarning> { buf.getRetrieveLong(refSize) }
        assertFailsWith<BufferOverflowWarning> { buf.getRetrieveULong(refSize) }
        assertFailsWith<BufferOverflowWarning> { buf.getRetrieveFloat(refSize) }
        assertFailsWith<BufferOverflowWarning> { buf.getRetrieveDouble(refSize) }

        if (buf is MutableDataBuffer) {
            assertFailsWith<BufferOverflowWarning> { buf.setStoreByte(refSize, refByte) }
            assertFailsWith<BufferOverflowWarning> { buf.setStoreUByte(refSize, refUByte) }
            assertFailsWith<BufferOverflowWarning> { buf.setStoreChar(refSize, refChar) }
            assertFailsWith<BufferOverflowWarning> { buf.setStoreShort(refSize, refShort) }
            assertFailsWith<BufferOverflowWarning> { buf.setStoreUShort(refSize, refUShort) }
            assertFailsWith<BufferOverflowWarning> { buf.setStoreInt(refSize, refInt) }
            assertFailsWith<BufferOverflowWarning> { buf.setStoreUInt(refSize, refUInt) }
            assertFailsWith<BufferOverflowWarning> { buf.setStoreLong(refSize, refLong) }
            assertFailsWith<BufferOverflowWarning> { buf.setStoreULong(refSize, refULong) }
            assertFailsWith<BufferOverflowWarning> { buf.setStoreFloat(refSize, refFloat) }
            assertFailsWith<BufferOverflowWarning> { buf.setStoreDouble(refSize, refDouble) }
        }
    }

    /**
     * Test ReferenceMutableBuffer from a write/read perspective with native endianness.
     * This test certifies that the buffer writes and reads values consistently.
     *
     */
    fun testMutableDataBufferWrite(buf: MutableDataBuffer) {
        buf.setStoreByte(0, refByte)
        buf.setStoreUByte(1, refUByte)
        buf.setStoreChar(2, refChar)
        buf.setStoreShort(4, refShort)
        buf.setStoreUShort(6, refUShort)
        buf.setStoreInt(8, refInt)
        buf.setStoreUInt(12, refUInt)
        buf.setStoreLong(16, refLong)
        buf.setStoreULong(24, refULong)
        buf.setStoreFloat(32, refFloat)
        buf.setStoreDouble(36, refDouble)

        buf.limit(44)

        assertEquals(buf.getRetrieveByte(0), refByte)
        assertEquals(buf.getRetrieveUByte(1), refUByte)
        assertEquals(buf.getRetrieveChar(2), refChar)
        assertEquals(buf.getRetrieveShort(4), refShort)
        assertEquals(buf.getRetrieveUShort(6), refUShort)
        assertEquals(buf.getRetrieveInt(8), refInt)
        assertEquals(buf.getRetrieveUInt(12), refUInt)
        assertEquals(buf.getRetrieveLong(16), refLong)
        assertEquals(buf.getRetrieveULong(24), refULong)
        assertEquals(buf.getRetrieveFloat(32).toRawBits(), refFloat.toRawBits())
        assertEquals(buf.getRetrieveDouble(36), refDouble)
    }

    /**
     * Test ReferenceMutableBuffer from a write/read perspective with opposite endianness.
     * This test certifies that the buffer writes and reads values consistently.
     *
     */
    fun testMutableDataBufferWriteReverse(buf: MutableDataBuffer) {
        buf.endian = reverseEndianness(buf)

        buf.setStoreByte(0, refByte)
        buf.setStoreUByte(1, refUByte)
        buf.setStoreChar(2, refChar)
        buf.setStoreShort(4, refShort)
        buf.setStoreUShort(6, refUShort)
        buf.setStoreInt(8, refInt)
        buf.setStoreUInt(12, refUInt)
        buf.setStoreLong(16, refLong)
        buf.setStoreULong(24, refULong)
        buf.setStoreFloat(32, refFloat)
        buf.setStoreDouble(36, refDouble)

        buf.limit(44)

        assertEquals(buf.getRetrieveByte(0), refByte)
        assertEquals(buf.getRetrieveUByte(1), refUByte)
        assertEquals(buf.getRetrieveChar(2), refChar)
        assertEquals(buf.getRetrieveShort(4), refShort)
        assertEquals(buf.getRetrieveUShort(6), refUShort)
        assertEquals(buf.getRetrieveInt(8), refInt)
        assertEquals(buf.getRetrieveUInt(12), refUInt)
        assertEquals(buf.getRetrieveLong(16), refLong)
        assertEquals(buf.getRetrieveULong(24), refULong)
        assertEquals(buf.getRetrieveFloat(32).toRawBits(), refFloat.toRawBits())
        assertEquals(buf.getRetrieveDouble(36), refDouble)
    }

    fun testReset(buf: MutableDataBuffer) {
        buf.reset()
        for(index in 0 until buf.limit) {
            assertEquals(buf.getRetrieveByte(index), 0)
        }
    }
}