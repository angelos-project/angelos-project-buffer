package org.angproj.io.buf

import org.mockito.kotlin.any
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import kotlin.test.Test
import kotlin.test.assertFailsWith

class BinaryWritableTest {

    @Test
    fun `test writeByte is called with correct arguments`() {
        val mock = mock<BinaryWritable>()
        mock.writeByte(0x7F)
        verify(mock).writeByte(0x7F)
    }

    @Test
    fun `test writeUByte is called with correct arguments`() {
        val mock = mock<BinaryWritable>()
        mock.writeUByte(0xFFu)
        verify(mock).writeUByte(0xFFu)
    }

    @Test
    fun `test writeShort is called with correct arguments`() {
        val mock = mock<BinaryWritable>()
        mock.writeShort(12345)
        verify(mock).writeShort(12345)
    }

    @Test
    fun `test writeUShort is called with correct arguments`() {
        val mock = mock<BinaryWritable>()
        mock.writeUShort(54321u)
        verify(mock).writeUShort(54321u)
    }

    @Test
    fun `test writeInt is called with correct arguments`() {
        val mock = mock<BinaryWritable>()
        mock.writeInt(0xDEADBEEF.toInt())
        verify(mock).writeInt(0xDEADBEEF.toInt())
    }

    @Test
    fun `test writeUInt is called with correct arguments`() {
        val mock = mock<BinaryWritable>()
        mock.writeUInt(0xFFFFFFFFu)
        verify(mock).writeUInt(0xFFFFFFFFu)
    }

    @Test
    fun `test writeLong is called with correct arguments`() {
        val mock = mock<BinaryWritable>()
        mock.writeLong(0x123456789ABCDEFL)
        verify(mock).writeLong(0x123456789ABCDEFL)
    }

    @Test
    fun `test writeULong is called with correct arguments`() {
        val mock = mock<BinaryWritable>()
        mock.writeULong(0xFFFFFFFFFFFFFFFFuL)
        verify(mock).writeULong(0xFFFFFFFFFFFFFFFFuL)
    }

    @Test
    fun `test writeFloat is called with correct arguments`() {
        val mock = mock<BinaryWritable>()
        mock.writeFloat(3.14f)
        verify(mock).writeFloat(3.14f)
    }

    @Test
    fun `test writeDouble is called with correct arguments`() {
        val mock = mock<BinaryWritable>()
        mock.writeDouble(2.71828)
        verify(mock).writeDouble(2.71828)
    }

    @Test
    fun `test writeByte throws exception`() {
        val mock = mock<BinaryWritable> {
            on { writeByte(any()) } doThrow IndexOutOfBoundsException()
        }
        assertFailsWith<IndexOutOfBoundsException> { mock.writeByte(0) }
    }

    @Test
    fun `test writeUByte throws exception`() {
        val mock = mock<BinaryWritable> {
            on { writeUByte(any()) } doThrow IndexOutOfBoundsException()
        }
        assertFailsWith<IndexOutOfBoundsException> { mock.writeUByte(0u) }
    }

    @Test
    fun `test writeShort throws exception`() {
        val mock = mock<BinaryWritable> {
            on { writeShort(any()) } doThrow IndexOutOfBoundsException()
        }
        assertFailsWith<IndexOutOfBoundsException> { mock.writeShort(0) }
    }

    @Test
    fun `test writeUShort throws exception`() {
        val mock = mock<BinaryWritable> {
            on { writeUShort(any()) } doThrow IndexOutOfBoundsException()
        }
        assertFailsWith<IndexOutOfBoundsException> { mock.writeUShort(0u) }
    }

    @Test
    fun `test writeInt throws exception`() {
        val mock = mock<BinaryWritable> {
            on { writeInt(any()) } doThrow IndexOutOfBoundsException()
        }
        assertFailsWith<IndexOutOfBoundsException> { mock.writeInt(0) }
    }

    @Test
    fun `test writeUInt throws exception`() {
        val mock = mock<BinaryWritable> {
            on { writeUInt(any()) } doThrow IndexOutOfBoundsException()
        }
        assertFailsWith<IndexOutOfBoundsException> { mock.writeUInt(0u) }
    }

    @Test
    fun `test writeLong throws exception`() {
        val mock = mock<BinaryWritable> {
            on { writeLong(any()) } doThrow IndexOutOfBoundsException()
        }
        assertFailsWith<IndexOutOfBoundsException> { mock.writeLong(0L) }
    }

    @Test
    fun `test writeULong throws exception`() {
        val mock = mock<BinaryWritable> {
            on { writeULong(any()) } doThrow IndexOutOfBoundsException()
        }
        assertFailsWith<IndexOutOfBoundsException> { mock.writeULong(0uL) }
    }

    @Test
    fun `test writeFloat throws exception`() {
        val mock = mock<BinaryWritable> {
            on { writeFloat(any()) } doThrow IndexOutOfBoundsException()
        }
        assertFailsWith<IndexOutOfBoundsException> { mock.writeFloat(0f) }
    }

    @Test
    fun `test writeDouble throws exception`() {
        val mock = mock<BinaryWritable> {
            on { writeDouble(any()) } doThrow IndexOutOfBoundsException()
        }
        assertFailsWith<IndexOutOfBoundsException> { mock.writeDouble(0.0) }
    }
}