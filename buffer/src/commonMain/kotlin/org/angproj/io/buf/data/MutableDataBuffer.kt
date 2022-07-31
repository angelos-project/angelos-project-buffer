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

import org.angproj.io.buf.MutableBuffer
import org.angproj.io.buf.Retrievable
import org.angproj.io.buf.Storable

/**
 * Mutable data buffer interface implementing both Retrievable and Storable.
 *
 * @constructor Create empty Mutable data buffer
 */
interface MutableDataBuffer : DataBuffer, MutableBuffer, Retrievable, Storable {
    /**
     * Resetting the buffer by zeroing the data.
     *
     */
    fun reset()
}