package org.angproj.io.buf.mem


import org.angproj.io.buf.util.DataSize
import org.angproj.io.buf.seg.Memory

class FixedMemoryPoolTest : AbstractFixedPoolTest<Memory, FixedMemoryPool>() {

    override val totalSize = DataSize._256B
    override val minSize = DataSize._32B
    override val maxSize = DataSize._32B // Fixed pool: minSize == maxSize

    override fun createPool(): FixedMemoryPool =
        FixedMemoryPool(totalSize, minSize)

    override fun allocate(pool: FixedMemoryPool, size: Int): Memory =
        pool.allocate(size)

    override fun getSize(obj: Memory): Int = obj.size

    override fun recycle(pool: FixedMemoryPool, obj: Memory) {
        pool.recycle(obj)
    }
}