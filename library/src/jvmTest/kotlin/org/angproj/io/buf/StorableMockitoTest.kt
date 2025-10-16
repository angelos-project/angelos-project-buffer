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

class StorableMockitoTest {

    @Mock
    private lateinit var storable: Storable

    @BeforeTest
    fun setup() {
        storable = mock()
    }

    @Test
    fun testWriteByte() {
        doNothing().whenever(storable).storeByte(0, 0x7f)
        assertEquals(Unit, storable.storeByte(0, 0x7f))
    }

    @Test
    fun testWriteUByte() {
        doNothing().whenever(storable).storeUByte(0, 0x7fu)
        assertEquals(Unit, storable.storeUByte(0, 0x7fu))
    }

    @Test
    fun testWriteShort() {
        doNothing().whenever(storable).storeShort(0, 12345)
        assertEquals(Unit, storable.storeShort(0, 12345))
    }

    @Test
    fun testWriteUShort() {
        doNothing().whenever(storable).storeUShort(0, 12345u)
        assertEquals(Unit, storable.storeUShort(0, 12345u))
    }

    @Test
    fun testWriteInt() {
        doNothing().whenever(storable).storeInt(0, 0xDEADBEEF.toInt())
        assertEquals(Unit, storable.storeInt(0, 0xDEADBEEF.toInt()))
    }

    @Test
    fun testWriteUInt() {
        doNothing().whenever(storable).storeUInt(0, 0xDEADBEEFu)
        assertEquals(Unit, storable.storeUInt(0, 0xDEADBEEFu))
    }

    @Test
    fun testWriteLong() {
        doNothing().whenever(storable).storeLong(0, 0x123456789ABCDEFL)
        assertEquals(Unit, storable.storeLong(0, 0x123456789ABCDEFL))
    }

    @Test
    fun testWriteULong() {
        doNothing().whenever(storable).storeULong(0, 0x123456789ABCDEFuL)
        assertEquals(Unit, storable.storeULong(0, 0x123456789ABCDEFuL))
    }

    @Test
    fun testWriteFloat() {
        doNothing().whenever(storable).storeFloat(0, 0xDEADBEEF.toInt().toUnitFraction())
        assertEquals(Unit, storable.storeFloat(0, 0xDEADBEEF.toInt().toUnitFraction()))
    }

    @Test
    fun testWriteDouble() {
        doNothing().whenever(storable).storeDouble(0, 0x123456789ABCDEFL.toUnitFraction())
        assertEquals(Unit, storable.storeDouble(0, 0x123456789ABCDEFL.toUnitFraction()))
    }
}