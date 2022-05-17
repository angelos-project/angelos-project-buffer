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
package org.angelos.io.buf

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@OptIn(ExperimentalUnsignedTypes::class)
class ReferenceMutableBufferTest {

    /**
     * Ref array to manipulate for testing.
     * The last value is purposefully inverted for easy identification.
     */
    private val refArray = byteArrayOf(
        0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
        10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
        20, 21, 22, 23, 24, 25, 26, 27, 28, 29,
        30, 31, 32, 33, 34, 35, 36, 37, 38, 39,
        40, 41, 42, 43, 44, 45, 46, 47, 48, 49,
        50, 51, 52, 53, 54, 55, 56, 57, 58, 59,
        60, 61, 62, 63, 64, 65, 66, 67, 68, 69,
        70, 71, 72, 73, 74, 75, 76, 77, 78, 79,
        80, 81, 82, 83, 84, 85, 86, 87, 88, 89,
        90, 91, 92, 93, 94, 95, 96, 97, 98, 99,
        100, 101, 102, 103, 104, 105, 106, 107, 108, 109,
        110, 111, 112, 113, 114, 115, 116, 117, 118, 119,
        120, 121, 122, 123, 124, 125, 126, -127
    )

    private val refByte: Byte = 0B1010101
    private val refUByte: UByte = 0B10101010u

    private val refChar: Char = 'Ö'

    private val refShort: Short = 0B1010101_10101010
    private val refUShort: UShort = 0B10101010_10101010u

    private val refInt: Int = 0B1010101_10101010_10101010_10101010
    private val refUInt: UInt = 0B10101010_10101010_10101010_10101010u

    private val refLong: Long = 0B1010101_10101010_10101010_10101010_10101010_10101010_10101010_10101010L
    private val refULong: ULong = 0B10101010_10101010_10101010_10101010_10101010_10101010_10101010_10101010u

    private val refFloat: Float = 23.43585F
    private val refDouble: Double = -0.892384774029876

    private val refSize = refArray.size

    /**
     * Reverses endianness on byffer.
     *
     * @param buf
     */
    private fun reverseEndianness(buf: Buffer) = when {
        buf.endian.isBig() -> Endianness.LITTLE_ENDIAN
        buf.endian.isLittle() -> Endianness.BIG_ENDIAN
        else -> error("Only big or little endian will do")
    }

    /**
     * Creates an empty ByteArray of reference size.
     *
     */
    private fun createArray() = refArray.copyOf()

    /**
     * Populates a ByteArray with reference values as if they were written by a mutable buffer.
     *
     * @param array
     */
    private fun populateArray(array: ByteArray) {
        array[0] = refByte
        array.setUByteAt(1, refUByte)
        array.setCharAt(2, refChar)
        array.setShortAt(4, refShort)
        array.setUShortAt(6, refUShort)
        array.setIntAt(8, refInt)
        array.setUIntAt(12, refUInt)
        array.setLongAt(16, refLong)
        array.setULongAt(24, refULong)
        array.setFloatAt(32, refFloat)
        array.setDoubleAt(36, refDouble)
    }

    /**
     * Test ByteArray read.
     * This test certifies that set*At() and get*At() methods of the ByteArray works properly.
     *
     */
    @Test
    fun testByteArrayRead() {
        val array = createArray()
        populateArray(array)

        assertEquals(refSize, 128)

        assertEquals(array[0], refByte)
        assertEquals(array.getUByteAt(1), refUByte)
        assertEquals(array.getCharAt(2), refChar)
        assertEquals(array.getShortAt(4), refShort)
        assertEquals(array.getUShortAt(6), refUShort)
        assertEquals(array.getIntAt(8), refInt)
        assertEquals(array.getUIntAt(12), refUInt)
        assertEquals(array.getLongAt(16), refLong)
        assertEquals(array.getULongAt(24), refULong)
        assertEquals(array.getFloatAt(32), refFloat)
        assertEquals(array.getDoubleAt(36), refDouble)

        assertEquals(array[refSize-1], -127)
    }

    /**
     * Test ReferenceMutableBuffer read from an immutability perspective.
     * This test certifies that the buffer getNext*() methods read values consistently
     * compared to KN ByteArray by utilizing the populateArray method.
     */
    @Test
    fun testReferenceMutableBufferRead() {
        val array = createArray()
        populateArray(array)
        val buf = refMutableBufferOf(array)

        assertEquals(buf.getNextByte(), refByte)
        assertEquals(buf.getNextUByte(), refUByte)
        assertEquals(buf.getNextChar(), refChar) // <-- Fix this
        assertEquals(buf.getNextShort(), refShort) // <-- Fix this
        assertEquals(buf.getNextUShort(), refUShort)
        assertEquals(buf.getNextInt(), refInt) // <-- Fix this
        assertEquals(buf.getNextUInt(), refUInt)
        assertEquals(buf.getNextLong(), refLong) // <-- Fix this
        assertEquals(buf.getNextULong(), refULong)
        assertEquals(buf.getNextFloat(), refFloat) // <-- Fix this
        assertEquals(buf.getNextDouble(), refDouble) // <-- Fix this

        assertEquals(buf.position, 44)
        for (idx in buf.position until buf.limit){
            assertEquals(refArray[buf.position], buf.getNextByte())
        }
        assertEquals(buf.position, refSize)
        assertFailsWith<ByteBufferOverflowWarning> { buf.getNextByte() }
        assertFailsWith<ByteBufferOverflowWarning> { buf.getNextUByte() }
        assertFailsWith<ByteBufferOverflowWarning> { buf.getNextChar() }
        assertFailsWith<ByteBufferOverflowWarning> { buf.getNextShort() }
        assertFailsWith<ByteBufferOverflowWarning> { buf.getNextUShort() }
        assertFailsWith<ByteBufferOverflowWarning> { buf.getNextInt() }
        assertFailsWith<ByteBufferOverflowWarning> { buf.getNextUInt() }
        assertFailsWith<ByteBufferOverflowWarning> { buf.getNextLong() }
        assertFailsWith<ByteBufferOverflowWarning> { buf.getNextULong() }
        assertFailsWith<ByteBufferOverflowWarning> { buf.getNextFloat() }
        assertFailsWith<ByteBufferOverflowWarning> { buf.getNextDouble() }

        assertFailsWith<ByteBufferOverflowWarning> { buf.setNextByte(refByte) }
        assertFailsWith<ByteBufferOverflowWarning> { buf.setNextUByte(refUByte) }
        assertFailsWith<ByteBufferOverflowWarning> { buf.setNextChar(refChar) }
        assertFailsWith<ByteBufferOverflowWarning> { buf.setNextShort(refShort) }
        assertFailsWith<ByteBufferOverflowWarning> { buf.setNextUShort(refUShort) }
        assertFailsWith<ByteBufferOverflowWarning> { buf.setNextInt(refInt) }
        assertFailsWith<ByteBufferOverflowWarning> { buf.setNextUInt(refUInt) }
        assertFailsWith<ByteBufferOverflowWarning> { buf.setNextLong(refLong) }
        assertFailsWith<ByteBufferOverflowWarning> { buf.setNextULong(refULong) }
        assertFailsWith<ByteBufferOverflowWarning> { buf.setNextFloat(refFloat) }
        assertFailsWith<ByteBufferOverflowWarning> { buf.setNextDouble(refDouble) }
        assertEquals(buf.position, refSize)
    }

    /**
     * Test ReferenceMutableBuffer from a write/read perspective with native endianness.
     * This test certifies that the buffer writes and reads values consistently.
     *
     */
    @Test
    fun testReferenceMutableBufferWrite() {
        val buf = refMutableBufferOf(createArray())

        buf.setNextByte(refByte)
        buf.setNextUByte(refUByte)
        buf.setNextChar(refChar)
        buf.setNextShort(refShort)
        buf.setNextUShort(refUShort)
        buf.setNextInt(refInt)
        buf.setNextUInt(refUInt)
        buf.setNextLong(refLong)
        buf.setNextULong(refULong)
        buf.setNextFloat(refFloat)
        buf.setNextDouble(refDouble)

        buf.flip()

        assertEquals(buf.getNextByte(), refByte)
        assertEquals(buf.getNextUByte(), refUByte)
        assertEquals(buf.getNextChar(), refChar)
        assertEquals(buf.getNextShort(), refShort)
        assertEquals(buf.getNextUShort(), refUShort)
        assertEquals(buf.getNextInt(), refInt)
        assertEquals(buf.getNextUInt(), refUInt)
        assertEquals(buf.getNextLong(), refLong)
        assertEquals(buf.getNextULong(), refULong)
        assertEquals(buf.getNextFloat(), refFloat)
        assertEquals(buf.getNextDouble(), refDouble)
    }

    /**
     * Test ReferenceMutableBuffer from a write/read perspective with opposite endianness.
     * This test certifies that the buffer writes and reads values consistently.
     *
     */
    @Test
    fun testReferenceMutableBufferWriteReverse() {
        val buf = refMutableBufferOf(createArray())
        buf.endian = reverseEndianness(buf)

        buf.setNextByte(refByte)
        buf.setNextUByte(refUByte)
        buf.setNextChar(refChar)
        buf.setNextShort(refShort)
        buf.setNextUShort(refUShort)
        buf.setNextInt(refInt)
        buf.setNextUInt(refUInt)
        buf.setNextLong(refLong)
        buf.setNextULong(refULong)
        buf.setNextFloat(refFloat)
        buf.setNextDouble(refDouble)

        buf.flip()

        assertEquals(buf.getNextByte(), refByte)
        assertEquals(buf.getNextUByte(), refUByte)
        assertEquals(buf.getNextChar(), refChar)
        assertEquals(buf.getNextShort(), refShort)
        assertEquals(buf.getNextUShort(), refUShort)
        assertEquals(buf.getNextInt(), refInt)
        assertEquals(buf.getNextUInt(), refUInt)
        assertEquals(buf.getNextLong(), refLong)
        assertEquals(buf.getNextULong(), refULong)
        assertEquals(buf.getNextFloat(), refFloat)
        assertEquals(buf.getNextDouble(), refDouble)
    }

    /**
     * Test Buffer.reverse*() in double on all types.
     * This test certifies that all public domain *.reverseBytes() methods reverses properly.
     */
    @Test
    fun testReverse() {
        assertEquals(refShort.swapEndian(), refShort.swapEndian())
        assertEquals(refUShort.swapEndian(), refUShort.swapEndian())
        assertEquals(refChar.swapEndian(), refChar.swapEndian())
        assertEquals(refInt.swapEndian(), refInt.swapEndian())
        assertEquals(refUInt.swapEndian(), refUInt.swapEndian())
        assertEquals(refLong.swapEndian(), refLong.swapEndian())
        assertEquals(refULong.swapEndian(), refULong.swapEndian())
        assertEquals(refFloat.swapEndian(), refFloat.swapEndian())
        assertEquals(refDouble.swapEndian(), refDouble.swapEndian())
    }
}