package org.angproj.io.buf.mem


import org.angproj.io.buf.util.DataSize
import org.angproj.io.buf.seg.Model

class SingleModelPoolTest : AbstractFixedPoolTest<Model, SingleModelPool>() {

    override val minSize = DataSize._32B

    override fun createPool(): SingleModelPool =
        SingleModelPool(minSize)

    override fun allocate(pool: SingleModelPool, size: Int): Model =
        pool.allocate(size)

    override fun getSize(obj: Model): Int = obj.size

    override fun recycle(pool: SingleModelPool, obj: Model) {
        pool.recycle(obj)
    }
}