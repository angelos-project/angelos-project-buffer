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
import org.angproj.sec.SecureRandom

/**
 * Concrete implementation of [Segment] backed by a managed [ByteArray].
 *
 * The `Bytes` class provides a fixed-size, cleanable byte segment that uses a [ByteArray] as its underlying storage.
 * It supports efficient read and write operations for primitive types (byte, short, int, long) with bounds checking,
 * and integrates with a [MemoryManager] for resource recycling and management.
 *
 * Key features:
 * - Provides random access to the underlying byte array with safe bounds checking for all supported types.
 * - Implements secure cleanup by overwriting the data with cryptographically secure random bytes before recycling.
 * - Supports explicit resource disposal via [closeImpl], which clears and recycles the segment.
 * - Designed for use cases requiring managed, reusable, and securely cleanable memory segments.
 *
 * @property memCtx The [MemoryManager] responsible for recycling and managing this segment instance.
 * @property data The underlying [ByteArray] that stores the segment's contents.
 *
 * @constructor Creates a new `Bytes` segment with the given memory manager and byte array.
 *
 * @see org.angproj.io.buf.seg.Segment
 * @see org.angproj.io.buf.mem.MemoryManager
 * @see org.angproj.sec.SecureRandom
 */
public class Bytes(
    private val memCtx: MemoryManager<Bytes>,
    private val data: ByteArray
) : Segment<Bytes>(data.size) {


    override fun getByte(index: Int): Byte {
        index.checkRangeByte<Unit>()
        return data[index]
    }

    override fun getShort(index: Int, revOrder: Boolean): Short {
        index.checkRangeShort<Unit>()
        return getShort<Unit>(data, index)
    }

    override fun getInt(index: Int, revOrder: Boolean): Int {
        index.checkRangeInt<Unit>()
        return getInt<Unit>(data, index)
    }

    override fun getLong(index: Int, revOrder: Boolean): Long {
        index.checkRangeLong<Unit>()
        return getLong<Unit>(data, index)
    }

    override fun setByte(index: Int, value: Byte) {
        index.checkRangeByte<Unit>()
        data[index] = value
    }

    override fun setShort(index: Int, value: Short, revOrder: Boolean) {
        index.checkRangeShort<Unit>()
        setShort<Unit>(data, index, value)
    }

    override fun setInt(index: Int, value: Int, revOrder: Boolean) {
        index.checkRangeInt<Unit>()
        setInt<Unit>(data, index, value)
    }

    override fun setLong(index: Int, value: Long, revOrder: Boolean) {
        index.checkRangeLong<Unit>()
        setLong<Unit>(data, index, value)
    }

    override fun closeImpl() {
        clear()
        SecureRandom.readBytes(data)
        memCtx.recycle(this)
    }

    internal fun copyInto(dest: Bytes, offset: Int, idxFrom: Int, idxTo: Int) {
        this.data.copyInto(dest.data, offset, idxFrom, idxTo)
    }
}