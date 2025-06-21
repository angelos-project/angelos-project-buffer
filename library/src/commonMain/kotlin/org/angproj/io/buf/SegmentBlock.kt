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
package org.angproj.io.buf

/**
 * A SegmentBlock is a memory block that represents a segment of memory.
 * It is used to read and write data to and from the memory.
 * It is a fixed size block of memory that can be used to store data.
 * It is used in conjunction with the MemoryManager to allocate and recycle memory.
 */
public class SegmentBlock(
    protected val parent: MemoryBlock<*>,
    protected val ptr: TypePointer<SegmentBlock>,
    protected val blockSize: Int
) : MemoryBlock<SegmentBlock>, ReadAccess, WriteAccess {

    init {
        require(blockSize > 0) { "Block size must be greater than zero" }
    }

    override val parentBlock: MemoryBlock<*>
        get() = parent

    override fun getPointer(): TypePointer<SegmentBlock> {
        return ptr
    }

    protected var blockLimit: Int = blockSize
    override val limit: Int
        get() = blockLimit

    override fun limitAt(newLimit: Int) {
        require(newLimit in 0..blockSize) { "New limit must be between 0 and size" }
        blockLimit = newLimit
    }

    override val size: Int
        get() = blockSize

    override fun getByte(index: Int): Byte = NativeAccess.getByteNative<SegmentBlock>(index + ptr.toLong())

    override fun getShort(index: Int): Short = NativeAccess.getShortNative<SegmentBlock>(index + ptr.toLong())

    override fun getInt(index: Int): Int = NativeAccess.getIntNative<SegmentBlock>(index + ptr.toLong())

    override fun getLong(index: Int): Long = NativeAccess.getLongNative<SegmentBlock>(index + ptr.toLong())

    override fun setByte(index: Int, value: Byte) { NativeAccess.setByteNative<SegmentBlock>(index + ptr.toLong(), value) }

    override fun setShort(index: Int, value: Short) { NativeAccess.setShortNative<SegmentBlock>(index + ptr.toLong(), value) }

    override fun setInt(index: Int, value: Int) { NativeAccess.setIntNative<SegmentBlock>(index + ptr.toLong(), value) }

    override fun setLong(index: Int, value: Long) { NativeAccess.setLongNative<SegmentBlock>(index + ptr.toLong(), value) }

}


internal object NativeAccess

internal fun NativeAccess.unsupported(): Nothing {
    throw UnsupportedOperationException("Native memory management is not available.")
}

internal expect inline fun<reified R: Any> NativeAccess.getByteNative(index: Long): Byte

internal expect inline fun<reified R: Any> NativeAccess.getShortNative(index: Long): Short

internal expect inline fun<reified R: Any> NativeAccess.getIntNative(index: Long): Int

internal expect inline fun<reified R: Any> NativeAccess.getLongNative(index: Long): Long

internal expect inline fun<reified R: Any> NativeAccess.setByteNative(index: Long, value: Byte)

internal expect inline fun<reified R: Any> NativeAccess.setShortNative(index: Long, value: Short)

internal expect inline fun<reified R: Any> NativeAccess.setIntNative(index: Long, value: Int)

internal expect inline fun<reified R: Any> NativeAccess.setLongNative(index: Long, value: Long)