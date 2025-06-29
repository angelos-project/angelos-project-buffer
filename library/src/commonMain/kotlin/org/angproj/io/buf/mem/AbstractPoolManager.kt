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

import org.angproj.io.buf.seg.Segment
import org.angproj.io.buf.util.Cleanable
import org.angproj.io.buf.util.DataSize
import org.angproj.sec.util.ensure

public abstract class AbstractPoolManager<T: Any, S: Segment<S>>(
    public override val totalSize: DataSize,
    public val minSize: DataSize,
    public val maxSize: DataSize
) : MemoryManager<S>, Cleanable {

    init {
        ensure(maxSize.toInt() >= minSize.toInt()) { MemoryException("Maximum size must be greater than or equal to minimum size.") }
        ensure(totalSize.toInt() >= maxSize.toInt()) { MemoryException("Allocation size must be greater than or equal to maximum size.") }
    }

    override val segmentSize: DataSize
        get() = minSize

    protected val segmentMap: MutableMap<DataSize, MutableSet<S>> = mutableMapOf()

    protected val allSegments: MutableSet<S> = mutableSetOf()

    /**
     * Allocates a segment block of the specified size.
     * The size must be between minSize and maxSize.
     *
     * @param size The size of the segment block to allocate.
     * @return A new SegmentBlock instance.
     */
    protected abstract fun subAllocate(size: DataSize): T

    protected abstract fun createSegment(data: T): S

    public override fun allocate(): S {
        return allocate(segmentSize.toInt())
    }

    public override fun allocate(size: Int): S {
        ensure(size in minSize.toInt()..maxSize.toInt()) {
            MemoryException("Requested size must be between minSize and maxSize.") }
        val dataSize = DataSize.findLowestAbove(size)

        if (!segmentMap.containsKey(dataSize)) {
            segmentMap[dataSize] = mutableSetOf()
        }

        if (segmentMap.get(dataSize).isNullOrEmpty()) {
            val segment = createSegment(subAllocate(dataSize))
            allSegments.add(segment)
            return segment
        } else {
            val segment = segmentMap[dataSize]!!.first()
            segmentMap[dataSize]!!.remove(segment)
            return segment
        }
    }

    public override fun recycle(segment: S) {
        ensure(segment in allSegments) { MemoryException("Memory block not managed by this MemoryManager.") }
        segmentMap[DataSize.findLowestAbove(segment.size)]!!.add(segment)
    }

    override fun dispose() {
        allSegments.clear()
        segmentMap.clear()
    }
}