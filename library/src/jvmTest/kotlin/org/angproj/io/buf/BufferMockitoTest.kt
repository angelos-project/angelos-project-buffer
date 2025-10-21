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

class BufferMockitoTest {

    @Mock
    private lateinit var buffer: Buffer

    @BeforeTest
    fun setup() {
        buffer = mock()
    }

    @Test
    fun testCapacity() {
        whenever(buffer.capacity).doReturn(1024)
        assertEquals(1024, buffer.capacity)
    }

    @Test
    fun testSize() {
        whenever(buffer.size).doReturn(1024)
        assertEquals(1024, buffer.size)
    }

    @Test
    fun testLimit() {
        whenever(buffer.limit).doReturn(1000)
        assertEquals(1000, buffer.limit)
    }

    @Test
    fun testIsMem() {
        whenever(buffer.isMem()).doReturn(true)
        assertEquals(true, buffer.isMem())
    }

    @Test
    fun testIsView() {
        whenever(buffer.isView()).doReturn(false)
        assertEquals(false, buffer.isView())
    }

    @Test
    fun testClose() {
        doNothing().whenever(buffer).close()
        assertEquals(Unit, buffer.close())
    }

    @Test
    fun testCompareTo() {
        val buf2 = mock<Buffer>()
        whenever(buffer.compareTo(buf2)).doReturn(0)
        assertEquals(0, buffer.compareTo(buf2))
    }
}