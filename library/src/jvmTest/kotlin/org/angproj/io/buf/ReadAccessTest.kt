package org.angproj.io.buf

import org.mockito.kotlin.*
import kotlin.IndexOutOfBoundsException
import kotlin.test.Test
import kotlin.test.assertFailsWith

class ReadAccessTest {

    @Test
    fun `test getByte is called with correct arguments`() {
        val mock = mock<ReadAccess> {
            on { getByte(2) } doReturn 0x7F
        }
        mock.getByte(2)
        verify(mock).getByte(2)
    }

    @Test
    fun `test getShort is called with correct arguments`() {
        val mock = mock<ReadAccess> {
            on { getShort(1) } doReturn 12345
        }
        mock.getShort(1)
        verify(mock).getShort(1)
    }

    @Test
    fun `test getInt is called with correct arguments`() {
        val mock = mock<ReadAccess> {
            on { getInt(0) } doReturn 0xDEADBEEF.toInt()
        }
        mock.getInt(0)
        verify(mock).getInt(0)
    }

    @Test
    fun `test getLong is called with correct arguments`() {
        val mock = mock<ReadAccess> {
            on { getLong(3) } doReturn 0x123456789ABCDEFL
        }
        mock.getLong(3)
        verify(mock).getLong(3)
    }

    @Test
    fun `test getByte throws IndexOutOfBoundsException`() {
        val mock = mock<ReadAccess> {
            on { getByte(eq(-1)) } doThrow IndexOutOfBoundsException()
        }
        assertFailsWith<IndexOutOfBoundsException> { mock.getByte(-1) }
    }

    @Test
    fun `test getShort throws IndexOutOfBoundsException`() {
        val mock = mock<ReadAccess> {
            on { getShort(eq(100)) } doThrow IndexOutOfBoundsException()
        }
        assertFailsWith<IndexOutOfBoundsException> { mock.getShort(100) }
    }

    @Test
    fun `test getInt throws IndexOutOfBoundsException`() {
        val mock = mock<ReadAccess> {
            on { getInt(eq(-5)) } doThrow IndexOutOfBoundsException()
        }
        assertFailsWith<IndexOutOfBoundsException> { mock.getInt(-5) }
    }

    @Test
    fun `test getLong throws IndexOutOfBoundsException`() {
        val mock = mock<ReadAccess> {
            on { getLong(eq(999)) } doThrow IndexOutOfBoundsException()
        }
        assertFailsWith<IndexOutOfBoundsException> { mock.getLong(999) }
    }
}