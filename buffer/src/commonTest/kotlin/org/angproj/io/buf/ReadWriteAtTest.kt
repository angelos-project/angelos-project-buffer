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
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Test of read and write functions.
 *
 * @constructor Create empty Read write at test
 */
class ReadWriteAtTest {

    private val refChar: Char = 'Ö'

    private val refShort: Short = 0B1010101_10101010
    private val refUShort: UShort = 0B10101010_10101010u

    private val refInt: Int = 0B1010101_10101010_10101010_10101010
    private val refUInt: UInt = 0B10101010_10101010_10101010_10101010u

    private val refLong: Long = 0B1010101_10101010_10101010_10101010_10101010_10101010_10101010_10101010L
    private val refULong: ULong = 0B10101010_10101010_10101010_10101010_10101010_10101010_10101010_10101010u

    private val refFloat: Float = 23.43585F
    private val refDouble: Double = -0.892384774029876

    /**
     * Writing and reading of Short to/from ByteArray.
     *
     */
    @Test
    fun short() {
        val array = ByteArray(8)
        array.writeShortAt(0, refShort)
        assertEquals(array.readShortAt(0), refShort)
    }

    /**
     * Writing and reading of UShort to/from ByteArray.
     *
     */
    @Test
    fun ushort() {
        val array = ByteArray(8)
        array.writeUShortAt(0, refUShort)
        assertEquals(array.readUShortAt(0), refUShort)
    }

    /**
     * Writing and reading of Char to/from ByteArray.
     *
     */
    @Test
    fun char() {
        val array = ByteArray(8)
        array.writeCharAt(0, refChar)
        assertEquals(array.readCharAt(0), refChar)
    }

    /**
     * Writing and reading of Int to/from ByteArray.
     *
     */
    @Test
    fun int() {
        val array = ByteArray(8)
        array.writeIntAt(0, refInt)
        assertEquals(array.readIntAt(0), refInt)
    }

    /**
     * Writing and reading of UInt to/from ByteArray.
     *
     */
    @Test
    fun uint() {
        val array = ByteArray(8)
        array.writeUIntAt(0, refUInt)
        assertEquals(array.readUIntAt(0), refUInt)
    }

    /**
     * Writing and reading of Long to/from ByteArray.
     *
     */
    @Test
    fun long() {
        val array = ByteArray(8)
        array.writeLongAt(0, refLong)
        assertEquals(array.readLongAt(0), refLong)
    }

    /**
     * Writing and reading of ULong to/from ByteArray.
     *
     */
    @Test
    fun ulong() {
        val array = ByteArray(8)
        array.writeULongAt(0, refULong)
        assertEquals(array.readULongAt(0), refULong)
    }

    /**
     * Writing and reading of Float to/from ByteArray.
     *
     */
    @Test
    fun float() {
        val array = ByteArray(8)
        array.writeFloatAt(0, refFloat)
        assertEquals(array.readFloatAt(0).toRawBits(), refFloat.toRawBits())
    }

    /**
     * Writing and reading of Double to/from ByteArray.
     *
     */
    @Test
    fun double() {
        val array = ByteArray(8)
        array.writeDoubleAt(0, refDouble)
        assertEquals(array.readDoubleAt(0), refDouble)
    }

    @Test
    fun flag0() {
        var value: Byte = 0
        assertFalse(value.checkFlag0())
        value = value.flipOnFlag0()
        assertTrue(value.checkFlag0())
        value = value.flipOffFlag0()
        assertFalse(value.checkFlag0())
    }

    @Test
    fun flag1() {
        var value: Byte = 0
        assertFalse(value.checkFlag1())
        value = value.flipOnFlag1()
        assertTrue(value.checkFlag1())
        value = value.flipOffFlag1()
        assertFalse(value.checkFlag1())
    }

    @Test
    fun flag2() {
        var value: Byte = 0
        assertFalse(value.checkFlag2())
        value = value.flipOnFlag2()
        assertTrue(value.checkFlag2())
        value = value.flipOffFlag2()
        assertFalse(value.checkFlag2())
    }

    @Test
    fun flag3() {
        var value: Byte = 0
        assertFalse(value.checkFlag3())
        value = value.flipOnFlag3()
        assertTrue(value.checkFlag3())
        value = value.flipOffFlag3()
        assertFalse(value.checkFlag3())
    }

    @Test
    fun flag4() {
        var value: Byte = 0
        assertFalse(value.checkFlag4())
        value = value.flipOnFlag4()
        assertTrue(value.checkFlag4())
        value = value.flipOffFlag4()
        assertFalse(value.checkFlag4())
    }

    @Test
    fun flag5() {
        var value: Byte = 0
        assertFalse(value.checkFlag5())
        value = value.flipOnFlag5()
        assertTrue(value.checkFlag5())
        value = value.flipOffFlag5()
        assertFalse(value.checkFlag5())
    }

    @Test
    fun flag6() {
        var value: Byte = 0
        assertFalse(value.checkFlag6())
        value = value.flipOnFlag6()
        assertTrue(value.checkFlag6())
        value = value.flipOffFlag6()
        assertFalse(value.checkFlag6())
    }

    @Test
    fun flag7() {
        var value: Byte = 0
        assertFalse(value.checkFlag7())
        value = value.flipOnFlag7()
        assertTrue(value.checkFlag7())
        value = value.flipOffFlag7()
        assertFalse(value.checkFlag7())
    }
}