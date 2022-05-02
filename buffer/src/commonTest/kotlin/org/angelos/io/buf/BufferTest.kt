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

import kotlin.test.assertEquals

open class BufferTest {
    val refRead = byteArrayOf(
        0B1010101, 0B10101010u.toByte(),
        0B1010101, 0B10101010.toByte(), 0B10101010.toByte(), 0B10101010.toByte(),
        0B1010101, 0B10101010.toByte(), 0B10101010.toByte(), 0B10101010.toByte(),
        0B10101010.toByte(), 0B10101010.toByte(), 0B10101010.toByte(), 0B10101010.toByte(),
        0B1010101, 0B10101010.toByte(), 0B10101010.toByte(), 0B10101010.toByte(),
        0B10101010.toByte(), 0B10101010.toByte(), 0B10101010.toByte(), 0B10101010.toByte(),
        0B10101010.toByte(), 0B10101010.toByte(), 0B10101010.toByte(), 0B10101010.toByte(),
        0B10101010.toByte(), 0B10101010.toByte(), 0B10101010.toByte(), 0B10101010.toByte(),
        30, 31, 32, 33, 34, 35, 36, 37, 38, 39,
        40, 41, 42, 43, 44, 45, 46, 47, 48, 49,
        50, 51, 52, 53, 54, 55, 56, 57, 58, 59,
        60, 61, 62, 63, 64, 65, 66, 67, 68, 69,
        70, 71, 72, 73, 74, 75, 76, 77, 78, 79,
        80, 81, 82, 83, 84, 85, 86, 87, 88, 89,
        90, 91, 92, 93, 94, 95, 96, 97, 98, 99,
        100, 101, 102, 103, 104, 105, 106, 107, 108, 109,
        110, 111, 112, 113, 114, 115, 116, 117, 118, 119,
        120, 121, 122, 123, 124, 125, 126, 127
    )

    val refWrite = byteArrayOf(
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
        120, 121, 122, 123, 124, 125, 126, 127
    )

    private val refByte: Byte = 0B1010101
    private val refUByte: UByte = 0B10101010u

    private val refShort: Short = 0B1010101_10101010
    private val refUShort: UShort = 0B10101010_10101010u

    private val refInt: Int = 0B1010101_10101010_10101010_10101010
    private val refUInt: UInt = 0B10101010_10101010_10101010_10101010u

    private val refLong: Long = 0B1010101_10101010_10101010_10101010_10101010_10101010_10101010_10101010L
    private val refULong: ULong = 0B10101010_10101010_10101010_10101010_10101010_10101010_10101010_10101010u

    val size: Int = refWrite.size

    fun readAny(buf: Buffer) {
        assertEquals(buf.getNextByte(), refByte)
        assertEquals(buf.getNextUByte(), refUByte)
        assertEquals(buf.getNextShort(), refShort)
        assertEquals(buf.getNextUShort(), refUShort)
        assertEquals(buf.getNextInt(), refInt)
        assertEquals(buf.getNextUInt(), refUInt)
        assertEquals(buf.getNextLong(), refLong)
        assertEquals(buf.getNextULong(), refULong)
        for(idx in buf.position until size){
            val data = buf.getNextByte()
            assertEquals(data, refRead[idx])
        }
    }

    fun writeAny(buf: MutableBuffer) {
        buf.setNextByte(refByte)
        buf.setNextUByte(refUByte)
        buf.setNextShort(refShort)
        buf.setNextUShort(refUShort)
        buf.setNextInt(refInt)
        buf.setNextUInt(refUInt)
        buf.setNextLong(refLong)
        buf.setNextULong(refULong)
        for(idx in buf.position until size)
            buf.setNextByte(refWrite[idx])
    }
}