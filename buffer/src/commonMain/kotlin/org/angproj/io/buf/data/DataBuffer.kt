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
package org.angproj.io.buf.data

import org.angproj.io.buf.Buffer
import org.angproj.io.buf.Retrievable

/**
 * Data buffer interface setting the standard for a data-buffer abstract base class.
 *
 * @constructor Create implementation of the DataBuffer interface.
 */
interface DataBuffer : Buffer, Retrievable {

    /**
     * Re-limits the size of the buffer.
     *
     * @param limit Must never exceed the size.
     */
    fun limit(limit: Int)

    /**
     * Calculates the remaining space between the current position and limit.
     * Use to find the space left until buffer overflow.
     *
     * @param position Given position.
     * @return The number of bytes remaining.
     */

    /**
     * Remaining
     *

     * @return
     */
    fun remaining(position: Int): Int

    /**
     * Checks if the buffer has enough remaining bytes left.
     * Throws BufferException if too little space left, that must be handled, otherwise the application crashes in order
     * to avoid undefined behavior.
     *
     * @param position Given position.
     * @param size The size of bytes requested.
     */
    fun hasRemaining(position: Int, size: Int)
}