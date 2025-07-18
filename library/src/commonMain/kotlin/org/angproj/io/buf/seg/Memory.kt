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

import org.angproj.io.buf.NativeAccess
import org.angproj.io.buf.SegmentBlock
import org.angproj.io.buf.TypePointer
import org.angproj.io.buf.getByteNative
import org.angproj.io.buf.getLongNative
import org.angproj.io.buf.mem.MemoryManager
import org.angproj.io.buf.setByteNative
import org.angproj.io.buf.setLongNative
import org.angproj.sec.util.TypeSize
import org.angproj.sec.util.floorMod


/**
 * Concrete implementation of [Segment] backed by a native or direct memory segment.
 *
 * The `Memory` class provides a fixed-size, cleanable byte segment that uses a [SegmentBlock] as its underlying storage,
 * typically representing native or direct memory. It supports efficient read and write operations for primitive types
 * (byte, short, int, long) with bounds checking, and integrates with a [MemoryManager] for resource recycling and management.
 *
 * Key features:
 * - Provides random access to the underlying memory segment with safe bounds checking for all supported types.
 * - Implements secure cleanup by clearing and randomizing the memory contents before recycling.
 * - Supports explicit resource disposal via [dispose], which securely clears and recycles the segment.
 * - Exposes the native memory address through [address], enabling interoperability with low-level APIs.
 *
 * @property memCtx The [MemoryManager] responsible for recycling and managing this memory segment instance.
 * @property data The underlying [SegmentBlock] that stores the segment's contents, typically representing native memory.
 *
 * @constructor Creates a new `Memory` segment with the given memory manager and segment block.
 *
 * @see org.angproj.io.buf.seg.Segment
 * @see org.angproj.io.buf.mem.MemoryManager
 * @see org.angproj.io.buf.SegmentBlock
 */
public class Memory(
    private val memCtx: MemoryManager<Memory>,
    private val data: SegmentBlock
) : Segment<Memory>(data.size) {

    init {
        check(data.size >= stringSize) { "Memory size must be at least $stringSize bytes, but was ${data.size} bytes." }
    }

    override val limit: Int
        get() = stringLimit

    override fun limitAt(newLimit: Int) {
        super.limitAt(newLimit)
        data.limitAt(newLimit)
    }

    override val size: Int
        get() = stringSize

    override fun getByte(index: Int): Byte {
        index.checkRangeByte<Unit>()
        return data.getByte(index)
    }

    override fun getShort(index: Int): Short {
        index.checkRangeShort<Unit>()
        return data.getShort(index)
    }

    override fun getInt(index: Int): Int {
        index.checkRangeInt<Unit>()
        return data.getInt(index)
    }

    override fun getLong(index: Int): Long {
        index.checkRangeLong<Unit>()
        return data.getLong(index)
    }

    override fun setByte(index: Int, value: Byte) {
        index.checkRangeByte<Unit>()
        data.setByte(index, value)
    }

    override fun setShort(index: Int, value: Short) {
        index.checkRangeShort<Unit>()
        data.setShort(index, value)
    }

    override fun setInt(index: Int, value: Int) {
        index.checkRangeInt<Unit>()
        data.setInt(index, value)
    }

    override fun setLong(index: Int, value: Long) {
        index.checkRangeLong<Unit>()
        data.setLong(index, value)
    }

    override fun dispose() {
        clear()
        securelyRandomize()
        memCtx.recycle(this)
    }

    override fun address(): TypePointer<*> = data.getPointer()

    internal fun copyInto(dest: Memory, offset: Int, idxFrom: Int, idxTo: Int) {
        val length = idxTo - idxFrom

        val srcPtr = (data.getPointer().ptr + idxFrom)
        val dstPtr = dest.data.getPointer().ptr + offset
        var pos = 0L

        repeat(length.floorDiv(TypeSize.longSize)) {
            NativeAccess.setLongNative<Unit>(
                dstPtr + pos,
                NativeAccess.getLongNative<Unit>(srcPtr + pos)
            )
            pos += TypeSize.longSize
        }

        repeat(length.floorMod(TypeSize.longSize)) {
            NativeAccess.setByteNative<Unit>(
                dstPtr + pos,
                NativeAccess.getByteNative<Unit>(srcPtr + pos)
            )
            pos++
        }
    }
}
