package org.angproj.io.buf.seg

import org.angproj.io.buf.mem.SingleMemoryPool
import org.angproj.io.buf.util.DataSize
import kotlin.test.*

class MemoryTest : SegmentTest<Memory>() {
    override fun createSegment(): Memory = SingleMemoryPool(DataSize._32B).allocate()

    @Test
    fun testCreateDispose() {
        assertFalse(segment.isNull(), "Bytes should not be disposed after creation")
    }
}
