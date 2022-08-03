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
 * Stream buffer interface setting the standard for a stream-buffer abstract base class.
 *
 * @constructor Create implementation of the StreamBuffer interface.
 */
interface StreamBuffer : Buffer, Readable {
    /**
     * The current position for operations within the allocated memory of the buffer. Must never exceed the limit.
     */
    val position: Int

    /**
     * Clears the buffer by setting position to 0 and limit to the value of size.
     * This function should be executed if the buffer should be reused without allocating a new one.
     * This is very practical in a high performance IO application.
     */
    fun clear()

    /**
     * Flips the buffer by setting limit to the value of position, and then setting the position
     * to 0.
     * This is used after the buffer has been written to, and is being prepared for reading from.
     *
     */
    fun flip()

    /**
     * Rewinds the buffer by setting position to 0 without touching the limit.
     */
    fun rewind()

    /**
     * Calculates the remaining space between the current position and limit.
     * Use to find the space left until buffer overflow.
     *
     * @return The number of bytes remaining.
     */
    fun remaining(): Int

    /**
     * Checks if the buffer has enough remaining bytes left.
     * Throws BufferException if too little space left, that must be handled, otherwise the application crashes in order
     * to avoid undefined behavior.
     *
     * @param size The size of bytes requested.
     */
    fun hasRemaining(size: Int)
}