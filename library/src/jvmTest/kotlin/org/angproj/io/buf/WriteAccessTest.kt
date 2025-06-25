package org.angproj.io.buf

import org.mockito.kotlin.*
import kotlin.test.Test
import kotlin.test.assertFailsWith

class WriteAccessTest {

    @Test
    fun `test setByte is called with correct arguments`() {
        val mock = mock<WriteAccess>()
        mock.setByte(2, 0x7F)
        verify(mock).setByte(2, 0x7F)
    }

    @Test
    fun `test setShort is called with correct arguments`() {
        val mock = mock<WriteAccess>()
        mock.setShort(1, 12345)
        verify(mock).setShort(1, 12345)
    }

    @Test
    fun `test setInt is called with correct arguments`() {
        val mock = mock<WriteAccess>()
        mock.setInt(0, 0xDEADBEEF.toInt())
        verify(mock).setInt(0, 0xDEADBEEF.toInt())
    }

    @Test
    fun `test setLong is called with correct arguments`() {
        val mock = mock<WriteAccess>()
        mock.setLong(3, 0x123456789ABCDEFL)
        verify(mock).setLong(3, 0x123456789ABCDEFL)
    }

    @Test
    fun `test setByte throws IndexOutOfBoundsException`() {
        val mock = mock<WriteAccess> {
            on { setByte(eq(-1), any()) } doThrow IndexOutOfBoundsException()
        }
        assertFailsWith<IndexOutOfBoundsException> { mock.setByte(-1, 0) }
    }

    @Test
    fun `test setShort throws IndexOutOfBoundsException`() {
        val mock = mock<WriteAccess> {
            on { setShort(eq(100), any()) } doThrow IndexOutOfBoundsException()
        }
        assertFailsWith<IndexOutOfBoundsException> { mock.setShort(100, 0) }
    }

    @Test
    fun `test setInt throws IndexOutOfBoundsException`() {
        val mock = mock<WriteAccess> {
            on { setInt(eq(-5), any()) } doThrow IndexOutOfBoundsException()
        }
        assertFailsWith<IndexOutOfBoundsException> { mock.setInt(-5, 0) }
    }

    @Test
    fun `test setLong throws IndexOutOfBoundsException`() {
        val mock = mock<WriteAccess> {
            on { setLong(eq(789), any()) } doThrow IndexOutOfBoundsException()
        }
        assertFailsWith<IndexOutOfBoundsException> { mock.setLong(789, 0L) }
    }
}