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
import org.angproj.io.buf.util.DataSize
import org.angproj.io.buf.util.unsupported

/**
 * A memory manager is responsible for allocating and recycling segments of memory.
 * It provides methods to allocate segments of a specific size or the default size,
 * and to recycle segments when they are no longer needed.
 *
 * @param S The type of segment managed by this memory manager.
 */
public interface MemoryManager<S: Segment<S>> {

    public val totalSize: DataSize
    public val segmentSize: DataSize

    public fun allocate(): S

    public fun allocate(size: Int): S

    public fun recycle(segment: S)

    public companion object {
        /**
         * Creates a null memory manager that does not allocate any memory.
         * This is useful for cases where no memory management is needed.
         */
        public val nullManager : MemoryManager<*> by lazy {
            createNullManager()
        }

        public fun req(expr: Boolean, msg: String) {
            if( !expr ) throw MemoryException(msg)
        }
    }
}

private fun MemoryManager.Companion.createNullManager(): MemoryManager<*> = object : MemoryManager<Nothing> {
    override val totalSize: DataSize = DataSize.UNKNOWN
    override val segmentSize: DataSize = DataSize.UNKNOWN

    override fun allocate(): Nothing = unsupported()
    override fun allocate(size: Int): Nothing = unsupported()
    override fun recycle(segment: Nothing) { /* Don't touch! */ }

}