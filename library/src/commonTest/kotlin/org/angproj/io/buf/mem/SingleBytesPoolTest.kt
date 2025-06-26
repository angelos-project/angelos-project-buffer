package org.angproj.io.buf.mem


import org.angproj.io.buf.util.DataSize
import org.angproj.io.buf.seg.Bytes

class SingleBytesPoolTest : AbstractFixedPoolTest<Bytes, SingleBytesPool>() {

    override val minSize = DataSize._32B

    override fun createPool(): SingleBytesPool =
        SingleBytesPool(minSize)

    override fun allocate(pool: SingleBytesPool, size: Int): Bytes =
        pool.allocate(size)

    override fun getSize(obj: Bytes): Int = obj.size

    override fun recycle(pool: SingleBytesPool, obj: Bytes) {
        pool.recycle(obj)
    }
}