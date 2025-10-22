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
import org.angproj.sec.util.ensure


public abstract class AbstractFlowBuffer protected constructor(
    segment: Segment<*>, view: Boolean = false
): AbstractBuffer(segment, view), FlowBuffer {

    /**
     * Gives the max capacity of the buffer
     * */
    public override val capacity: Int
        get() = segment.size

    protected var _position: Int = 0

    /**
     * The current position of the cursor in the buffer.
     * */
    public override val position: Int
        get() = _position

    /**
     * Jump to a defined position.
     * The newPos has to be between the current mark and the current limit.
     * */
    public override fun positionAt(newPos: Int) {
        ensure<BufferException>(newPos in _mark..segment.limit) { BufferException("New position outside buffer") }
        _position = newPos
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
    public override fun limitAt(newLimit: Int) {
        ensure<BufferException>(_mark in 0..segment.size) { BufferException("New limit outside mark") }
        segment.limitAt(newLimit)
        if(_position > newLimit) positionAt(newLimit) //_position = newLimit
    }

    protected var _mark: Int = 0
    /**
     * Given mark at position zero or as latest defined.
     * */
    public override val mark: Int
        get() = _mark

    /**
     * Set the mark defined by the current position.
     * */
    public override fun markAt() {
        _mark = _position
    }

    /**
     * Reset the current position to the latest defined mark.
     * */
    public override fun reset() {
        positionAt(_mark) //_position = _mark
    }

    /**
     * Rewinds and set limit to the given capacity.
     * */
    public override fun clear() {
        rewind()
        segment.limitAt(segment.size)
    }

    /**
     *  Flips the limit to the current position,
     *  and secondly rewinds.
     * */
    public override fun flip() {
        segment.limitAt(_position)
        rewind()
    }

    /**
     * Sets both the position and mark to zero.
     * */
    public override fun rewind() {
        _mark = 0
        positionAt(0) // _position = 0
    }

    /**
     * Gives the remaining bytes between position and limit.
     * */
    public override val remaining: Int
        get() = segment.limit - _position

    override fun isView(): Boolean = view

    override fun isMem(): Boolean = segment is Memory

    override fun close() {
        segment.dispose()
    }

    public override fun isNull(): Boolean = this === nullBuffer

    public companion object Companion {
        /**
         * A null block is a special case where the block does not contain any data.
         * It is used to represent an empty or uninitialized memory block.
         */
        public val nullBuffer: AbstractFlowBuffer by lazy { createNullBuffer() }
    }
}

private fun AbstractFlowBuffer.Companion.createNullBuffer(): AbstractFlowBuffer {
    return object : AbstractFlowBuffer(
        Segment.nullSegment, false
    ) {
        override val size: Int = 0
    }
}