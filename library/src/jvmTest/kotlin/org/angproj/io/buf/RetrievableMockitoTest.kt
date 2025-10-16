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

class RetrievableMockitoTest {

    @Mock
    private lateinit var retrievable: Retrievable

    @BeforeTest
    fun setup() {
        retrievable = mock()
    }

    @Test
    fun testRetrieveByte() {
        whenever(retrievable.retrieveByte(0)).thenReturn(0x7f)
        assertEquals(0x7f, retrievable.retrieveByte(0))
    }

    @Test
    fun testRetrieveUByte() {
        whenever(retrievable.retrieveUByte(0)).thenReturn(0x7fu)
        assertEquals(0x7fu, retrievable.retrieveUByte(0))
    }

    @Test
    fun testRetrieveShort() {
        whenever(retrievable.retrieveShort(0)).thenReturn(12345)
        assertEquals(12345, retrievable.retrieveShort(0))
    }

    @Test
    fun testRetrieveUShort() {
        whenever(retrievable.retrieveUShort(0)).thenReturn(12345u)
        assertEquals(12345u, retrievable.retrieveUShort(0))
    }

    @Test
    fun testRetrieveInt() {
        whenever(retrievable.retrieveInt(0)).thenReturn(0xDEADBEEF.toInt())
        assertEquals(0xDEADBEEF.toInt(), retrievable.retrieveInt(0))
    }

    @Test
    fun testRetrieveUInt() {
        whenever(retrievable.retrieveUInt(0)).thenReturn(0xDEADBEEFu)
        assertEquals(0xDEADBEEFu, retrievable.retrieveUInt(0))
    }

    @Test
    fun testRetrieveLong() {
        whenever(retrievable.retrieveLong(0)).thenReturn(0x123456789ABCDEFL)
        assertEquals(0x123456789ABCDEFL, retrievable.retrieveLong(0))
    }

    @Test
    fun testRetrieveULong() {
        whenever(retrievable.retrieveULong(0)).thenReturn(0x123456789ABCDEFuL)
        assertEquals(0x123456789ABCDEFuL, retrievable.retrieveULong(0))
    }

    @Test
    fun testRetrieveFloat() {
        whenever(retrievable.retrieveFloat(0)).thenReturn(0xDEADBEEF.toInt().toUnitFraction())
        assertEquals(0xDEADBEEF.toInt().toUnitFraction(), retrievable.retrieveFloat(0))
    }

    @Test
    fun testRetrieveDouble() {
        whenever(retrievable.retrieveDouble(0)).thenReturn(0x123456789ABCDEFL.toUnitFraction())
        assertEquals(0x123456789ABCDEFL.toUnitFraction(), retrievable.retrieveDouble(0))
    }
}