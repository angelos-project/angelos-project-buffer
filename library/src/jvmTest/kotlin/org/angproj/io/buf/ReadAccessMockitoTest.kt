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

import org.mockito.Mock
import org.mockito.kotlin.*
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ReadAccessMockitoTest {

    @Mock
    private lateinit var readAccess: ReadAccess

    @BeforeTest
    fun setup() {
        readAccess = mock()
    }

    @Test
    fun testGetByte() {
        whenever(readAccess.getByte(2)).doReturn(0x7f)
        assertEquals(readAccess.getByte(2), 0x7f)
    }

    @Test
    fun testGetShort() {
        whenever(readAccess.getShort(1)).doReturn(12345)
        assertEquals(readAccess.getShort(1), 12345)
    }

    @Test
    fun testGetInt() {
        whenever(readAccess.getInt(0)).doReturn(0xDEADBEEF.toInt())
        assertEquals(readAccess.getInt(0), 0xDEADBEEF.toInt())
    }

    @Test
    fun testGetLong() {
        whenever(readAccess.getLong(3)).doReturn(0x123456789ABCDEFL)
        assertEquals(readAccess.getLong(3), 0x123456789ABCDEFL)
    }

    @Test
    fun testGetByteThrowException() {
        whenever(readAccess.getByte(-1)).doThrow(IndexOutOfBoundsException())
        assertFailsWith<IndexOutOfBoundsException> { readAccess.getByte(-1) }
    }

    @Test
    fun testGetShortThrowException() {
        whenever(readAccess.getShort(-1)).doThrow(IndexOutOfBoundsException())
        assertFailsWith<IndexOutOfBoundsException> { readAccess.getShort(-1) }
    }

    @Test
    fun testGetIntThrowException() {
        whenever(readAccess.getInt(-1)).doThrow(IndexOutOfBoundsException())
        assertFailsWith<IndexOutOfBoundsException> { readAccess.getInt(-1) }
    }

    @Test
    fun testGetLongThrowException() {
        whenever(readAccess.getLong(-1)).doThrow(IndexOutOfBoundsException())
        assertFailsWith<IndexOutOfBoundsException> { readAccess.getLong(-1) }
    }
}