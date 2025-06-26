/**
 * Copyright (c) 2024-2025 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
 *
 * This software is available under the terms of the MIT license. Parts are licensed
 * under different terms if stated. The legal terms are attached to the LICENSE file
 * and are made available on:
 *
 *      https://opensource.org/licenses/MIT
 *
 * SPDX-License-Identifier: MIT
 *
 * Contributors:
 *      Kristoffer Paulsson - initial implementation
 */
package org.angproj.io.buf

import org.angproj.aux.util.ifJvmOrNative
import org.angproj.io.buf.util.DataSize
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SegmentBlockTest {
    private val dataSize = DataSize._1K // Define the data size for the segment block
    private lateinit var manager: NativeMemoryManager
    private lateinit var root: RootBlock
    private lateinit var block: SegmentBlock

    @BeforeTest
    fun setup(): Unit = ifJvmOrNative {
        manager = NativeMemoryManager(dataSize)
        root = manager.allocate()
        block = root.subBlock(0, dataSize.toInt()) { _, s, p ->
            SegmentBlock(this, p, s) // Create a SegmentBlock with the given size and pointer
        }
    }

    @AfterTest
    fun tearDown(): Unit = ifJvmOrNative {
        manager.release()
    }

    @Test
    fun testReadWriteByte(): Unit = ifJvmOrNative {
        block.setByte(0, 0x7F)
        assertEquals(0x7F.toByte(), block.getByte(0))
    }

    @Test
    fun testReadWriteShort(): Unit = ifJvmOrNative {
        block.setShort(1, 0x1234)
        assertEquals(0x1234.toShort(), block.getShort(1))
    }

    @Test
    fun testReadWriteInt(): Unit = ifJvmOrNative {
        block.setInt(2, 0x12345678)
        assertEquals(0x12345678, block.getInt(2))
    }

    @Test
    fun testReadWriteLong(): Unit = ifJvmOrNative {
        block.setLong(4, 0x123456789ABCDEF0)
        assertEquals(0x123456789ABCDEF0, block.getLong(4))
    }

    @Test
    fun getParentBlock(): Unit = ifJvmOrNative {
        assertEquals(root, block.parentBlock)
        assertTrue { block.parentBlock.parentBlock.isNull() }
    }

    @Test
    fun testBlockSize(): Unit = ifJvmOrNative {
        assertEquals(dataSize.toInt(), block.size)
    }

    @Test
    fun testBlockLimit(): Unit = ifJvmOrNative {
        assertEquals(dataSize.toInt(), block.limit)
        block.limitAt(512)
        assertEquals(512, block.limit)
    }

    @Test
    fun testBlockPointer(): Unit = ifJvmOrNative {
        val pointer = block.getPointer()
        assertEquals(root.getPointer().ptr, pointer.ptr)
    }
}