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

import org.angproj.io.buf.*

/**
 * The Kotlin/JS implementation of the NativeDataByteBuffer class uses the access functions implemented by this
 * library to access the underlying ByteArray without using any pointer arithmetic. This is a simulation of native
 * memory access due to the runtime environment restrictions.
 *
 * @constructor
 *
 * @param size
 * @param limit
 * @param endianness
 */
actual class NativeDataByteBuffer internal actual constructor(
    size: Int,
    limit: Int,
    endianness: Endianness,
) : AbstractDataBuffer(size, limit, endianness), ImmutableNativeDataBuffer {
    private val _array = ByteArray(size)

    override fun readByte(position: Int): Byte = _array[position]

    override fun readUByte(position: Int): UByte = _array[position].toUByte()

    override fun readChar(position: Int): Char = when (reverse) {
        true -> _array.readCharAt(position).swapEndian()
        false -> _array.readCharAt(position)
    }

    override fun readShort(position: Int): Short = when (reverse) {
        true -> _array.readShortAt(position).swapEndian()
        false -> _array.readShortAt(position)
    }

    override fun readUShort(position: Int): UShort = when (reverse) {
        true -> _array.readUShortAt(position).swapEndian()
        false -> _array.readUShortAt(position)
    }

    override fun readInt(position: Int): Int = when (reverse) {
        true -> _array.readIntAt(position).swapEndian()
        false -> _array.readIntAt(position)
    }

    override fun readUInt(position: Int): UInt = when (reverse) {
        true -> _array.readUIntAt(position).swapEndian()
        false -> _array.readUIntAt(position)
    }

    override fun readLong(position: Int): Long = when (reverse) {
        true -> _array.readLongAt(position).swapEndian()
        false -> _array.readLongAt(position)
    }

    override fun readULong(position: Int): ULong = when (reverse) {
        true -> _array.readULongAt(position).swapEndian()
        false -> _array.readULongAt(position)
    }

    override fun readFloat(position: Int): Float = when (reverse) {
        true -> _array.readFloatAt(position).swapEndian()
        false -> _array.readFloatAt(position)
    }

    override fun readDouble(position: Int): Double = when (reverse) {
        true -> _array.readDoubleAt(position).swapEndian()
        false -> _array.readDoubleAt(position)
    }

    override fun getPointer(): TypePointer<Byte> {
        throw UnsupportedOperationException()
    }

    override fun dispose() {}

    override fun getArray(): ByteArray = _array
}