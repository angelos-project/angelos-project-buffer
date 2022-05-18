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
 * Mutable native byte buffer implemented outside save memory environment as mutable.
 *
 * @constructor
 *
 * @param size
 * @param limit
 * @param position
 * @param endianness
 */
actual class MutableNativeByteBuffer internal actual constructor(
    size: Int,
    limit: Int,
    position: Int,
    endianness: Endianness,
) : AbstractMutableBuffer(size, limit, position, endianness), MutableNativeBuffer {
    private val _array = ByteArray(size)

    override fun saveByte(index: Int, value: Byte) { _array[index] = value }

    override fun saveLong(index: Int, value: Long) { _array.writeLongAt(index, value) }

    override fun writeByte(value: Byte) { _array[_position] = value }

    override fun writeUByte(value: UByte) { _array[_position] = value.toByte() }

    override fun writeChar(value: Char) = when (reverse) {
        true -> _array.writeCharAt(_position, value.swapEndian())
        false -> _array.writeCharAt(_position, value)
    }

    override fun writeShort(value: Short) = when (reverse) {
        true -> _array.writeShortAt(_position, value.swapEndian())
        false -> _array.writeShortAt(_position, value)
    }

    override fun writeUShort(value: UShort) = when (reverse) {
        true -> _array.writeUShortAt(_position, value.swapEndian())
        false -> _array.writeUShortAt(_position, value)
    }

    override fun writeInt(value: Int) = when (reverse) {
        true -> _array.writeIntAt(_position, value.swapEndian())
        false -> _array.writeIntAt(_position, value)
    }

    override fun writeUInt(value: UInt) = when (reverse) {
        true -> _array.writeUIntAt(_position, value.swapEndian())
        false -> _array.writeUIntAt(_position, value)
    }

    override fun writeLong(value: Long) = when (reverse) {
        true -> _array.writeLongAt(_position, value.swapEndian())
        false -> _array.writeLongAt(_position, value)
    }

    override fun writeULong(value: ULong) = when (reverse) {
        true -> _array.writeULongAt(_position, value.swapEndian())
        false -> _array.writeULongAt(_position, value)
    }

    override fun writeFloat(value: Float) = when (reverse) {
        true -> _array.writeFloatAt(_position, value.swapEndian())
        false -> _array.writeFloatAt(_position, value)
    }

    override fun writeDouble(value: Double) = when (reverse) {
        true -> _array.writeDoubleAt(_position, value.swapEndian())
        false -> _array.writeDoubleAt(_position, value)
    }

    override fun loadByte(index: Int): Byte = _array[index]

    override fun loadLong(index: Int): Long = _array.readLongAt(index)

    override fun readByte(): Byte = _array[_position]

    override fun readUByte(): UByte = _array[_position].toUByte()

    override fun readChar(): Char = when (reverse) {
        true -> _array.readCharAt(_position).swapEndian()
        false -> _array.readCharAt(_position)
    }

    override fun readShort(): Short = when (reverse) {
        true -> _array.readShortAt(_position).swapEndian()
        false -> _array.readShortAt(_position)
    }

    override fun readUShort(): UShort = when (reverse) {
        true -> _array.readUShortAt(_position).swapEndian()
        false -> _array.readUShortAt(_position)
    }

    override fun readInt(): Int = when (reverse) {
        true -> _array.readIntAt(_position).swapEndian()
        false -> _array.readIntAt(_position)
    }

    override fun readUInt(): UInt = when (reverse) {
        true -> _array.readUIntAt(_position).swapEndian()
        false -> _array.readUIntAt(_position)
    }

    override fun readLong(): Long = when (reverse) {
        true -> _array.readLongAt(_position).swapEndian()
        false -> _array.readLongAt(_position)
    }

    override fun readULong(): ULong = when (reverse) {
        true -> _array.readULongAt(_position).swapEndian()
        false -> _array.readULongAt(_position)
    }

    override fun readFloat(): Float = when (reverse) {
        true -> _array.readFloatAt(_position).swapEndian()
        false -> _array.readFloatAt(_position)
    }

    override fun readDouble(): Double = when (reverse) {
        true -> _array.readDoubleAt(_position).swapEndian()
        false -> _array.readDoubleAt(_position)
    }

    override fun copyInto(destination: MutableBuffer, destinationOffset: Int, startIndex: Int, endIndex: Int) = when(destination) {
        is AbstractMutableBuffer -> copyInto(destination, destinationOffset, startIndex, endIndex)
        else -> error("Only handles AbstractMutableBuffer.")
    }

    override fun getPointer(): TypePointer<Byte> {
        throw UnsupportedOperationException()
    }

    override fun usePinned(native: (ptr: TypePointer<Byte>) -> Unit) {
        native(getPointer())
    }

    override fun dispose() { }
}