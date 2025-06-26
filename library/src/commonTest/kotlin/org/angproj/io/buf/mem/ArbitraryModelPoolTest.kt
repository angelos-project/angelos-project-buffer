package org.angproj.io.buf.mem


import org.angproj.io.buf.seg.Model

class ArbitraryModelPoolTest : AbstractArbitraryPoolTest<Model, ArbitraryModelPool>() {

    override fun createPool(): ArbitraryModelPool =
        ArbitraryModelPool(totalSize, minSize, maxSize)

    override fun allocate(pool: ArbitraryModelPool, size: Int): Model =
        pool.allocate(size)

    override fun getSize(obj: Model): Int = obj.size

    override fun recycle(pool: ArbitraryModelPool, obj: Model) {
        pool.recycle(obj)
    }
}