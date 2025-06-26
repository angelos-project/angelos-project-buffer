package org.angproj.io.buf.mem

import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class MemoryManagerTest {

    @Test
    fun testMemoryManagerNullManager() {
        assertTrue{ MemoryManager.nullManager.isNull() }
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