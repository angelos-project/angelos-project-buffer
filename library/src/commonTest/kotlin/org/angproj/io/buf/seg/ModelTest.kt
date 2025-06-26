package org.angproj.io.buf.seg

import org.angproj.io.buf.mem.ModelPool
import kotlin.test.*

class ModelTest : SegmentTest<Model>() {
    override fun createSegment(): Model = Model(ModelPool.nullManager, LongArray(4))

    @Test
    fun testCreateDispose() {
        assertFalse(segment.isNull(), "Bytes should not be disposed after creation")
    }
}