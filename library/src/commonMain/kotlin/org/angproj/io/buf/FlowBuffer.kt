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

import org.angproj.io.buf.seg.Memory
import org.angproj.io.buf.seg.Segment


public abstract class FlowBuffer protected constructor(
    segment: Segment<*>, view: Boolean = false
): AbstractBuffer(segment, view) {

    private var _innerOffset: Int = 0
    private var _count: Long = 0
     public val count: Long
         get() = _count + (_position - _innerOffset)

    /**
     * Gives the max capacity of the buffer
     * */
    public override val capacity: Int
        get() = segment.size

    protected var _position: Int = 0
    /**
     * The current position of the cursor in the buffer.
     * */
    public val position: Int
        get() = _position

    /**
     * Jump to a defined position.
     * The newPos has to be between the current mark and the current limit.
     * */
    public fun positionAt(newPos: Int) {
        require(newPos in _mark..segment.limit)
        _count += _position - _innerOffset
        _position = newPos
        _innerOffset = _position
    }

    override val size: Int
        get() = segment.size

    /**
     * The current limit of the buffer as defined.
     * */
    public override val limit: Int
        get() = segment.limit

    /**
     * Set a new limit of the buffer.
     * The newLimit must be between the current mark and given capacity.
     * If the limit is before the current position, the position is moved to the new limit.
     * */
    public fun limitAt(newLimit: Int) {
        require(newLimit in _mark..segment.size)
        segment.limitAt(newLimit)
        if(_position > newLimit) positionAt(newLimit) //_position = newLimit
    }

    protected var _mark: Int = 0
    /**
     * Given mark at position zero or as latest defined.
     * */
    public val mark: Int
        get() = _mark

    /**
     * Set the mark defined by the current position.
     * */
    public fun markAt() {
        _mark = _position
    }

    /**
     * Reset the current position to the latest defined mark.
     * */
    public fun reset() {
        positionAt(_mark) //_position = _mark
    }

    /**
     * Rewinds and set limit to the given capacity.
     * */
    public fun clear() {
        rewind()
        segment.limitAt(segment.size)
    }

    /**
     *  Flips the limit to the current position,
     *  and secondly rewinds.
     * */
    public fun flip() {
        segment.limitAt(_position)
        rewind()
    }

    /**
     * Sets both the position and mark to zero.
     * */
    public fun rewind() {
        _mark = 0
        positionAt(0) // _position = 0
    }

    /**
     * Gives the remaining bytes between position and limit.
     * */
    public val remaining: Int
        get() = segment.limit - _position

    override fun isView(): Boolean = view

    override fun isMem(): Boolean = segment is Memory

    override fun close() {
        segment.dispose()
    }

    public fun isNull(): Boolean = this === nullBuffer

    public companion object {
        /**
         * A null block is a special case where the block does not contain any data.
         * It is used to represent an empty or uninitialized memory block.
         */
        public val nullBuffer: FlowBuffer by lazy { createNullBuffer() }
    }
}

private fun FlowBuffer.Companion.createNullBuffer(): FlowBuffer {
    return object : FlowBuffer(
        Segment.nullSegment, false
    ) {
        override val size: Int = 0
    }
}