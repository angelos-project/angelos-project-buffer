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
import org.angproj.io.buf.util.toInt
import org.angproj.io.buf.util.unsupported

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