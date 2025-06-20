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

import org.angproj.io.buf.util.DataSize
import org.angproj.io.buf.util.Once


/**
 * A memory manager for native memory segments.
 *
 * This class is responsible for allocating and releasing a root block of native memory segment.
 * It does not perform any actual recycling, as native memory management is typically
 * handled by the underlying system.
 *
 * @property size The size of the native memory segments managed by this manager.
 */
public class NativeMemoryManager(public val size: DataSize) {

    protected enum class InnerState {
        UNINITIALIZED,
        ALLOCATED,
        RELEASED
    }

    private var innerState: InnerState = InnerState.UNINITIALIZED
    private var rootBlock: RootBlock by Once()

    /**
     * Allocates a new root block of native memory,
     * or returns the existing one if it has already been allocated.
     *
     * This method allocates a block of memory of the specified size and returns a [RootBlock]
     * instance that wraps this memory. If the allocation has already been released, an
     * [IllegalStateException] is thrown.
     *
     * @return A new [RootBlock] instance wrapping the allocated memory.
     */
    public fun allocate(): RootBlock = when (innerState) {
        InnerState.UNINITIALIZED -> {
            innerState = InnerState.ALLOCATED
            rootBlock = allocateRootBlock(size)
            rootBlock
        }
        InnerState.ALLOCATED -> rootBlock
        InnerState.RELEASED -> error("Root block has already been released")
    }

    /**
     * Returns the current root block of native memory.
     *
     * This method returns the root block that was allocated by the last call to [allocate].
     * If no root block has been allocated, an [IllegalStateException] is thrown.
     *
     * @throws IllegalStateException if no root block has been allocated.
     */
    public fun release(): Unit = when (innerState) {
        InnerState.UNINITIALIZED -> error("No root block has been allocated.")
        InnerState.ALLOCATED -> {
            releaseRootBlock(rootBlock)
            innerState = InnerState.RELEASED
        }
        InnerState.RELEASED -> Unit
    }
}


internal expect fun NativeMemoryManager.allocateRootBlock(size: DataSize): RootBlock

internal expect fun NativeMemoryManager.releaseRootBlock(block: RootBlock): Unit