package org.angproj.io.buf.mem

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertNotNull

class MemoryManagerTest {

    @Test
    fun testMemoryManagerNullManager() {
        assertNotNull(MemoryManager.nullManager)
    }

    @Test
    fun testBytesPoolIsNull() {
        assertFalse { BytesPool.nullManager == MemoryManager.nullManager }
    }

    @Test
    fun testMemoryPoolIsNull() {
        assertFalse { MemoryPool.nullManager == MemoryManager.nullManager }
    }

    @Test
    fun testModelPoolIsNull() {
        assertFalse { ModelPool.nullManager == MemoryManager.nullManager }
    }
}