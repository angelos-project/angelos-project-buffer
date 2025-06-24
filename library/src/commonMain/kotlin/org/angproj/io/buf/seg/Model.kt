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
package org.angproj.io.buf.seg

import org.angproj.io.buf.mem.MemoryManager
import org.angproj.sec.SecureFeed

/**
 * Concrete implementation of [Segment] backed by a managed [LongArray].
 *
 * The `Model` class provides a fixed-size, cleanable byte segment that uses a [LongArray] as its underlying storage,
 * enabling efficient access and manipulation of 64-bit values. It supports read and write operations for primitive types
 * (byte, short, int, long) with bounds checking, and integrates with a [MemoryManager] for resource recycling and management.
 *
 * Key features:
 * - Provides random access to the underlying long array, mapping byte-based indices to 64-bit storage.
 * - Implements secure cleanup by overwriting the data with cryptographically secure random long values before recycling.
 * - Supports explicit resource disposal via [dispose], which clears and recycles the segment.
 * - Designed for use cases requiring managed, reusable, and securely cleanable memory segments with 64-bit alignment.
 *
 * @property memCtx The [MemoryManager] responsible for recycling and managing this segment instance.
 * @property data The underlying [LongArray] that stores the segment's contents.
 *
 * @constructor Creates a new `Model` segment with the given memory manager and long array.
 *
 * @see org.angproj.io.buf.seg.Segment
 * @see org.angproj.io.buf.mem.MemoryManager
 * @see org.angproj.sec.SecureFeed
 */
public class Model(
    private val memCtx: MemoryManager<Model>,
    private val data: LongArray
) : Segment<Model>(data.size * 8) {

    override fun getByte(index: Int): Byte {
        index.checkRangeByte<Unit>()
        // Implementation here
        TODO("Not yet implemented")
    }

    override fun getShort(index: Int): Short {
        index.checkRangeShort<Unit>()
        // Implementation here
        TODO("Not yet implemented")
    }

    override fun getInt(index: Int): Int {
        index.checkRangeInt<Unit>()
        // Implementation here
        TODO("Not yet implemented")
    }

    override fun getLong(index: Int): Long {
        index.checkRangeLong<Unit>()
        // Implementation here
        TODO("Not yet implemented")
    }

    override fun setByte(index: Int, value: Byte) {
        index.checkRangeByte<Unit>()
        // Implementation here
        TODO("Not yet implemented")
    }

    override fun setShort(index: Int, value: Short) {
        index.checkRangeShort<Unit>()
        // Implementation here
        TODO("Not yet implemented")
    }

    override fun setInt(index: Int, value: Int) {
        index.checkRangeInt<Unit>()
        // Implementation here
        TODO("Not yet implemented")
    }

    override fun setLong(index: Int, value: Long) {
        index.checkRangeLong<Unit>()
        // Implementation here
        TODO("Not yet implemented")
    }

    override fun dispose() {
        clear()
        SecureFeed.exportLongs(data, 0, data.size) { index, value ->
            data[index] = value
        }
        memCtx.recycle(this)
    }
}