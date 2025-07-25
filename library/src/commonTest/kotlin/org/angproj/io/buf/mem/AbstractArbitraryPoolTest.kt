package org.angproj.io.buf.mem


import org.angproj.io.buf.seg.Segment
import org.angproj.io.buf.util.DataSize
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

abstract class AbstractArbitraryPoolTest<S : Segment<S>, M : MemoryManager<S>> {

    open val totalSize = DataSize._256B
    open val minSize = DataSize._32B
    open val maxSize = DataSize._128B

    abstract fun createPool(): M
    abstract fun allocate(pool: M, size: Int): S
    abstract fun getSize(obj: S): Int
    abstract fun recycle(pool: M, obj: S)

    @Test
    fun testConstruction() {
        val pool = createPool()
        assertNotNull(pool)
    }

    @Test
    fun testAllocateWithinBounds() {
        val pool = createPool()
        val obj = allocate(pool, DataSize._32B.toInt())
        assertNotNull(obj)
        assertEquals(DataSize._32B.toInt(), getSize(obj))
    }

    @Test
    fun testAllocateAtMinAndMax() {
        val pool = createPool()
        val minObj = allocate(pool, DataSize._32B.toInt())
        val maxObj = allocate(pool, DataSize._128B.toInt())
        assertEquals(DataSize._32B.toInt(), getSize(minObj))
        assertEquals(DataSize._128B.toInt(), getSize(maxObj))
    }

    @Test
    fun testAllocateBelowMinThrows() {
        val pool = createPool()
        assertFailsWith<MemoryException> {
            allocate(pool, DataSize.UNKNOWN.toInt())
        }
    }

    @Test
    fun testAllocateAboveMaxThrows() {
        val pool = createPool()
        assertFailsWith<MemoryException> {
            allocate(pool, DataSize._256B.toInt())
        }
    }

    @Test
    fun testRecycle() {
        val pool = createPool()
        val obj = allocate(pool, DataSize._32B.toInt())
        recycle(pool, obj)
    }
}