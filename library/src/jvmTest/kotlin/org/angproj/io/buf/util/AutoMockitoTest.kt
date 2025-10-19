package org.angproj.io.buf.util

import org.mockito.Mock
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.BeforeTest
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class AutoMockitoTest {

    @Mock
    private lateinit var auto: Auto

    @BeforeTest
    fun setup() {
        auto = mock()
    }

    @Test
    fun `test useWith closes resource if not view and is memory`() {
        whenever(auto.isView()).doReturn(false)
        whenever(auto.isMem()).doReturn(true)

        auto.useWith { it }
        verify(auto).close()
    }

    @Test
    fun `test useWith does not close if is view`() {
        whenever(auto.isView()).doReturn(true)
        whenever(auto.isMem()).doReturn(true)

        auto.useWith { it }
        verify(auto, never()).close()
    }

    @Test
    fun `test useWith does not close if not memory`() {
        whenever(auto.isView()).doReturn(false)
        whenever(auto.isMem()).doReturn(false)

        auto.useWith { it }
        verify(auto, never()).close()
    }

    @Test
    fun `test useWith returns block result`() {
        whenever(auto.isView()).doReturn(false)
        whenever(auto.isMem()).doReturn(true)

        assertEquals("result", auto.useWith { "result" })
    }

    @Test
    fun `test useWith closes resource on exception2`() {
        whenever(auto.isView()).doReturn(false)
        whenever(auto.isMem()).doReturn(true)

        assertFailsWith<IllegalStateException> {
            auto.useWith { throw IllegalStateException() }
        }
        verify(auto).close()
    }
}