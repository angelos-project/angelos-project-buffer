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
import org.angproj.sec.util.TypeSize

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
 * - Supports explicit resource disposal via [closeImpl], which clears and recycles the segment.
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
        return data.chunkGet<Unit>(index / TypeSize.longSize, index % TypeSize.longSize, TypeSize.byteSize).toByte()
    }

    override fun getShort(index: Int, revOrder: Boolean): Short {
        index.checkRangeShort<Unit>()
        return data.chunkGet<Unit>(index / TypeSize.longSize, index % TypeSize.longSize, TypeSize.shortSize).toShort()
    }

    override fun getInt(index: Int, revOrder: Boolean): Int {
        index.checkRangeInt<Unit>()
        return data.chunkGet<Unit>(index / TypeSize.longSize, index % TypeSize.longSize, TypeSize.intSize).toInt()
    }

    override fun getLong(index: Int, revOrder: Boolean): Long {
        index.checkRangeLong<Unit>()
        return data.chunkGet<Unit>(index / TypeSize.longSize, index % TypeSize.longSize, TypeSize.longSize)
    }

    override fun setByte(index: Int, value: Byte) {
        index.checkRangeByte<Unit>()
        data.chunkSet<Unit>(index / TypeSize.longSize, index % TypeSize.longSize, value.toLong(), byteMask, TypeSize.byteSize)
    }

    override fun setShort(index: Int, value: Short, revOrder: Boolean) {
        index.checkRangeShort<Unit>()
        data.shortSet<Unit>(index / TypeSize.longSize, index % TypeSize.longSize, value.toLong(), shortMask, TypeSize.shortSize)
    }

    override fun setInt(index: Int, value: Int, revOrder: Boolean) {
        index.checkRangeInt<Unit>()
        data.chunkSet<Unit>(index / TypeSize.longSize, index % TypeSize.longSize, value.toLong(), intMask, TypeSize.intSize)
    }

    override fun setLong(index: Int, value: Long, revOrder: Boolean) {
        index.checkRangeLong<Unit>()
        data.chunkSet<Unit>(index / TypeSize.longSize, index % TypeSize.longSize, value, longMask, TypeSize.longSize)
    }

    private inline fun <reified R: Any> LongArray.chunkGet(off: Int, idx: Int, size: Int): Long = ((
            get(off) ushr (idx * TypeSize.longSize)) or if(idx > TypeSize.longSize - size) ((
            get(off + 1) and (-1L shl ((idx - size) * TypeSize.longSize)).inv()) shl ((
            TypeSize.longSize - idx) * TypeSize.longSize)) else 0)

    private inline fun <reified R: Any> LongArray.chunkSet(off: Int, idx: Int, value: Long, mask: Long, size: Int) {
        val pos = idx * TypeSize.longSize
        set(off, (get(off) and (mask shl pos).inv()) or (value shl pos))
        if(idx > TypeSize.longSize - size) set(off + 1, ((
                get(off + 1) and (-1L shl ((idx - size) * TypeSize.longSize))) or (
                value ushr ((TypeSize.longSize - idx) * TypeSize.longSize))))
    }

    private inline fun <reified R: Any> LongArray.shortSet(off: Int, idx: Int, value: Long, mask: Long, size: Int) {
        val pos = idx * TypeSize.longSize
        set(off, (get(off) and (mask shl pos).inv()) or (value shl pos))
        if(idx > TypeSize.longSize - size) set(off + 1, ((get(off + 1) and 0xff.inv()) or (value ushr TypeSize.longSize)))
    }

    private inline fun <reified R: Any> shortReverse(value: Short, swap: Boolean): Short = when(swap) {
        true -> swapShort<Unit>(value)
        false -> value
    }

    private inline fun <reified R: Any> intReverse(value: Int, swap: Boolean): Int = when(swap) {
        true -> swapInt<Unit>(value)
        false -> value
    }

    private inline fun <reified R: Any> longReverse(value: Long, swap: Boolean): Long = when(swap) {
        true -> swapLong<Unit>(value)
        false -> value
    }

    override fun closeImpl() {
        clear()
        SecureFeed.readLongs(data, 0, data.size) { index, value ->
            data[index] = value
        }
        memCtx.recycle(this)
    }

    public companion object {
        public const val longMask: Long = -1
        public const val intMask: Long = 0xFFFFFFFF
        public const val shortMask: Long = 0xFFFF
        public const val byteMask: Long = 0xFF
    }
}