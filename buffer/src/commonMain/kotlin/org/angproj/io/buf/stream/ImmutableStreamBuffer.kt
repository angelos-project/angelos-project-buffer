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

import org.angproj.io.buf.Readable

/**
 * Immutable stream-buffer represents an immutable stream-buffer. Use this
 * interface as types on method parameters in order to allow third party implementations of buffers.
 *
 * @constructor Create implementation of the ImmutableStreamBuffer interface.
 */
interface ImmutableStreamBuffer : StreamBuffer, Readable {

    /**
     * Flips the buffer by setting limit to the value of position, and then setting the position
     * to 0.
     * This can only be done once before having to clear the buffer. Use with extreme CAUTION,
     * should only be used when filling from streams from an underlying layer.
     * Throws BufferException if called twice before clearing.
     *
     * @param limit
     */
    fun flip(limit: Int)
}