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

import cbuffer.speedmemcpy
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.refTo
import kotlinx.cinterop.toCPointer
import kotlinx.cinterop.usePinned

/**
 * Mutable byte buffer implemented on the heap, as mutable.
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
actual class MutableByteBuffer internal actual constructor(
    array: ByteArray,
    size: Int,
    limit: Int,
    position: Int,
    endianness: Endianness,
) : AbstractMutableBuffer(size, limit, position, endianness), MutableHeapBuffer {
    private val _array = array

    override fun saveByte(index: Int, value: Byte) {
        _array[index] = value
    }

    override fun saveLong(index: Int, value: Long) = _array.setLongAt(index, value)

    override inline fun writeByte(value: Byte) {
        _array[_position] = value
    }

    override inline fun writeUByte(value: UByte) {
        _array[_position] = value.toByte()
    }

    override inline fun writeChar(value: Char) = when (reverse) {
        true -> _array.setCharAt(_position, value.swapEndian())
        false -> _array.setCharAt(_position, value)
    }

    override inline fun writeShort(value: Short) = when (reverse) {
        true -> _array.setShortAt(_position, value.swapEndian())
        false -> _array.setShortAt(_position, value)
    }

    override inline fun writeUShort(value: UShort) = when (reverse) {
        true -> _array.setUShortAt(_position, value.swapEndian())
        false -> _array.setUShortAt(_position, value)
    }

    override inline fun writeInt(value: Int) = when (reverse) {
        true -> _array.setIntAt(_position, value.swapEndian())
        false -> _array.setIntAt(_position, value)
    }

    override inline fun writeUInt(value: UInt) = when (reverse) {
        true -> _array.setUIntAt(_position, value.swapEndian())
        false -> _array.setUIntAt(_position, value)
    }

    override inline fun writeLong(value: Long) = when (reverse) {
        true -> _array.setLongAt(_position, value.swapEndian())
        false -> _array.setLongAt(_position, value)
    }

    override inline fun writeULong(value: ULong) = when (reverse) {
        true -> _array.setULongAt(_position, value.swapEndian())
        false -> _array.setULongAt(_position, value)
    }

    override inline fun writeFloat(value: Float) = when (reverse) {
        true -> _array.setFloatAt(_position, value.swapEndian())
        false -> _array.setFloatAt(_position, value)
    }

    override inline fun writeDouble(value: Double) = when (reverse) {
        true -> _array.setDoubleAt(_position, value.swapEndian())
        false -> _array.setDoubleAt(_position, value)
    }

    override fun loadByte(index: Int): Byte = _array[index]

    override fun loadLong(index: Int): Long = _array.getLongAt(index)

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

    override fun copyInto(destination: MutableBuffer, destinationOffset: Int, startIndex: Int, endIndex: Int) =
        when (destination) {
            is AbstractMutableBuffer -> copyInto(destination, destinationOffset, startIndex, endIndex)
            else -> error("Only handles AbstractMutableBuffer.")
        }

    override fun copyInto(destination: AbstractMutableBuffer, destinationOffset: Int, startIndex: Int, endIndex: Int) {
        Buffer.copyIntoContract(destination, destinationOffset, this, startIndex, endIndex)

        _array.usePinned {
            val src = _array.refTo(startIndex)
            when (destination) {
                is HeapBuffer -> speedmemcpy(
                    destination.getArray().refTo(destinationOffset),
                    src,
                    (endIndex - startIndex).toUInt()
                )
                is NativeBuffer -> speedmemcpy(
                    (destination.getPointer() + destinationOffset).toCPointer<ByteVar>(),
                    src,
                    (endIndex - startIndex).toUInt()
                )
            }
        }
    }

    override fun getArray(): ByteArray = _array
}