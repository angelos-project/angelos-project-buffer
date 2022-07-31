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
package org.angproj.io.buf

import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Implements total reference testing support for mutable buffers.
 *
 * @constructor Create empty Mutable buffer test
 */
open class MutableBufferTest {

    /**
     * Ref array to manipulate for testing.
     * The last value is purposefully inverted for easy identification.
     */
    val refArray = byteArrayOf(
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

    val refByte: Byte = 0B1010101
    val refUByte: UByte = 0B10101010u

    val refChar: Char = 'Ö'

    val refShort: Short = 0B1010101_10101010
    val refUShort: UShort = 0B10101010_10101010u

    val refInt: Int = 0B1010101_10101010_10101010_10101010
    val refUInt: UInt = 0B10101010_10101010_10101010_10101010u

    val refLong: Long = 0B1010101_10101010_10101010_10101010_10101010_10101010_10101010_10101010L
    val refULong: ULong = 0B10101010_10101010_10101010_10101010_10101010_10101010_10101010_10101010u

    val refFloat: Float = 23.43585F
    val refDouble: Double = -0.892384774029876

    val refSize = refArray.size

    internal fun reverseEndianness(buf: Buffer) = when {
        buf.endian.isBig() -> Endianness.LITTLE_ENDIAN
        buf.endian.isLittle() -> Endianness.BIG_ENDIAN
        else -> error("Only big or little endian will do")
    }

    /**
     * Creates an empty ByteArray of reference size.
     *
     */
    fun createArray() = refArray.copyOf()

    /**
     * Populates a ByteArray with reference values as if they were written by a mutable buffer.
     *
     * @param array
     */
    fun populateArray(array: ByteArray): ByteArray {
        array[0] = refByte
        array[1] = refUByte.toByte()
        array.writeCharAt(2, refChar)
        array.writeShortAt(4, refShort)
        array.writeUShortAt(6, refUShort)
        array.writeIntAt(8, refInt)
        array.writeUIntAt(12, refUInt)
        array.writeLongAt(16, refLong)
        array.writeULongAt(24, refULong)
        array.writeFloatAt(32, refFloat)
        array.writeDoubleAt(36, refDouble)
        return array
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
        assertEquals(array[1].toUByte(), refUByte)
        assertEquals(array.readCharAt(2), refChar)
        assertEquals(array.readShortAt(4), refShort)
        assertEquals(array.readUShortAt(6), refUShort)
        assertEquals(array.readIntAt(8), refInt)
        assertEquals(array.readUIntAt(12), refUInt)
        assertEquals(array.readLongAt(16), refLong)
        assertEquals(array.readULongAt(24), refULong)
        assertEquals(array.readFloatAt(32).toBits(), refFloat.toBits())
        assertEquals(array.readDoubleAt(36), refDouble)

        assertEquals(array[refSize - 1], -127)
    }
}