/**
 * Copyright (c) 2024 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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
import org.angproj.io.buf.util.Cleanable
import org.angproj.io.buf.util.DataSize
import org.angproj.io.buf.util.toInt

public class MemoryManager(
    public val allocationSize: DataSize,
    public val minSize: DataSize,
    public val maxSize: DataSize
) : Cleanable {

    init {
        require(maxSize.toInt() >= minSize.toInt()) {
            "Maximum size must be greater than or equal to minimum size."
        }
        require(allocationSize.toInt() >= maxSize.toInt()) {
            "Allocation size must be greater than or equal to maximum size."
        }
    }

    protected val nativeMemoryManager: NativeMemoryManager = NativeMemoryManager(allocationSize)

    protected val rootBlock: RootBlock = nativeMemoryManager.allocate()

    protected val segmentMap: MutableMap<DataSize, MutableSet<Memory>> = mutableMapOf()

    protected val allSegments: MutableSet<Memory> = mutableSetOf()

    /**
     * Allocates a segment block of the specified size.
     * The size must be between minSize and maxSize.
     *
     * @param size The size of the segment block to allocate.
     * @return A new SegmentBlock instance.
     */
    protected fun subAllocate(size: DataSize): SegmentBlock {
        require(rootBlock.size - rootBlock.limit >= size.toInt()) {
            "Not enough memory available to allocate the requested size."
        }
        require(size.toInt() in minSize.toInt()..maxSize.toInt()) {
            "Requested size must be between minSize and maxSize."
        }

        return rootBlock.subBlock(rootBlock.limit, size.toInt()) { _, s, p ->
            rootBlock.limitAt(rootBlock.limit + size.toInt())
            SegmentBlock(this, p, s)
        }
    }

    public fun allocate(size: Int): Memory {
        val dataSize = DataSize.findLowestAbove(size)

        if(!segmentMap.containsKey(dataSize)) {
            segmentMap[dataSize] = mutableSetOf()
        }

        if(segmentMap.get(dataSize).isNullOrEmpty()) {
            val memory = Memory(this, subAllocate(dataSize))
            allSegments.add(memory)
            return memory
        } else {
            val memory = segmentMap[dataSize]!!.first()
            segmentMap[dataSize]!!.remove(memory)
            return memory
        }
    }

    public fun recycle(memory: Memory) {
        check(memory in allSegments) {
            "Memory block not managed by this MemoryManager."
        }
        segmentMap[DataSize.findLowestAbove(memory.size)]!!.add(memory)
    }

    override fun dispose() {
        allSegments.clear()
        segmentMap.clear()
        nativeMemoryManager.release()
    }
}