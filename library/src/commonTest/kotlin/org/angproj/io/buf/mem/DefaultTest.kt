package org.angproj.io.buf.mem


import org.angproj.io.buf.seg.Bytes

class DefaultTest : AbstractFixedPoolTest<Bytes, Default>() {

    override val totalSize = Default.totalSize
    override val minSize = Default.segmentSize
    override val maxSize = Default.segmentSize
    override val aboveSize = Int.MAX_VALUE

    override fun createPool(): Default = Default

    override fun allocate(pool: Default, size: Int): Bytes = pool.allocate(size)

    override fun getSize(obj: Bytes): Int = obj.size

    override fun recycle(pool: Default, obj: Bytes) {
        pool.recycle(obj)
    }
}