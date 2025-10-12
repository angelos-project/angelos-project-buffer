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

class WriteAccessMockitoTest {

    @Mock
    private lateinit var writeAccess: WriteAccess

    @BeforeTest
    fun setup() {
        writeAccess = mock()
    }

    @Test
    fun testSetByte() {
        doNothing().whenever(writeAccess).setByte(2, 0x7f)
        assertEquals(Unit, writeAccess.setByte(2, 0x7f))
    }

    @Test
    fun testSetShort() {
        doNothing().whenever(writeAccess).setShort(1, 12345)
        assertEquals(Unit, writeAccess.setShort(1, 12345))
    }

    @Test
    fun testSetInt() {
        doNothing().whenever(writeAccess).setInt(0, 0xDEADBEEF.toInt())
        assertEquals(Unit, writeAccess.setInt(0, 0xDEADBEEF.toInt()))
    }

    @Test
    fun testSetLong() {
        doNothing().whenever(writeAccess).setLong(3, 0x123456789ABCDEFL)
        assertEquals(Unit, writeAccess.setLong(3, 0x123456789ABCDEFL))
    }

    @Test
    fun testSetByteThrowException() {
        whenever(writeAccess.setByte(-1, 0)).doThrow(IndexOutOfBoundsException())
        assertFailsWith<IndexOutOfBoundsException> { writeAccess.setByte(-1, 0) }
    }

    @Test
    fun testSetShortThrowException() {
        whenever(writeAccess.setShort(-1, 0)).doThrow(IndexOutOfBoundsException())
        assertFailsWith<IndexOutOfBoundsException> { writeAccess.setShort(-1, 0) }
    }

    @Test
    fun testSetIntThrowException() {
        whenever(writeAccess.setInt(-1, 0)).doThrow(IndexOutOfBoundsException())
        assertFailsWith<IndexOutOfBoundsException> { writeAccess.setInt(-1, 0) }
    }

    @Test
    fun testSetLongThrowException() {
        whenever(writeAccess.setLong(-1, 0)).doThrow(IndexOutOfBoundsException())
        assertFailsWith<IndexOutOfBoundsException> { writeAccess.setLong(-1, 0) }
    }
}