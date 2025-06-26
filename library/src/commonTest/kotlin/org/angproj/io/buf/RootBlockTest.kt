package org.angproj.io.buf

import org.angproj.aux.util.ifJvmOrNative
import org.angproj.io.buf.util.DataSize
import kotlin.test.*

class RootBlockTest {

    @Test
    fun testConstructionAndProperties() {
        val size = DataSize._128B
        val block = RootBlock(0x2000L, size)
        assertEquals(128, block.size)
        assertEquals(128, block.limit)
        assertEquals(MemoryBlock.nullBlock, block.parentBlock)
        assertEquals(0x2000L, block.getPointer().ptr)
    }

    @Test
    fun testLimitAtValid() {
        val block = RootBlock(0x3000L, DataSize._128B)
        block.limitAt(32)
        assertEquals(32, block.limit)
        block.limitAt(0)
        assertEquals(0, block.limit)
        block.limitAt(64)
        assertEquals(64, block.limit)
    }

    @Test
    fun testLimitAtInvalid() {
        val block = RootBlock(0x4000L, DataSize._128B)
        assertFailsWith<IllegalArgumentException> { block.limitAt(-1) }
        assertFailsWith<IllegalArgumentException> { block.limitAt(block.size + 1) }
    }

    @Test
    fun testPointerType() {
        val block = RootBlock(0x1234L, DataSize._128B)
        val ptr: TypePointer<RootBlock> = block.getPointer()
        assertEquals(0x1234L, ptr.ptr)
    }

    @Test
    fun getParentBlock() {
        val block = RootBlock(0x5000L, DataSize._128B)
        assertEquals(MemoryBlock.nullBlock, block.parentBlock)
        assertTrue { block.parentBlock.isNull() }
    }

    @Test
    fun testBlockSize() {
        val size = DataSize._256B
        val block = RootBlock(0x6000L, size)
        assertEquals(256, block.size)
        assertEquals(size.toInt(), block.size)
    }

    @Test
    fun testBlockLimit() {
        val block = RootBlock(0x7000L, DataSize._256B)
        assertEquals(256, block.limit)
        block.limitAt(128)
        assertEquals(128, block.limit)
        block.limitAt(0)
        assertEquals(0, block.limit)
    }

    @Test
    fun testBlockPointer() {
        val block = RootBlock(0x8000L, DataSize._256B)
        val pointer = block.getPointer()
        assertEquals(0x8000L, pointer.ptr)
    }
}