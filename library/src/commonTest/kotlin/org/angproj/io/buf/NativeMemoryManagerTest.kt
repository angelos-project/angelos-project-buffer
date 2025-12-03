/**
 * Copyright (c) 2022-2025 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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

import org.angproj.io.buf.util.DataSize
import org.angproj.io.buf.util.ifJvmOrNative
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertSame


class NativeMemoryManagerTest {

    @Test
    fun testDoubleAllocateReturnsSameBlock(): Unit = ifJvmOrNative {
        val manager = NativeMemoryManager(DataSize._1K)
        val block1 = manager.allocate()
        val block2 = manager.allocate()

        assertSame(block1, block2)
        assertFalse(block1.isNull())
    }

    @Test
    fun testReleaseWithoutAllocateThrows(): Unit  = ifJvmOrNative {
        val manager = NativeMemoryManager(DataSize._1K)
        assertFailsWith<IllegalStateException> { manager.release() }
    }

    @Test
    fun testAllocateAfterReleaseThrows(): Unit = ifJvmOrNative {
        val manager = NativeMemoryManager(DataSize._1K)
        manager.allocate()
        manager.release()
        assertFailsWith<IllegalStateException> { manager.allocate() }

        // Indempotent release
        manager.release()
    }
}