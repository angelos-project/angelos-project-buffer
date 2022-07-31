/**
 * Copyright (c) 2022 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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
package org.angproj.io.buf.stream

import org.angproj.io.buf.Buffer
import org.angproj.io.buf.Readable

/**
 * Stream buffer interface implementing Buffer and Readable.
 *
 * @constructor Create empty Stream buffer
 */
interface StreamBuffer : Buffer, Readable {
    /**
     * Current position for operations in the buffer. Must never exceed the limit.
     */
    val position: Int

    /**
     * Clears the buffer by setting position to beginning and limit to capacity, for performing
     * a reuse of the buffer for operations from scratch.
     */
    fun clear()

    /**
     * Flips the buffer by setting limit to the current position, and then setting the position
     * to the beginning, for performing getting operations after setting values.
     *
     */
    fun flip()

    /**
     * Rewinds the buffer by setting position to the beginning without touching the limit, for
     * rereading operations on the buffer.
     */
    fun rewind()

    /**
     * Remaining space between the current position and limit.
     *
     * @return number of bytes remaining
     */
    fun remaining(): Int

    /**
     * Has enough remaining bytes left or throws BufferException.
     *
     * @param size needed space
     */
    fun hasRemaining(size: Int)
}