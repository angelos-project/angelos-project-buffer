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

class BinaryReadableMockitoTest {

    @Mock
    private lateinit var binaryReadable: BinaryReadable

    @BeforeTest
    fun setup() {
        binaryReadable = mock()
    }

    @Test
    fun testReadByte() {
        whenever(binaryReadable.readByte()).thenReturn(0x7f)
        assertEquals(0x7f, binaryReadable.readByte())
    }

    @Test
    fun testReadUByte() {
        whenever(binaryReadable.readUByte()).thenReturn(0x7fu)
        assertEquals(0x7fu, binaryReadable.readUByte())
    }

    @Test
    fun testReadShort() {
        whenever(binaryReadable.readShort()).thenReturn(12345)
        assertEquals(12345, binaryReadable.readShort())
    }

    @Test
    fun testReadUShort() {
        whenever(binaryReadable.readUShort()).thenReturn(12345u)
        assertEquals(12345u, binaryReadable.readUShort())
    }

    @Test
    fun testReadInt() {
        whenever(binaryReadable.readInt()).thenReturn(0xDEADBEEF.toInt())
        assertEquals(0xDEADBEEF.toInt(), binaryReadable.readInt())
    }

    @Test
    fun testReadUInt() {
        whenever(binaryReadable.readUInt()).thenReturn(0xDEADBEEFu)
        assertEquals(0xDEADBEEFu, binaryReadable.readUInt())
    }

    @Test
    fun testReadLong() {
        whenever(binaryReadable.readLong()).thenReturn(0x123456789ABCDEFL)
        assertEquals(0x123456789ABCDEFL, binaryReadable.readLong())
    }

    @Test
    fun testReadULong() {
        whenever(binaryReadable.readULong()).thenReturn(0x123456789ABCDEFuL)
        assertEquals(0x123456789ABCDEFuL, binaryReadable.readULong())
    }

    @Test
    fun testReadFloat() {
        whenever(binaryReadable.readFloat()).thenReturn(0xDEADBEEF.toInt().toUnitFraction())
        assertEquals(0xDEADBEEF.toInt().toUnitFraction(), binaryReadable.readFloat())
    }

    @Test
    fun testReadDouble() {
        whenever(binaryReadable.readDouble()).thenReturn(0x123456789ABCDEFL.toUnitFraction())
        assertEquals(0x123456789ABCDEFL.toUnitFraction(), binaryReadable.readDouble())
    }
}