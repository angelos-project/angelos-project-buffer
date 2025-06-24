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

import org.angproj.io.buf.TypePointer
import org.angproj.io.buf.util.Cleanable
import org.angproj.io.buf.util.DataSize
import org.angproj.io.buf.util.unsupported

/**
 * Abstract base class representing a fixed-size, cleanable byte segment with read and write access.
 *
 * The `Segment` class extends [ByteString] and implements [Cleanable], providing a foundation for managing
 * a segment of bytes with a defined size and explicit resource management. It is designed for use cases
 * where memory segments or buffers require both controlled access and explicit cleanup.
 *
 * Key features:
 * - Enforces bounds checking for primitive access to prevent out-of-bounds operations.
 * - Supports explicit resource disposal via [dispose], which must be implemented by subclasses.
 * - Provides a method to obtain the native memory address of the segment, if supported.
 * - Includes a singleton [nullSegment] representing an empty or uninitialized segment.
 *
 * Type Parameters:
 * @param E The concrete type of the segment, enabling type-safe operations in subclasses.
 *
 * @property segSize The total fixed size of the segment in bytes.
 *
 * @constructor Creates a new `Segment` with the specified size.
 *
 * @see org.angproj.io.buf.seg.ByteString
 * @see org.angproj.io.buf.util.Cleanable
 */
public abstract class Segment<E: Segment<E>>(segSize: Int) : ByteString(segSize), Cleanable {

    /**
     * Disposes of the resources held by this segment.
     */
    public abstract override fun dispose()

    public open fun address(): TypePointer<*> = unsupported()

    public fun isNull(): Boolean = this === nullSegment

    public companion object {
        /**
         * A null block is a special case where the block does not contain any data.
         * It is used to represent an empty or uninitialized memory block.
         */
        public val nullSegment: Segment<*> by lazy { createNullSegment() }
    }
}

private fun Segment.Companion.createNullSegment(): Segment<*> {
    return object : Segment<Nothing>(DataSize.UNKNOWN.toInt()) {
        override fun dispose() = unsupported()
        override fun getByte(index: Int): Byte = unsupported()
        override fun getShort(index: Int): Short = unsupported()
        override fun getInt(index: Int): Int = unsupported()
        override fun getLong(index: Int): Long = unsupported()
        override fun setByte(index: Int, value: Byte) = unsupported()
        override fun setShort(index: Int, value: Short) = unsupported()
        override fun setInt(index: Int, value: Int) = unsupported()
        override fun setLong(index: Int, value: Long) = unsupported()
    }
}