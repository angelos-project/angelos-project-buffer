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

import org.angproj.io.buf.seg.Bytes
import org.angproj.io.buf.util.Cleanable
import org.angproj.io.buf.util.DataSize
import org.angproj.io.buf.util.toInt

public class BytesPool(
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

    protected var allocated: Int = 0

    protected val segmentMap: MutableMap<DataSize, MutableSet<Bytes>> = mutableMapOf()

    protected val allSegments: MutableSet<Bytes> = mutableSetOf()

    /**
     * Allocates a segment block of the specified size.
     * The size must be between minSize and maxSize.
     *
     * @param size The size of the segment block to allocate.
     * @return A new SegmentBlock instance.
     */
    protected fun subAllocate(size: DataSize): ByteArray {
        require(allocationSize.toInt() - allocated >= size.toInt()) {
            "Not enough memory available to allocate the requested size."
        }
        require(size.toInt() in minSize.toInt()..maxSize.toInt()) {
            "Requested size must be between minSize and maxSize."
        }

        allocated += size.toInt()
        return ByteArray(size.toInt())
    }

    public fun allocate(size: Int): Bytes {
        val dataSize = DataSize.findLowestAbove(size)

        if(!segmentMap.containsKey(dataSize)) {
            segmentMap[dataSize] = mutableSetOf()
        }

        if(segmentMap.get(dataSize).isNullOrEmpty()) {
            val bytes = Bytes(this, subAllocate(dataSize))
            allSegments.add(bytes)
            return bytes
        } else {
            val bytes = segmentMap[dataSize]!!.first()
            segmentMap[dataSize]!!.remove(bytes)
            return bytes
        }
    }

    public fun recycle(bytes: Bytes) {
        check(bytes in allSegments) {
            "Memory block not managed by this MemoryManager."
        }
        segmentMap[DataSize.findLowestAbove(bytes.size)]!!.add(bytes)
    }

    override fun dispose() {
        allSegments.clear()
        segmentMap.clear()
    }
}