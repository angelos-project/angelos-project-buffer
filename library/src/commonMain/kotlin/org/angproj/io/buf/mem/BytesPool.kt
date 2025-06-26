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

import org.angproj.io.buf.seg.Bytes
import org.angproj.io.buf.util.DataSize
import org.angproj.io.buf.util.unsupported

public abstract class BytesPool(
    allocationSize: DataSize, minSize: DataSize, maxSize: DataSize
) : AbstractPoolManager<ByteArray, Bytes>(allocationSize, minSize, maxSize) {

    protected var allocated: Int = 0

    override fun subAllocate(size: DataSize): ByteArray {
        require(allocationSize.toInt() - allocated >= size.toInt()) {
            "Not enough memory available to allocate the requested size."
        }
        require(size.toInt() in minSize.toInt()..maxSize.toInt()) {
            "Requested size must be between minSize and maxSize."
        }

        allocated += size.toInt()
        return ByteArray(size.toInt())
    }

    override fun createSegment(
        data: ByteArray
    ): Bytes {
        return Bytes(this, data)
    }

    public companion object {
        public val nullManager : MemoryManager<Bytes> by lazy {
            createNullManager()
        }
    }
}

private fun BytesPool.Companion.createNullManager(): MemoryManager<Bytes> = object : MemoryManager<Bytes> {
    override val allocationSize: DataSize = DataSize.UNKNOWN
    override val segmentSize: DataSize = DataSize.UNKNOWN

    override fun allocate(): Nothing = unsupported()
    override fun allocate(size: Int): Nothing = unsupported()
    override fun recycle(segment: Bytes) { /* Don't touch! */ }
}