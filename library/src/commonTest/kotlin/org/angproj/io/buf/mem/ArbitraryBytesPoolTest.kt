package org.angproj.io.buf.mem

import kotlin.test.Test
import kotlin.test.assertFailsWith
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

    @Test
    fun testAllocateMaxSmallerThanMin() {
        assertFailsWith<MemoryException> {
            ArbitraryBytesPool(totalSize, maxSize, minSize)
        }
    }

    @Test
    fun testAllocateTotalSmallerThanMax() {
        assertFailsWith<MemoryException> {
            ArbitraryBytesPool(maxSize, minSize, totalSize)
        }
    }
}