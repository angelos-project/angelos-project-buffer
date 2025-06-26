package org.angproj.io.buf.mem

import org.angproj.io.buf.seg.Bytes


class ArbitraryBytesPoolTest : AbstractArbitraryPoolTest<Bytes, ArbitraryBytesPool>() {

    override fun createPool(): ArbitraryBytesPool =
        ArbitraryBytesPool(totalSize, minSize, maxSize)

    override fun allocate(pool: ArbitraryBytesPool, size: Int): Bytes =
        pool.allocate(size)

    override fun getSize(obj: Bytes): Int = obj.size

    override fun recycle(pool: ArbitraryBytesPool, obj: Bytes) {
        pool.recycle(obj)
    }
}