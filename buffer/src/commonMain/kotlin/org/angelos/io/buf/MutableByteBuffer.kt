/**
 * Copyright (c) 2021-2022 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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

@Suppress("OVERRIDE_BY_INLINE")
@OptIn(ExperimentalUnsignedTypes::class)
class MutableByteBuffer internal constructor(
    array: ByteArray,
    size: Int,
    limit: Int,
    position: Int,
    endianness: Endianness
) : AbstractMutableByteBuffer(size, limit, position, endianness), MutableHeapBuffer {
    private val _array = array
    private val _view = _array.asUByteArray()

    override inline fun load(index: Int): UByte {
        return _view[index]
    }

    override inline fun save(value: UByte, index: Int) {
        _view[index] = value
    }

    override fun getArray(): ByteArray = _array
}