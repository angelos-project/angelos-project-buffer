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

import org.angproj.io.buf.Endianness
import org.angproj.io.buf.swapEndian

/**
 * Data byte buffer
 *
 * @constructor
 *
 * @param array
 * @param size
 * @param limit
 * @param endianness
 */
@OptIn(ExperimentalUnsignedTypes::class)
@Suppress("OVERRIDE_BY_INLINE")
actual class DataByteBuffer internal actual constructor(
    array: ByteArray,
    size: Int,
    limit: Int,
    endianness: Endianness,
) : AbstractDataBuffer(size, limit, endianness), ImmutableHeapDataBuffer {
    private val _array = array

    override inline fun readByte(position: Int): Byte = _array[position]

    override inline fun readUByte(position: Int): UByte = _array.getUByteAt(position)

    override inline fun readChar(position: Int): Char = when (reverse) {
        true -> _array.getCharAt(position).swapEndian()
        false -> _array.getCharAt(position)
    }

    override inline fun readShort(position: Int): Short = when (reverse) {
        true -> _array.getShortAt(position).swapEndian()
        false -> _array.getShortAt(position)
    }

    override inline fun readUShort(position: Int): UShort = when (reverse) {
        true -> _array.getUShortAt(position).swapEndian()
        false -> _array.getUShortAt(position)
    }

    override inline fun readInt(position: Int): Int = when (reverse) {
        true -> _array.getIntAt(position).swapEndian()
        false -> _array.getIntAt(position)
    }

    override inline fun readUInt(position: Int): UInt = when (reverse) {
        true -> _array.getUIntAt(position).swapEndian()
        false -> _array.getUIntAt(position)
    }

    override inline fun readLong(position: Int): Long = when (reverse) {
        true -> _array.getLongAt(position).swapEndian()
        false -> _array.getLongAt(position)
    }

    override inline fun readULong(position: Int): ULong = when (reverse) {
        true -> _array.getULongAt(position).swapEndian()
        false -> _array.getULongAt(position)
    }

    override inline fun readFloat(position: Int): Float = when (reverse) {
        true -> _array.getFloatAt(position).swapEndian()
        false -> _array.getFloatAt(position)
    }

    override inline fun readDouble(position: Int): Double = when (reverse) {
        true -> _array.getDoubleAt(position).swapEndian()
        false -> _array.getDoubleAt(position)
    }

    override fun getArray(): ByteArray = _array
}