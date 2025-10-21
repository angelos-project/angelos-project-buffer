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

import org.angproj.io.buf.seg.Bytes
import org.angproj.io.buf.seg.Memory
import org.angproj.io.buf.seg.Segment
import org.angproj.sec.util.TypeSize
import org.angproj.sec.util.floorMod


/**
 * Abstract buffer from which all buffer implementations must inherit.
 * Implements the basic logic regarding size, limit and endianness for reading and writing space.
 *
 * @constructor
 *
 * @param size Total size of the buffer.
 * @param limit The initial limitation of how far to operate into the buffer. Must never exceed the size.
 * @param endianness The initial current endianness of the buffer.
 */
public abstract class AbstractBuffer internal constructor(
    internal val segment: Segment<*>, protected val view: Boolean = false
) : Buffer {

    /**
     * Gives the max bytes capacity of the buffer
     * */
    public abstract override val capacity: Int

    /**
     * Gives the max size of the buffers represented item
     * */
    public abstract override val size: Int

    /**
     * The current limit of the buffer as defined.
     * */
    public abstract override val limit: Int

    override fun isView(): Boolean = view

    override fun isMem(): Boolean = segment is Memory

    override fun close() {
        if(!isView()) segment.dispose()
    }

    public override fun equals(other: Any?): Boolean {
        if(this === other) return true
        if(other == null || this::class != other::class) return false
        other as AbstractBuffer
        return compareTo(other) == 0
    }

    public infix fun contentEquals(other: AbstractBuffer?): Boolean {
        if (this === other) return true
        if (other !is AbstractBuffer) return false

        return segment.checkSum() == other.segment.checkSum()
    }

    override fun hashCode(): Int {
        var result = segment.hashCode()
        result = 31 * result + size
        result = 31 * result + limit
        result = 31 * result + capacity
        result = 31 * result + if(view) 1 else 0
        return result
    }

    public override operator fun compareTo(other: Buffer): Int { return hashCode() - other.hashCode() }
}

/**
 * Always copies with offset, idxFrom and idxTo, calculated in byte units.
 * */
public fun <E: AbstractBuffer, S: AbstractBuffer> E.copyInto(dest: S, offset: Int, idxFrom: Int, idxTo: Int) {
    // TODO Implement or search for complete unit tests
    val length = idxTo - idxFrom
    val srcLim = this.segment.limit
    val dstLim = dest.segment.limit

    require(idxFrom <= idxTo) {
        "Start index ($idxFrom) is larger than end index ($idxTo)" }
    require(length >= 0) {
        "Length ($length) can not be negative" }
    require(idxFrom in 0..<srcLim) {
        "Start index ($idxFrom) not in memory range" }
    require(idxFrom + length in 0..srcLim) {
        "End index (${idxFrom + length}) outside of memory range" }
    require(offset in 0..<dstLim) {
        "Destination offset ($offset) not in memory range" }
    require(offset + length in 0..dstLim) {
        "End index (${offset + length}) outside of memory range" }

    when {
        dest.segment is Bytes && segment is Bytes -> segment.copyInto(dest.segment, offset, idxFrom, idxTo)
        dest.segment is Memory && segment is Memory -> segment.copyInto(dest.segment, offset, idxFrom, idxTo)
        else -> {
            val destination = dest.segment
            var pos = 0

            repeat(length.floorDiv(TypeSize.longSize)) {
                destination.setLong(
                    offset + pos,
                    segment.getLong(idxFrom + pos)
                )
                pos += TypeSize.longSize
            }

            repeat(length.floorMod(TypeSize.longSize)) {
                destination.setByte(
                    offset + pos,
                    segment.getByte(idxFrom + pos)
                )
                pos++
            }
        }
    }
}