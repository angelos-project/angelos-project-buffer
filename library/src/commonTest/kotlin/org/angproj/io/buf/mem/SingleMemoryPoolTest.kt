package org.angproj.io.buf.mem


import org.angproj.io.buf.util.DataSize
import org.angproj.io.buf.seg.Memory

class SingleMemoryPoolTest : AbstractFixedPoolTest<Memory, SingleMemoryPool>() {

    override val minSize = DataSize._32B

    override fun createPool(): SingleMemoryPool =
        SingleMemoryPool(minSize)

    override fun allocate(pool: SingleMemoryPool, size: Int): Memory =
        pool.allocate(size)

    override fun getSize(obj: Memory): Int = obj.size

    override fun recycle(pool: SingleMemoryPool, obj: Memory) {
        pool.recycle(obj)
    }
}