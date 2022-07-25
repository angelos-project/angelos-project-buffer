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

import org.angproj.io.buf.*

/**
 * Native byte buffer implemented outside save memory environment as immutable.
 *
 * @constructor
 *
 * @param size
 * @param limit
 * @param position
 * @param endianness
 */
actual class NativeStreamByteBuffer internal actual constructor(
    size: Int,
    limit: Int,
    position: Int,
    endianness: Endianness,
) : AbstractStreamBuffer(size, limit, position, endianness), ImmutableNativeStreamBuffer {
    private val _array = ByteArray(size)

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

    override fun copyInto(destination: MutableStreamBuffer, destinationOffset: Int, startIndex: Int, endIndex: Int) =
        when (destination) {
            is AbstractMutableStreamBuffer -> copyInto(destination, destinationOffset, startIndex, endIndex)
            else -> error("Only handles AbstractMutableBuffer.")
        }

    override fun getPointer(): TypePointer<Byte> {
        throw UnsupportedOperationException()
    }

    override fun usePinned(native: (ptr: TypePointer<Byte>) -> Unit) {
        native(getPointer())
    }

    override fun dispose() {}
}