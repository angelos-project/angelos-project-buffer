/**
 * Copyright (c) 2021-2025 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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

import org.angproj.io.buf.seg.Segment
import org.angproj.io.buf.util.unsupported


public abstract class ArrayBuffer<E> protected constructor(
    segment: Segment<*>, view: Boolean = false, protected val typeSize: Int
): AbstractBuffer(segment, view), Iterable<E>{

    override fun iterator(): Iterator<E> = object: Iterator<E> {
        private var index = 0
        override fun hasNext(): Boolean = index < limit
        override fun next(): E = this@ArrayBuffer[index++]
    }

    public val indices: IntRange by lazy { 0..<limit }
    public val lastIndex: Int = limit - 1

    public final override val capacity: Int
        get() = segment.size

    public final override val size: Int
        get() = segment.size / typeSize

    public final override val limit: Int
        get() = segment.limit / typeSize

    public abstract operator fun get(index: Int): E
    public abstract operator fun set(index: Int, value: E)

    public fun isNull(): Boolean = this === nullBuffer

    public companion object {
        /**
         * A null block is a special case where the block does not contain any data.
         * It is used to represent an empty or uninitialized memory block.
         */
        public val nullBuffer: ArrayBuffer<*> by lazy { createNullBuffer() }
    }
}

private fun ArrayBuffer.Companion.createNullBuffer(): ArrayBuffer<*> {
    return object : ArrayBuffer<Nothing>(
        Segment.nullSegment, false, Int.MIN_VALUE
    ) {
        override fun get(index: Int): Nothing { unsupported() }
        override fun set(index: Int, value: Nothing) { unsupported() }
    }
}