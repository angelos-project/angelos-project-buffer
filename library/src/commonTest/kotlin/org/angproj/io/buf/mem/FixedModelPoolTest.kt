package org.angproj.io.buf.mem


import org.angproj.io.buf.util.DataSize
import org.angproj.io.buf.seg.Model

class FixedModelPoolTest : AbstractFixedPoolTest<Model, FixedModelPool>() {

    override val totalSize = DataSize._256B
    override val minSize = DataSize._32B
    override val maxSize = DataSize._32B // Fixed pool: minSize == maxSize

    override fun createPool(): FixedModelPool =
        FixedModelPool(totalSize, minSize)

    override fun allocate(pool: FixedModelPool, size: Int): Model =
        pool.allocate(size)

    override fun getSize(obj: Model): Int = obj.size

    override fun recycle(pool: FixedModelPool, obj: Model) {
        pool.recycle(obj)
    }
}