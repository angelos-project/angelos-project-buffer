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
 * Reference implementation of a MutableDataByteBuffer that works on all targets.
 * It may be slow but can be optimized. The Idea is to use this to benchmark against.
 *
 * @constructor
 *
 * @param array
 * @param size
 * @param limit
 * @param position
 * @param endianness
 */
@Suppress("OVERRIDE_BY_INLINE")
class ReferenceMutableDataBuffer internal constructor(
    array: ByteArray,
    size: Int,
    limit: Int,
    endianness: Endianness,
) : AbstractMutableDataBuffer(size, limit, endianness), MutableHeapDataBuffer {
    private val _array = array

    override inline fun writeByte(position: Int, value: Byte) {
        _array[position] = value
    }

    override inline fun writeUByte(position: Int, value: UByte) {
        _array[position] = value.toByte()
    }

    override inline fun writeChar(position: Int, value: Char) = when (reverse) {
        true -> _array.writeCharAt(position, value.swapEndian())
        false -> _array.writeCharAt(position, value)
    }

    override inline fun writeShort(position: Int, value: Short) = when (reverse) {
        true -> _array.writeShortAt(position, value.swapEndian())
        false -> _array.writeShortAt(position, value)
    }

    override inline fun writeUShort(position: Int, value: UShort) = when (reverse) {
        true -> _array.writeUShortAt(position, value.swapEndian())
        false -> _array.writeUShortAt(position, value)
    }

    override inline fun writeInt(position: Int, value: Int) = when (reverse) {
        true -> _array.writeIntAt(position, value.swapEndian())
        false -> _array.writeIntAt(position, value)
    }

    override inline fun writeUInt(position: Int, value: UInt) = when (reverse) {
        true -> _array.writeUIntAt(position, value.swapEndian())
        false -> _array.writeUIntAt(position, value)
    }

    override inline fun writeLong(position: Int, value: Long) = when (reverse) {
        true -> _array.writeLongAt(position, value.swapEndian())
        false -> _array.writeLongAt(position, value)
    }

    override inline fun writeULong(position: Int, value: ULong) = when (reverse) {
        true -> _array.writeULongAt(position, value.swapEndian())
        false -> _array.writeULongAt(position, value)
    }

    override inline fun writeFloat(position: Int, value: Float) = when (reverse) {
        true -> _array.writeFloatAt(position, value.swapEndian())
        false -> _array.writeFloatAt(position, value)
    }

    override inline fun writeDouble(position: Int, value: Double) = when (reverse) {
        true -> _array.writeDoubleAt(position, value.swapEndian())
        false -> _array.writeDoubleAt(position, value)
    }

    override inline fun readByte(position: Int): Byte = _array[position]

    override inline fun readUByte(position: Int): UByte = _array[position].toUByte()

    override inline fun readChar(position: Int): Char = when (reverse) {
        true -> _array.readCharAt(position).swapEndian()
        false -> _array.readCharAt(position)
    }

    override inline fun readShort(position: Int): Short = when (reverse) {
        true -> _array.readShortAt(position).swapEndian()
        false -> _array.readShortAt(position)
    }

    override inline fun readUShort(position: Int): UShort = when (reverse) {
        true -> _array.readUShortAt(position).swapEndian()
        false -> _array.readUShortAt(position)
    }

    override inline fun readInt(position: Int): Int = when (reverse) {
        true -> _array.readIntAt(position).swapEndian()
        false -> _array.readIntAt(position)
    }

    override inline fun readUInt(position: Int): UInt = when (reverse) {
        true -> _array.readUIntAt(position).swapEndian()
        false -> _array.readUIntAt(position)
    }

    override inline fun readLong(position: Int): Long = when (reverse) {
        true -> _array.readLongAt(position).swapEndian()
        false -> _array.readLongAt(position)
    }

    override inline fun readULong(position: Int): ULong = when (reverse) {
        true -> _array.readULongAt(position).swapEndian()
        false -> _array.readULongAt(position)
    }

    override inline fun readFloat(position: Int): Float = when (reverse) {
        true -> _array.readFloatAt(position).swapEndian()
        false -> _array.readFloatAt(position)
    }

    override inline fun readDouble(position: Int): Double = when (reverse) {
        true -> _array.readDoubleAt(position).swapEndian()
        false -> _array.readDoubleAt(position)
    }

    override fun getArray(): ByteArray = _array
}

/**
 * Create reference mutable buffer based on presumably random data.
 *
 * @param data ByteBuffer of data of presumably reference size
 * @return a newly created reference buffer
 */
fun refMutableDataBufferOf(data: ByteArray): MutableHeapDataBuffer {
    return ReferenceMutableDataBuffer(data, data.size, data.size, Buffer.nativeEndianness)
}