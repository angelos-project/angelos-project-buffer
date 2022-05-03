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

/**
 * Byte buffer implemented on the heap, as immutable.
 *
 * @constructor
 *
 * @param array ByteArray to wrap into a buffer
 * @param size
 * @param limit
 * @param position
 * @param endianness
 */
@Suppress("OVERRIDE_BY_INLINE")
@OptIn(ExperimentalUnsignedTypes::class)
class ByteBuffer internal constructor(
    array: ByteArray,
    size: Int,
    limit: Int,
    position: Int,
    endianness: Endianness
) : AbstractByteBuffer(size, limit, position, endianness), ImmutableHeapBuffer {
    private val _array = array
    private val _view = _array.asUByteArray()

    override inline fun load(index: Int): UByte {
        return _view[index]
    }

    override fun getArray(): ByteArray = _array
}