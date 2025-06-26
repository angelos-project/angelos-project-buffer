package org.angproj.io.buf

import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.test.assertEquals

class MemoryBlockTest {

    @Test
    fun testNullBlock() {
        val nullBlock = MemoryBlock.nullBlock
        assertTrue { nullBlock.isNull() }
        assertEquals(nullBlock.size, 0)
        assertEquals(nullBlock.limit, 0)
        assertEquals(nullBlock.getPointer().ptr, 0L)
        assertEquals(nullBlock.parentBlock, MemoryBlock.nullBlock)
    }
}