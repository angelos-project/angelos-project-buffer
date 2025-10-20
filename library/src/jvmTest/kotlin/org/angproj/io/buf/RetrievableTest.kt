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

import org.mockito.kotlin.*
import kotlin.test.Test
import kotlin.test.assertFailsWith

class RetrievableTest {
    // TODO Review and merge with RetrievableMockitoTest

    @Test
    fun `test retrieveByte is called with correct arguments`() {
        val mock = mock<Retrievable> {
            on { retrieveByte(2) } doReturn 0x7F
        }
        mock.retrieveByte(2)
        verify(mock).retrieveByte(2)
    }

    @Test
    fun `test retrieveUByte is called with correct arguments`() {
        val mock = mock<Retrievable> {
            on { retrieveUByte(3) } doReturn 0xFFu
        }
        mock.retrieveUByte(3)
        verify(mock).retrieveUByte(3)
    }

    @Test
    fun `test retrieveShort is called with correct arguments`() {
        val mock = mock<Retrievable> {
            on { retrieveShort(1) } doReturn 12345
        }
        mock.retrieveShort(1)
        verify(mock).retrieveShort(1)
    }

    @Test
    fun `test retrieveUShort is called with correct arguments`() {
        val mock = mock<Retrievable> {
            on { retrieveUShort(4) } doReturn 54321u
        }
        mock.retrieveUShort(4)
        verify(mock).retrieveUShort(4)
    }

    @Test
    fun `test retrieveInt is called with correct arguments`() {
        val mock = mock<Retrievable> {
            on { retrieveInt(0) } doReturn 0xDEADBEEF.toInt()
        }
        mock.retrieveInt(0)
        verify(mock).retrieveInt(0)
    }

    @Test
    fun `test retrieveUInt is called with correct arguments`() {
        val mock = mock<Retrievable> {
            on { retrieveUInt(5) } doReturn 0xFFFFFFFFu
        }
        mock.retrieveUInt(5)
        verify(mock).retrieveUInt(5)
    }

    @Test
    fun `test retrieveLong is called with correct arguments`() {
        val mock = mock<Retrievable> {
            on { retrieveLong(3) } doReturn 0x123456789ABCDEFL
        }
        mock.retrieveLong(3)
        verify(mock).retrieveLong(3)
    }

    @Test
    fun `test retrieveULong is called with correct arguments`() {
        val mock = mock<Retrievable> {
            on { retrieveULong(6) } doReturn 0xFFFFFFFFFFFFFFFFuL
        }
        mock.retrieveULong(6)
        verify(mock).retrieveULong(6)
    }

    @Test
    fun `test retrieveFloat is called with correct arguments`() {
        val mock = mock<Retrievable> {
            on { retrieveFloat(7) } doReturn 3.14f
        }
        mock.retrieveFloat(7)
        verify(mock).retrieveFloat(7)
    }

    @Test
    fun `test retrieveDouble is called with correct arguments`() {
        val mock = mock<Retrievable> {
            on { retrieveDouble(8) } doReturn 2.71828
        }
        mock.retrieveDouble(8)
        verify(mock).retrieveDouble(8)
    }

    @Test
    fun `test retrieveByte throws IndexOutOfBoundsException`() {
        val mock = mock<Retrievable> {
            on { retrieveByte(eq(-1)) } doThrow IndexOutOfBoundsException()
        }
        assertFailsWith<IndexOutOfBoundsException> { mock.retrieveByte(-1) }
    }

    @Test
    fun `test retrieveUByte throws IndexOutOfBoundsException`() {
        val mock = mock<Retrievable> {
            on { retrieveUByte(eq(-1)) } doThrow IndexOutOfBoundsException()
        }
        assertFailsWith<IndexOutOfBoundsException> { mock.retrieveUByte(-1) }
    }

    @Test
    fun `test retrieveShort throws IndexOutOfBoundsException`() {
        val mock = mock<Retrievable> {
            on { retrieveShort(eq(100)) } doThrow IndexOutOfBoundsException()
        }
        assertFailsWith<IndexOutOfBoundsException> { mock.retrieveShort(100) }
    }

    @Test
    fun `test retrieveUShort throws IndexOutOfBoundsException`() {
        val mock = mock<Retrievable> {
            on { retrieveUShort(eq(100)) } doThrow IndexOutOfBoundsException()
        }
        assertFailsWith<IndexOutOfBoundsException> { mock.retrieveUShort(100) }
    }

    @Test
    fun `test retrieveInt throws IndexOutOfBoundsException`() {
        val mock = mock<Retrievable> {
            on { retrieveInt(eq(-5)) } doThrow IndexOutOfBoundsException()
        }
        assertFailsWith<IndexOutOfBoundsException> { mock.retrieveInt(-5) }
    }

    @Test
    fun `test retrieveUInt throws IndexOutOfBoundsException`() {
        val mock = mock<Retrievable> {
            on { retrieveUInt(eq(-5)) } doThrow IndexOutOfBoundsException()
        }
        assertFailsWith<IndexOutOfBoundsException> { mock.retrieveUInt(-5) }
    }

    @Test
    fun `test retrieveLong throws IndexOutOfBoundsException`() {
        val mock = mock<Retrievable> {
            on { retrieveLong(eq(789)) } doThrow IndexOutOfBoundsException()
        }
        assertFailsWith<IndexOutOfBoundsException> { mock.retrieveLong(789) }
    }

    @Test
    fun `test retrieveULong throws IndexOutOfBoundsException`() {
        val mock = mock<Retrievable> {
            on { retrieveULong(eq(789)) } doThrow IndexOutOfBoundsException()
        }
        assertFailsWith<IndexOutOfBoundsException> { mock.retrieveULong(789) }
    }

    @Test
    fun `test retrieveFloat throws IndexOutOfBoundsException`() {
        val mock = mock<Retrievable> {
            on { retrieveFloat(eq(789)) } doThrow IndexOutOfBoundsException()
        }
        assertFailsWith<IndexOutOfBoundsException> { mock.retrieveFloat(789) }
    }

    @Test
    fun `test retrieveDouble throws IndexOutOfBoundsException`() {
        val mock = mock<Retrievable> {
            on { retrieveDouble(eq(789)) } doThrow IndexOutOfBoundsException()
        }
        assertFailsWith<IndexOutOfBoundsException> { mock.retrieveDouble(789) }
    }
}