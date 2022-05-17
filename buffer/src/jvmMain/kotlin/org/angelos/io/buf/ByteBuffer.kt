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
actual class ByteBuffer internal actual constructor(
    array: ByteArray,
    size: Int,
    limit: Int,
    position: Int,
    endianness: Endianness
) : AbstractBuffer(size, limit, position, endianness), ImmutableHeapBuffer {
    private val _array = array

    override fun loadByte(index: Int): Byte = _array[index]

    override fun loadLong(index: Int): Long = Internals.unsafe.getLong(_array, Internals.byteArrayOffset + index)

    override fun readByte(): Byte = _array[_position]

    override fun readUByte(): UByte = _array[_position].toUByte()

    override fun readChar(): Char = when (reverse) {
        true -> Internals.unsafe.getChar(_array, Internals.byteArrayOffset + _position).swapEndian()
        false -> Internals.unsafe.getChar(_array, Internals.byteArrayOffset + _position)
    }

    override fun readShort(): Short = when (reverse) {
        true -> Internals.unsafe.getShort(_array, Internals.byteArrayOffset + _position).swapEndian()
        false -> Internals.unsafe.getShort(_array, Internals.byteArrayOffset + _position)
    }

    override fun readUShort(): UShort = when (reverse) {
        true -> Internals.unsafe.getShort(_array, Internals.byteArrayOffset + _position).swapEndian()
        false -> Internals.unsafe.getShort(_array, Internals.byteArrayOffset + _position)
    }.toUShort()

    override fun readInt(): Int = when (reverse) {
        true -> Internals.unsafe.getInt(_array, Internals.byteArrayOffset + _position).swapEndian()
        false -> Internals.unsafe.getInt(_array, Internals.byteArrayOffset + _position)
    }

    override fun readUInt(): UInt = when (reverse) {
        true -> Internals.unsafe.getInt(_array, Internals.byteArrayOffset + _position).swapEndian()
        false -> Internals.unsafe.getInt(_array, Internals.byteArrayOffset + _position)
    }.toUInt()

    override fun readLong(): Long = when (reverse) {
        true -> Internals.unsafe.getLong(_array, Internals.byteArrayOffset + _position).swapEndian()
        false -> Internals.unsafe.getLong(_array, Internals.byteArrayOffset + _position)
    }

    override fun readULong(): ULong = when (reverse) {
        true -> Internals.unsafe.getLong(_array, Internals.byteArrayOffset + _position).swapEndian()
        false -> Internals.unsafe.getLong(_array, Internals.byteArrayOffset + _position)
    }.toULong()

    override fun readFloat(): Float = when (reverse) {
        true -> Internals.unsafe.getFloat(_array, Internals.byteArrayOffset + _position).swapEndian()
        false -> Internals.unsafe.getFloat(_array, Internals.byteArrayOffset + _position)
    }

    override fun readDouble(): Double = when (reverse) {
        true -> Internals.unsafe.getDouble(_array, Internals.byteArrayOffset + _position).swapEndian()
        false -> Internals.unsafe.getDouble(_array, Internals.byteArrayOffset + _position)
    }

    override fun copyInto(destination: MutableBuffer, destinationOffset: Int, startIndex: Int, endIndex: Int) = when(destination) {
        is AbstractMutableBuffer -> copyInto(destination, destinationOffset, startIndex, endIndex)
        else -> error("Only handles AbstractMutableBuffer.")
    }

    override fun getArray(): ByteArray = _array
}