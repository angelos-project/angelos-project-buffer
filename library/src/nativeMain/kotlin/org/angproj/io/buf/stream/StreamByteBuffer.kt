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
package org.angproj.io.buf.stream

import org.angproj.io.buf.Endianness
import org.angproj.io.buf.swapEndian

/**
 * The Kotlin/Native implementation of the StreamByteBuffer class uses the access functions implemented by this
 * library to access the underlying ByteArray without using any pointer arithmetic.
 *
 * @constructor
 *
 * @param array
 * @param size
 * @param limit
 * @param position
 * @param endianness
 */
@OptIn(ExperimentalUnsignedTypes::class)
@Suppress("OVERRIDE_BY_INLINE")
actual class StreamByteBuffer internal actual constructor(
    array: ByteArray,
    size: Int,
    limit: Int,
    position: Int,
    endianness: Endianness,
) : AbstractImmutableStreamBuffer(size, limit, position, endianness), ImmutableHeapStreamBuffer {
    private val _array = array

    override inline fun readByte(): Byte = _array[_position]

    override inline fun readUByte(): UByte = _array.getUByteAt(_position)

    override inline fun readChar(): Char = when (reverse) {
        true -> _array.getCharAt(_position).swapEndian()
        false -> _array.getCharAt(_position)
    }

    override inline fun readShort(): Short = when (reverse) {
        true -> _array.getShortAt(_position).swapEndian()
        false -> _array.getShortAt(_position)
    }

    override inline fun readUShort(): UShort = when (reverse) {
        true -> _array.getUShortAt(_position).swapEndian()
        false -> _array.getUShortAt(_position)
    }

    override inline fun readInt(): Int = when (reverse) {
        true -> _array.getIntAt(_position).swapEndian()
        false -> _array.getIntAt(_position)
    }

    override inline fun readUInt(): UInt = when (reverse) {
        true -> _array.getUIntAt(_position).swapEndian()
        false -> _array.getUIntAt(_position)
    }

    override inline fun readLong(): Long = when (reverse) {
        true -> _array.getLongAt(_position).swapEndian()
        false -> _array.getLongAt(_position)
    }

    override inline fun readULong(): ULong = when (reverse) {
        true -> _array.getULongAt(_position).swapEndian()
        false -> _array.getULongAt(_position)
    }

    override inline fun readFloat(): Float = when (reverse) {
        true -> _array.getFloatAt(_position).swapEndian()
        false -> _array.getFloatAt(_position)
    }

    override inline fun readDouble(): Double = when (reverse) {
        true -> _array.getDoubleAt(_position).swapEndian()
        false -> _array.getDoubleAt(_position)
    }

    override fun getArray(): ByteArray = _array
}