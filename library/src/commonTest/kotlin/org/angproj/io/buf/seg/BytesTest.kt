package org.angproj.io.buf.seg

import org.angproj.io.buf.mem.BytesPool
import kotlin.test.*

class BytesTest : SegmentTest<Bytes>() {
    override fun createSegment(): Bytes = Bytes(BytesPool.nullManager, ByteArray(32))

    @Test
    fun testCreateDispose() {
        assertFalse(segment.isNull(), "Bytes should not be disposed after creation")
    }
}