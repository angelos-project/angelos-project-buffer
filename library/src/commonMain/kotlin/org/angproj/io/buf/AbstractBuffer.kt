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
package org.angproj.io.buf

/**
 * Abstract buffer from which all buffer implementations must inherit.
 * Implements the basic logic regarding size, limit and endianness for reading and writing space.
 *
 * @constructor
 *
 * @param size Total size of the buffer.
 * @param limit The initial limitation of how far to operate into the buffer. Must never exceed the size.
 * @param endianness The initial current endianness of the buffer.
 */
abstract class AbstractBuffer internal constructor(
    size: Int,
    limit: Int,
    endianness: Endianness,
) : Buffer {
    override val size: Int = size

    protected var _limit: Int = limit
    override val limit: Int
        get() = _limit

    private var _endian: Endianness = endianness
    override var endian: Endianness
        get() = _endian
        set(value) {
            _endian = value
            _reverse = _endian != Buffer.nativeEndianness
        }

    private var _reverse: Boolean = _endian != Buffer.nativeEndianness
    override val reverse: Boolean
        get() = _reverse

    init {
        require(size >= limit)
    }
}