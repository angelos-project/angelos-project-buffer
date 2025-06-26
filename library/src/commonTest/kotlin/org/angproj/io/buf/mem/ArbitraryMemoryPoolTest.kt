package org.angproj.io.buf.mem

import org.angproj.io.buf.seg.Memory


class ArbitraryMemoryPoolTest : AbstractArbitraryPoolTest<Memory, ArbitraryMemoryPool>() {

    override fun createPool(): ArbitraryMemoryPool =
        ArbitraryMemoryPool(totalSize, minSize, maxSize)

    override fun allocate(pool: ArbitraryMemoryPool, size: Int): Memory =
        pool.allocate(size)

    override fun getSize(obj: Memory): Int = obj.size

    override fun recycle(pool: ArbitraryMemoryPool, obj: Memory) {
        pool.recycle(obj)
    }
}