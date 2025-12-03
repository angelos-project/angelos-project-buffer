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
import org.angproj.sec.util.ensure

public abstract class BytesPool(
    allocationSize: DataSize, minSize: DataSize, maxSize: DataSize
) : AbstractPoolManager<ByteArray, Bytes>(allocationSize, minSize, maxSize) {

    override fun subAllocate(size: DataSize): ByteArray {
        ensure(size.toInt() in minSize.toInt()..maxSize.toInt()) { MemoryException("Requested size must be between minSize and maxSize.") }
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
    override val totalSize: DataSize = DataSize.UNKNOWN
    override val segmentSize: DataSize = DataSize.UNKNOWN

    override fun allocate(): Nothing = unsupported()
    override fun allocate(size: Int): Nothing = unsupported()
    override fun recycle(segment: Bytes) { /* Don't touch! */ }
}