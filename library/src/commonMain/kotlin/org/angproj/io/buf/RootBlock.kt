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

import org.angproj.aux.io.DataSize


public class RootBlock(rawPtr: Long, size: DataSize): MemoryBlock<RootBlock> {

    init {
        require(size.size >= 0) { "Size must be non-negative" }
    }

    private val pointer: TypePointer<RootBlock> = TypePointer(rawPtr)
    private val blockSize: DataSize = size
    private var blockLimit: Int = this.size

    override val parentBlock: MemoryBlock<*>
        get() = MemoryBlock.nullBlock

    override fun getPointer(): TypePointer<RootBlock> = pointer

    override val size: Int
        get() = blockSize.size

    override val limit: Int
        get() = blockLimit

    override fun limitAt(newLimit: Int) {
        require(newLimit in 0..size) { "New limit must be between 0 and size" }
        blockLimit = newLimit
    }
}