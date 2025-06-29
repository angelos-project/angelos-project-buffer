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
package org.angproj.io.buf.mem

import org.angproj.io.buf.NativeMemoryManager
import org.angproj.io.buf.RootBlock
import org.angproj.io.buf.SegmentBlock
import org.angproj.io.buf.seg.Memory
import org.angproj.io.buf.util.DataSize
import org.angproj.io.buf.util.unsupported
import org.angproj.sec.util.ensure

public abstract class MemoryPool(allocationSize: DataSize, minSize: DataSize, maxSize: DataSize
) : AbstractPoolManager<SegmentBlock, Memory>(allocationSize, minSize, maxSize) {

    protected val nativeMemoryManager: NativeMemoryManager = NativeMemoryManager(allocationSize)

    protected val rootBlock: RootBlock = nativeMemoryManager.allocate().also { it.limitAt(0) }

    override fun subAllocate(size: DataSize): SegmentBlock {
        ensure(rootBlock.size - rootBlock.limit >= size.toInt()) {
            MemoryException("Not enough memory available to allocate the requested size.") }
        ensure(size.toInt() in minSize.toInt()..maxSize.toInt()) {
            MemoryException("Requested size must be between minSize and maxSize.") }

        val offset = rootBlock.limit
        rootBlock.limitAt(rootBlock.limit + size.toInt())
        return rootBlock.subBlock(offset, size.toInt()) { _, s, p ->
            SegmentBlock(this, p, s)
        }
    }

    override fun createSegment(data: SegmentBlock): Memory {
        return Memory(this, data)
    }

    override fun dispose() {
        super.dispose()
        nativeMemoryManager.release()
    }

    public companion object {
        public val nullManager : MemoryManager<Memory> by lazy {
            createNullManager()
        }
    }
}

private fun MemoryPool.Companion.createNullManager(): MemoryManager<Memory> = object :  MemoryManager<Memory>{
    override val totalSize: DataSize = DataSize.UNKNOWN
    override val segmentSize: DataSize = DataSize.UNKNOWN

    override fun allocate(): Nothing = unsupported()
    override fun allocate(size: Int): Nothing = unsupported()
    override fun recycle(segment: Memory) { /* Don't touch! */ }
}