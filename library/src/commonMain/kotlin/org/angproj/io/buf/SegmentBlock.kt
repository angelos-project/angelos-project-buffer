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


public class SegmentBlock(
    protected val parent: MemoryBlock<*>,
    protected val ptr: TypePointer<SegmentBlock>,
    protected val blockSize: Int
) : MemoryBlock<SegmentBlock>, ReadAccess, WriteAccess{
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

    override fun getByte(index: Int): Byte = NativeAccess.getByteNative<SegmentBlock>(index)

    override fun getShort(index: Int): Short = NativeAccess.getShortNative<SegmentBlock>(index)

    override fun getInt(index: Int): Int = NativeAccess.getIntNative<SegmentBlock>(index)

    override fun getLong(index: Int): Long = NativeAccess.getLongNative<SegmentBlock>(index)

    override fun setByte(index: Int, value: Byte) { NativeAccess.setByteNative<SegmentBlock>(index, value) }

    override fun setShort(index: Int, value: Short) { NativeAccess.setShortNative<SegmentBlock>(index, value) }

    override fun setInt(index: Int, value: Int) { NativeAccess.setIntNative<SegmentBlock>(index, value) }

    override fun setLong(index: Int, value: Long) { NativeAccess.setLongNative<SegmentBlock>(index, value) }

}

internal object NativeAccess

internal fun NativeAccess.unsupported(): Nothing {
    throw UnsupportedOperationException("Native memory management is not supported in WebAssembly.")
}

internal expect inline fun<reified R: Any> NativeAccess.getByteNative(index: Int): Byte

internal expect inline fun<reified R: Any> NativeAccess.getShortNative(index: Int): Short

internal expect inline fun<reified R: Any> NativeAccess.getIntNative(index: Int): Int

internal expect inline fun<reified R: Any> NativeAccess.getLongNative(index: Int): Long

internal expect inline fun<reified R: Any> NativeAccess.setByteNative(index: Int, value: Byte)

internal expect inline fun<reified R: Any> NativeAccess.setShortNative(index: Int, value: Short)

internal expect inline fun<reified R: Any> NativeAccess.setIntNative(index: Int, value: Int)

internal expect inline fun<reified R: Any> NativeAccess.setLongNative(index: Int, value: Long)