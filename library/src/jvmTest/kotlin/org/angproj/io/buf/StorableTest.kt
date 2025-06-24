package org.angproj.io.buf

import kotlin.test.*
import org.mockito.kotlin.*

class StorableTest {

    @Test
    fun `test storeByte is called with correct arguments`() {
        val mock = mock<Storable>()
        mock.storeByte(2, 0x7F)
        verify(mock).storeByte(2, 0x7F)
    }

    @Test
    fun `test storeUByte is called with correct arguments`() {
        val mock = mock<Storable>()
        mock.storeUByte(3, 0xFFu)
        verify(mock).storeUByte(3, 0xFFu)
    }

    @Test
    fun `test storeShort is called with correct arguments`() {
        val mock = mock<Storable>()
        mock.storeShort(1, 12345)
        verify(mock).storeShort(1, 12345)
    }

    @Test
    fun `test storeUShort is called with correct arguments`() {
        val mock = mock<Storable>()
        mock.storeUShort(4, 54321u)
        verify(mock).storeUShort(4, 54321u)
    }

    @Test
    fun `test storeInt is called with correct arguments`() {
        val mock = mock<Storable>()
        mock.storeInt(0, 0xDEADBEEF.toInt())
        verify(mock).storeInt(0, 0xDEADBEEF.toInt())
    }

    @Test
    fun `test storeUInt is called with correct arguments`() {
        val mock = mock<Storable>()
        mock.storeUInt(5, 0xFFFFFFFFu)
        verify(mock).storeUInt(5, 0xFFFFFFFFu)
    }

    @Test
    fun `test storeLong is called with correct arguments`() {
        val mock = mock<Storable>()
        mock.storeLong(3, 0x123456789ABCDEFL)
        verify(mock).storeLong(3, 0x123456789ABCDEFL)
    }

    @Test
    fun `test storeULong is called with correct arguments`() {
        val mock = mock<Storable>()
        mock.storeULong(6, 0xFFFFFFFFFFFFFFFFuL)
        verify(mock).storeULong(6, 0xFFFFFFFFFFFFFFFFuL)
    }

    @Test
    fun `test storeFloat is called with correct arguments`() {
        val mock = mock<Storable>()
        mock.storeFloat(7, 3.14f)
        verify(mock).storeFloat(7, 3.14f)
    }

    @Test
    fun `test storeDouble is called with correct arguments`() {
        val mock = mock<Storable>()
        mock.storeDouble(8, 2.71828)
        verify(mock).storeDouble(8, 2.71828)
    }

    @Test
    fun `test storeByte throws IndexOutOfBoundsException`() {
        val mock = mock<Storable> {
            on { storeByte(eq(-1), any()) } doThrow IndexOutOfBoundsException()
        }
        assertFailsWith<IndexOutOfBoundsException> { mock.storeByte(-1, 0) }
    }

    @Test
    fun `test storeUByte throws IndexOutOfBoundsException`() {
        val mock = mock<Storable> {
            on { storeUByte(eq(-1), any()) } doThrow IndexOutOfBoundsException()
        }
        assertFailsWith<IndexOutOfBoundsException> { mock.storeUByte(-1, 0u) }
    }

    @Test
    fun `test storeShort throws IndexOutOfBoundsException`() {
        val mock = mock<Storable> {
            on { storeShort(eq(100), any()) } doThrow IndexOutOfBoundsException()
        }
        assertFailsWith<IndexOutOfBoundsException> { mock.storeShort(100, 0) }
    }

    @Test
    fun `test storeUShort throws IndexOutOfBoundsException`() {
        val mock = mock<Storable> {
            on { storeUShort(eq(100), any()) } doThrow IndexOutOfBoundsException()
        }
        assertFailsWith<IndexOutOfBoundsException> { mock.storeUShort(100, 0u) }
    }

    @Test
    fun `test storeInt throws IndexOutOfBoundsException`() {
        val mock = mock<Storable> {
            on { storeInt(eq(-5), any()) } doThrow IndexOutOfBoundsException()
        }
        assertFailsWith<IndexOutOfBoundsException> { mock.storeInt(-5, 0) }
    }

    @Test
    fun `test storeUInt throws IndexOutOfBoundsException`() {
        val mock = mock<Storable> {
            on { storeUInt(eq(-5), any()) } doThrow IndexOutOfBoundsException()
        }
        assertFailsWith<IndexOutOfBoundsException> { mock.storeUInt(-5, 0u) }
    }

    @Test
    fun `test storeLong throws IndexOutOfBoundsException`() {
        val mock = mock<Storable> {
            on { storeLong(eq(999), any()) } doThrow IndexOutOfBoundsException()
        }
        assertFailsWith<IndexOutOfBoundsException> { mock.storeLong(999, 0L) }
    }

    @Test
    fun `test storeULong throws IndexOutOfBoundsException`() {
        val mock = mock<Storable> {
            on { storeULong(eq(999), any()) } doThrow IndexOutOfBoundsException()
        }
        assertFailsWith<IndexOutOfBoundsException> { mock.storeULong(999, 0uL) }
    }

    @Test
    fun `test storeFloat throws IndexOutOfBoundsException`() {
        val mock = mock<Storable> {
            on { storeFloat(eq(999), any()) } doThrow IndexOutOfBoundsException()
        }
        assertFailsWith<IndexOutOfBoundsException> { mock.storeFloat(999, 0f) }
    }

    @Test
    fun `test storeDouble throws IndexOutOfBoundsException`() {
        val mock = mock<Storable> {
            on { storeDouble(eq(999), any()) } doThrow IndexOutOfBoundsException()
        }
        assertFailsWith<IndexOutOfBoundsException> { mock.storeDouble(999, 0.0) }
    }
}