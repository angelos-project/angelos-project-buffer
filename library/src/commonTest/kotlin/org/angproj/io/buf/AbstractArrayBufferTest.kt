package org.angproj.io.buf

import org.angproj.io.buf.seg.SegmentException
import kotlin.test.*


abstract class AbstractArrayBufferTest<T, E: ArrayBuffer<T>> {

    protected val bufferSize: Int = 47
    protected lateinit var buffer: E
    protected lateinit var testValues: List<T>

    abstract fun createBuffer(size: Int): E
    abstract fun createTestValues(): List<T> // 0, 1, 3, MID, <neg>, MAX

    @BeforeTest
    fun setup() {
        buffer = createBuffer(bufferSize)
        testValues = createTestValues()

        check(testValues.size >= 6) { "Test values must contain at least two elements." }
    }

    @Test
    fun testSetAndGet() {
        buffer.set(0, 123)
        buffer.set(1, -456)
        buffer.set(7, 32767)
        assertEquals(123, buffer.get(0))
        assertEquals(-456, buffer.get(1))
        assertEquals(32767, buffer.get(7))
    }

    @Test
    fun testOverwrite() {

        buffer.set(2, 100)
        assertEquals(100, buffer.get(2))
        buffer.set(2, 200)
        assertEquals(200, buffer.get(2))
    }

    @Test
    fun testBoundary() {

        buffer.set(0, testValues[0])
        buffer.set(buffer.size - 1, testValues[1])
        assertEquals(1, buffer.get(0))
        assertEquals(2, buffer.get(buffer.size - 1))
    }

    @Test
    fun testOutOfBounds() {
        assertFailsWith<SegmentException> { buffer.get(-1) }
        assertFailsWith<SegmentException> { buffer.get(buffer.size) }
        assertFailsWith<SegmentException> { buffer.set(-1, testValues[0]) }
        assertFailsWith<SegmentException> { buffer.set(buffer.size, testValues[0]) }
    }
}