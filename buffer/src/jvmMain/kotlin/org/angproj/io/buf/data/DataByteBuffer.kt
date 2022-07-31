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
import org.angproj.io.buf.Internals
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
actual class DataByteBuffer internal actual constructor(
    array: ByteArray,
    size: Int,
    limit: Int,
    endianness: Endianness,
) : AbstractDataBuffer(size, limit, endianness), ImmutableHeapDataBuffer {
    private val _array = array

    override fun readByte(position: Int): Byte = _array[position]

    override fun readUByte(position: Int): UByte = _array[position].toUByte()

    override fun readChar(position: Int): Char = when (reverse) {
        true -> Internals.unsafe.getChar(_array, Internals.byteArrayOffset + position).swapEndian()
        false -> Internals.unsafe.getChar(_array, Internals.byteArrayOffset + position)
    }

    override fun readShort(position: Int): Short = when (reverse) {
        true -> Internals.unsafe.getShort(_array, Internals.byteArrayOffset + position).swapEndian()
        false -> Internals.unsafe.getShort(_array, Internals.byteArrayOffset + position)
    }

    override fun readUShort(position: Int): UShort = when (reverse) {
        true -> Internals.unsafe.getShort(_array, Internals.byteArrayOffset + position).swapEndian()
        false -> Internals.unsafe.getShort(_array, Internals.byteArrayOffset + position)
    }.toUShort()

    override fun readInt(position: Int): Int = when (reverse) {
        true -> Internals.unsafe.getInt(_array, Internals.byteArrayOffset + position).swapEndian()
        false -> Internals.unsafe.getInt(_array, Internals.byteArrayOffset + position)
    }

    override fun readUInt(position: Int): UInt = when (reverse) {
        true -> Internals.unsafe.getInt(_array, Internals.byteArrayOffset + position).swapEndian()
        false -> Internals.unsafe.getInt(_array, Internals.byteArrayOffset + position)
    }.toUInt()

    override fun readLong(position: Int): Long = when (reverse) {
        true -> Internals.unsafe.getLong(_array, Internals.byteArrayOffset + position).swapEndian()
        false -> Internals.unsafe.getLong(_array, Internals.byteArrayOffset + position)
    }

    override fun readULong(position: Int): ULong = when (reverse) {
        true -> Internals.unsafe.getLong(_array, Internals.byteArrayOffset + position).swapEndian()
        false -> Internals.unsafe.getLong(_array, Internals.byteArrayOffset + position)
    }.toULong()

    override fun readFloat(position: Int): Float = when (reverse) {
        true -> Internals.unsafe.getFloat(_array, Internals.byteArrayOffset + position).swapEndian()
        false -> Internals.unsafe.getFloat(_array, Internals.byteArrayOffset + position)
    }

    override fun readDouble(position: Int): Double = when (reverse) {
        true -> Internals.unsafe.getDouble(_array, Internals.byteArrayOffset + position).swapEndian()
        false -> Internals.unsafe.getDouble(_array, Internals.byteArrayOffset + position)
    }

    override fun getArray(): ByteArray = _array
}