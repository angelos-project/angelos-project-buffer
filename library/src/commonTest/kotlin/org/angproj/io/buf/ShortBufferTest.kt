package org.angproj.io.buf

import org.angproj.io.buf.seg.SegmentException
import kotlin.test.*


class ShortBufferTest {
    @Test
    fun testSetAndGet() {
        val buffer = BufMgr.shortBuf(128)
        buffer.set(0, 123)
        buffer.set(1, -456)
        buffer.set(7, 32767)
        assertEquals(123, buffer.get(0))
        assertEquals(-456, buffer.get(1))
        assertEquals(32767, buffer.get(7))
    }

    @Test
    fun testOverwrite() {
        val buffer = BufMgr.shortBuf(128)

        buffer.set(2, 100)
        assertEquals(100, buffer.get(2))
        buffer.set(2, 200)
        assertEquals(200, buffer.get(2))
    }

    @Test
    fun testBoundary() {
        val buffer = BufMgr.shortBuf(128)

        buffer.set(0, 1)
        buffer.set(buffer.size - 1, 2)
        assertEquals(1, buffer.get(0))
        assertEquals(2, buffer.get(buffer.size - 1))
    }

    @Test
    fun testOutOfBounds() {
        val buffer = BufMgr.shortBuf(128)
        assertFailsWith<SegmentException> { buffer.get(-1) }
        assertFailsWith<SegmentException> { buffer.get(buffer.size) }
        assertFailsWith<SegmentException> { buffer.set(-1, 0) }
        assertFailsWith<SegmentException> { buffer.set(buffer.size, 0) }
    }
}