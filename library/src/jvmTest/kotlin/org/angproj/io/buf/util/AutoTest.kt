package org.angproj.io.buf.util

import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import kotlin.test.Test
import kotlin.test.assertFailsWith

class AutoTest {

    @Test
    fun `test useWith closes resource if not view and is memory`() {
        val mock = mock<Auto> {
            on { isView() } doReturn false
            on { isMem() } doReturn true
        }
        mock.useWith { it }
        verify(mock).close()
    }

    @Test
    fun `test useWith does not close if is view`() {
        val mock = mock<Auto> {
            on { isView() } doReturn true
            on { isMem() } doReturn true
        }
        mock.useWith { it }
        verify(mock, never()).close()
    }

    @Test
    fun `test useWith does not close if not memory`() {
        val mock = mock<Auto> {
            on { isView() } doReturn false
            on { isMem() } doReturn false
        }
        mock.useWith { it }
        verify(mock, never()).close()
    }

    @Test
    fun `test useWith returns block result`() {
        val mock = mock<Auto> {
            on { isView() } doReturn false
            on { isMem() } doReturn true
        }
        val result = mock.useWith { "result" }
        assert(result == "result")
    }

    @Test
    fun `test useWith closes resource on exception`() {
        val mock = mock<Auto> {
            on { isView() } doReturn false
            on { isMem() } doReturn true
        }
        assertFailsWith<IllegalStateException> {
            mock.useWith { throw IllegalStateException() }
        }
        verify(mock).close()
    }
}