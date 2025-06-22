/**
 * Copyright (c) 2024 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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


public abstract class BlockBuffer internal constructor(
    segment: Segment<*>, view: Boolean = false
) : AbstractBuffer(segment, view) {

    override val size: Int
        get() = segment.size

    override val limit: Int
        get() = segment.limit

    override val capacity: Int
        get() = segment.size

    /**
     * The same as on Buffer with upper limit.
     * */
    public fun limitAt(newLimit: Int) {
        require(newLimit in 0..segment.size)
        segment.limitAt(newLimit)
    }

    /**
     * Reduced function compared to Buffer interface due to no rewind capability.
     * */
    public fun clear() {
        segment.limitAt(segment.size)
    }

    internal inline fun <reified E : Any> remaining(position: Int): Int = segment.limit - position
}