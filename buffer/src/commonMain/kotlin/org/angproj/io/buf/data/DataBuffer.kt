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

interface DataBuffer : Buffer, Retrievable {

    /**
     * Resetting the buffer by relimiting and by default zeroing the data.
     *
     * @param limit new inner limit of data to be stored
     * @param zeroing resetting fo the actual memory reserved as storage
     */
    fun reset(limit: Int = size, zeroing: Boolean = true)

    /**
     * Remaining space between the current position and limit.
     *
     * @return number of bytes remaining
     */
    fun remaining(position: Int): Int

    /**
     * Has enough remaining bytes left or throws BufferException.
     *
     * @param size needed space
     */
    fun hasRemaining(position: Int, size: Int)
}