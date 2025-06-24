package org.angproj.io.buf

import kotlin.test.*
import org.mockito.kotlin.*
import kotlin.IndexOutOfBoundsException

class BinaryReadableTest {

    @Test
    fun `test readByte is called and returns value`() {
        val mock = mock<BinaryReadable> {
            on { readByte() } doReturn 0x7F
        }
        mock.readByte()
        verify(mock).readByte()
    }

    @Test
    fun `test readUByte is called and returns value`() {
        val mock = mock<BinaryReadable> {
            on { readUByte() } doReturn 0xFFu
        }
        mock.readUByte()
        verify(mock).readUByte()
    }

    @Test
    fun `test readShort is called and returns value`() {
        val mock = mock<BinaryReadable> {
            on { readShort() } doReturn 12345
        }
        mock.readShort()
        verify(mock).readShort()
    }

    @Test
    fun `test readUShort is called and returns value`() {
        val mock = mock<BinaryReadable> {
            on { readUShort() } doReturn 54321u
        }
        mock.readUShort()
        verify(mock).readUShort()
    }

    @Test
    fun `test readInt is called and returns value`() {
        val mock = mock<BinaryReadable> {
            on { readInt() } doReturn 0xDEADBEEF.toInt()
        }
        mock.readInt()
        verify(mock).readInt()
    }

    @Test
    fun `test readUInt is called and returns value`() {
        val mock = mock<BinaryReadable> {
            on { readUInt() } doReturn 0xFFFFFFFFu
        }
        mock.readUInt()
        verify(mock).readUInt()
    }

    @Test
    fun `test readLong is called and returns value`() {
        val mock = mock<BinaryReadable> {
            on { readLong() } doReturn 0x123456789ABCDEFL
        }
        mock.readLong()
        verify(mock).readLong()
    }

    @Test
    fun `test readULong is called and returns value`() {
        val mock = mock<BinaryReadable> {
            on { readULong() } doReturn 0xFFFFFFFFFFFFFFFFuL
        }
        mock.readULong()
        verify(mock).readULong()
    }

    @Test
    fun `test readFloat is called and returns value`() {
        val mock = mock<BinaryReadable> {
            on { readFloat() } doReturn 3.14f
        }
        mock.readFloat()
        verify(mock).readFloat()
    }

    @Test
    fun `test readDouble is called and returns value`() {
        val mock = mock<BinaryReadable> {
            on { readDouble() } doReturn 2.71828
        }
        mock.readDouble()
        verify(mock).readDouble()
    }

    @Test
    fun `test readByte throws exception`() {
        val mock = mock<BinaryReadable> {
            on { readByte() } doThrow IndexOutOfBoundsException()
        }
        assertFailsWith<IndexOutOfBoundsException> { mock.readByte() }
    }

    @Test
    fun `test readUByte throws exception`() {
        val mock = mock<BinaryReadable> {
            on { readUByte() } doThrow IndexOutOfBoundsException()
        }
        assertFailsWith<IndexOutOfBoundsException> { mock.readUByte() }
    }

    @Test
    fun `test readShort throws exception`() {
        val mock = mock<BinaryReadable> {
            on { readShort() } doThrow IndexOutOfBoundsException()
        }
        assertFailsWith<IndexOutOfBoundsException> { mock.readShort() }
    }

    @Test
    fun `test readUShort throws exception`() {
        val mock = mock<BinaryReadable> {
            on { readUShort() } doThrow IndexOutOfBoundsException()
        }
        assertFailsWith<IndexOutOfBoundsException> { mock.readUShort() }
    }

    @Test
    fun `test readInt throws exception`() {
        val mock = mock<BinaryReadable> {
            on { readInt() } doThrow IndexOutOfBoundsException()
        }
        assertFailsWith<IndexOutOfBoundsException> { mock.readInt() }
    }

    @Test
    fun `test readUInt throws exception`() {
        val mock = mock<BinaryReadable> {
            on { readUInt() } doThrow IndexOutOfBoundsException()
        }
        assertFailsWith<IndexOutOfBoundsException> { mock.readUInt() }
    }

    @Test
    fun `test readLong throws exception`() {
        val mock = mock<BinaryReadable> {
            on { readLong() } doThrow IndexOutOfBoundsException()
        }
        assertFailsWith<IndexOutOfBoundsException> { mock.readLong() }
    }

    @Test
    fun `test readULong throws exception`() {
        val mock = mock<BinaryReadable> {
            on { readULong() } doThrow IndexOutOfBoundsException()
        }
        assertFailsWith<IndexOutOfBoundsException> { mock.readULong() }
    }

    @Test
    fun `test readFloat throws exception`() {
        val mock = mock<BinaryReadable> {
            on { readFloat() } doThrow IndexOutOfBoundsException()
        }
        assertFailsWith<IndexOutOfBoundsException> { mock.readFloat() }
    }

    @Test
    fun `test readDouble throws exception`() {
        val mock = mock<BinaryReadable> {
            on { readDouble() } doThrow IndexOutOfBoundsException()
        }
        assertFailsWith<IndexOutOfBoundsException> { mock.readDouble() }
    }
}