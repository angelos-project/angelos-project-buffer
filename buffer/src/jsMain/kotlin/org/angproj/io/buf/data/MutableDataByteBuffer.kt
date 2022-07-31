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
 * Mutable data byte buffer
 *
 * @constructor
 *
 * @param array
 * @param size
 * @param limit
 * @param endianness
 */
actual class MutableDataByteBuffer internal actual constructor(
    array: ByteArray,
    size: Int,
    limit: Int,
    endianness: Endianness,
) : AbstractMutableDataBuffer(size, limit, endianness), MutableHeapDataBuffer {
    private val _array = array

    override fun writeByte(position: Int, value: Byte) {
        _array[position] = value
    }

    override fun writeUByte(position: Int, value: UByte) {
        _array[position] = value.toByte()
    }

    override fun writeChar(position: Int, value: Char) = when (reverse) {
        true -> _array.writeCharAt(position, value.swapEndian())
        false -> _array.writeCharAt(position, value)
    }

    override fun writeShort(position: Int, value: Short) = when (reverse) {
        true -> _array.writeShortAt(position, value.swapEndian())
        false -> _array.writeShortAt(position, value)
    }

    override fun writeUShort(position: Int, value: UShort) = when (reverse) {
        true -> _array.writeUShortAt(position, value.swapEndian())
        false -> _array.writeUShortAt(position, value)
    }

    override fun writeInt(position: Int, value: Int) = when (reverse) {
        true -> _array.writeIntAt(position, value.swapEndian())
        false -> _array.writeIntAt(position, value)
    }

    override fun writeUInt(position: Int, value: UInt) = when (reverse) {
        true -> _array.writeUIntAt(position, value.swapEndian())
        false -> _array.writeUIntAt(position, value)
    }

    override fun writeLong(position: Int, value: Long) = when (reverse) {
        true -> _array.writeLongAt(position, value.swapEndian())
        false -> _array.writeLongAt(position, value)
    }

    override fun writeULong(position: Int, value: ULong) = when (reverse) {
        true -> _array.writeULongAt(position, value.swapEndian())
        false -> _array.writeULongAt(position, value)
    }

    override fun writeFloat(position: Int, value: Float) = when (reverse) {
        true -> _array.writeFloatAt(position, value.swapEndian())
        false -> _array.writeFloatAt(position, value)
    }

    override fun writeDouble(position: Int, value: Double) = when (reverse) {
        true -> _array.writeDoubleAt(position, value.swapEndian())
        false -> _array.writeDoubleAt(position, value)
    }

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

    override fun getArray(): ByteArray = _array
}