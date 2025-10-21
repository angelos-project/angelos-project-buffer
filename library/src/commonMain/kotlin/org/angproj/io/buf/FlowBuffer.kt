/**
 * Copyright (c) 2025 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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


public interface FlowBuffer : Buffer {

    /**
     * Gives the max capacity of the buffer
     * */
    public override val capacity: Int


    /**
     * The current position of the cursor in the buffer.
     * */
    public val position: Int

    /**
     * Jump to a defined position.
     * The newPos has to be between the current mark and the current limit.
     * */
    public fun positionAt(newPos: Int)

    override val size: Int

    /**
     * The current limit of the buffer as defined.
     * */
    public override val limit: Int

    /**
     * Set a new limit of the buffer.
     * The newLimit must be between the current mark and given capacity.
     * If the limit is before the current position, the position is moved to the new limit.
     * */
    public fun limitAt(newLimit: Int)

    /**
     * Given mark at position zero or as latest defined.
     * */
    public val mark: Int

    /**
     * Set the mark defined by the current position.
     * */
    public fun markAt()

    /**
     * Reset the current position to the latest defined mark.
     * */
    public fun reset()

    /**
     * Rewinds and set limit to the given capacity.
     * */
    public fun clear()

    /**
     *  Flips the limit to the current position,
     *  and secondly rewinds.
     * */
    public fun flip()

    /**
     * Sets both the position and mark to zero.
     * */
    public fun rewind()

    /**
     * Gives the remaining bytes between position and limit.
     * */
    public val remaining: Int

    override fun isView(): Boolean

    override fun isMem(): Boolean

    override fun close()

    public fun isNull(): Boolean
}