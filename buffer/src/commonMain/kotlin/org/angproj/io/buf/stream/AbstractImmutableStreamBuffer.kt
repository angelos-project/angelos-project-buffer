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

import org.angproj.io.buf.BufferException
import org.angproj.io.buf.Endianness

/**
 * Abstract base class for all immutable stream-buffers.
 *
 * @constructor
 *
 * @param size Total size of the buffer.
 * @param limit The initial limitation of how far to operate into the buffer. Must never exceed the size.
 * @param position The initial position in the buffer.
 * @param endianness The initial current endianness of the buffer.
 */
abstract class AbstractImmutableStreamBuffer internal constructor(
    size: Int,
    limit: Int,
    position: Int,
    endianness: Endianness,
) : AbstractStreamBuffer(size, limit, position, endianness), ImmutableStreamBuffer {
    override fun flip(limit: Int) {
        if(_limit != size)
            throw BufferException("Immutable stream buffers can not be flipped twice without clearing.")

        check(0 <= limit)
        check(limit <= size)

        _limit = limit
        _position = 0
    }
}