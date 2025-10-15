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
package org.angproj.io.buf

import org.angproj.sec.util.toUnitFraction
import org.mockito.Mock
import org.mockito.kotlin.*
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class BinaryWritableMockitoTest {

    @Mock
    private lateinit var binaryWritable: BinaryWritable

    @BeforeTest
    fun setup() {
        binaryWritable = mock()
    }

    @Test
    fun testWriteByte() {
        doNothing().whenever(binaryWritable).writeByte(0x7f)
        assertEquals(Unit, binaryWritable.writeByte(0x7f))
    }

    @Test
    fun testWriteUByte() {
        doNothing().whenever(binaryWritable).writeUByte(0x7fu)
        assertEquals(Unit, binaryWritable.writeUByte(0x7fu))
    }

    @Test
    fun testWriteShort() {
        doNothing().whenever(binaryWritable).writeShort(12345)
        assertEquals(Unit, binaryWritable.writeShort(12345))
    }

    @Test
    fun testWriteUShort() {
        doNothing().whenever(binaryWritable).writeUShort(12345u)
        assertEquals(Unit, binaryWritable.writeUShort(12345u))
    }

    @Test
    fun testWriteInt() {
        doNothing().whenever(binaryWritable).writeInt(0xDEADBEEF.toInt())
        assertEquals(Unit, binaryWritable.writeInt(0xDEADBEEF.toInt()))
    }

    @Test
    fun testWriteUInt() {
        doNothing().whenever(binaryWritable).writeUInt(0xDEADBEEFu)
        assertEquals(Unit, binaryWritable.writeUInt(0xDEADBEEFu))
    }

    @Test
    fun testWriteLong() {
        doNothing().whenever(binaryWritable).writeLong(0x123456789ABCDEFL)
        assertEquals(Unit, binaryWritable.writeLong(0x123456789ABCDEFL))
    }

    @Test
    fun testWriteULong() {
        doNothing().whenever(binaryWritable).writeULong(0x123456789ABCDEFuL)
        assertEquals(Unit, binaryWritable.writeULong(0x123456789ABCDEFuL))
    }

    @Test
    fun testWriteFloat() {
        doNothing().whenever(binaryWritable).writeFloat(0xDEADBEEF.toInt().toUnitFraction())
        assertEquals(Unit, binaryWritable.writeFloat(0xDEADBEEF.toInt().toUnitFraction()))
    }

    @Test
    fun testWriteDouble() {
        doNothing().whenever(binaryWritable).writeDouble(0x123456789ABCDEFL.toUnitFraction())
        assertEquals(Unit, binaryWritable.writeDouble(0x123456789ABCDEFL.toUnitFraction()))
    }
}