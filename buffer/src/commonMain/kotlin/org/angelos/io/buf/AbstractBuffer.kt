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
package org.angelos.io.buf

import kotlin.math.absoluteValue
import kotlin.math.min

/**
 * Abstract buffer from which all buffer implementations must inherit.
 * Implements the basic logic regarding size, position and limit of reading and writing space.
 *
 * @constructor
 *
 * @param size max size of the buffer
 * @param limit initial limit if partial data already exists
 * @param position initial position in an already existing data stream
 * @param endianness endian of the buffered data
 */
abstract class AbstractBuffer internal constructor(
    size: Int,
    limit: Int,
    position: Int,
    endianness: Endianness,
) : Buffer {
    override val size: Int = size.absoluteValue

    private var _limit: Int
    override val limit: Int
        get() = _limit

    internal var _position: Int
    override val position: Int
        get() = _position

    private var _reverse: Boolean
    override val reverse: Boolean
        get() = _reverse

    private var _endian: Endianness
    override var endian: Endianness
        get() = _endian
        set(value) {
            _endian = value
            _reverse = _endian != Buffer.nativeEndianness
        }

    override val optimized
        get() = false

    init {
        _limit = min(size.absoluteValue, limit.absoluteValue)
        _position = min(limit.absoluteValue, position.absoluteValue)
        _endian = endianness
        _reverse = _endian != Buffer.nativeEndianness
    }

    override fun clear() {
        _limit = size
        _position = 0
    }

    override fun flip() {
        _limit = _position
        _position = 0
    }

    override fun rewind() {
        _position = 0
    }
}