package org.angproj.io.buf.mem


import org.angproj.io.buf.util.DataSize
import org.angproj.io.buf.seg.Bytes

class FixedBytesPoolTest : AbstractFixedPoolTest<Bytes, FixedBytesPool>() {

    override val totalSize = DataSize._256B
    override val minSize = DataSize._32B
    override val maxSize = DataSize._32B // Fixed pool: minSize == maxSize

    override fun createPool(): FixedBytesPool =
        FixedBytesPool(totalSize, minSize)

    override fun allocate(pool: FixedBytesPool, size: Int): Bytes =
        pool.allocate(size)

    override fun getSize(obj: Bytes): Int = obj.size

    override fun recycle(pool: FixedBytesPool, obj: Bytes) {
        pool.recycle(obj)
    }
}