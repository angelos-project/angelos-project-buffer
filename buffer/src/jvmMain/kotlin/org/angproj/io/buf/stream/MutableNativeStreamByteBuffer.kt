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
import org.angproj.io.buf.Internals

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
actual class MutableNativeStreamByteBuffer internal actual constructor(
    size: Int,
    limit: Int,
    position: Int,
    endianness: Endianness,
) : AbstractMutableStreamBuffer(size, limit, position, endianness), MutableNativeStreamBuffer {
    private val _pointer = Internals.unsafe.allocateMemory(size.toLong())

    override fun saveByte(index: Int, value: Byte) = Internals.unsafe.putByte(_pointer + index, value)

    override fun saveLong(index: Int, value: Long) = Internals.unsafe.putLong(_pointer + index, value)

    override fun writeByte(value: Byte) = Internals.unsafe.putByte(_pointer + _position, value)

    override fun writeUByte(value: UByte) = Internals.unsafe.putByte(_pointer + _position, value.toByte())

    override fun writeChar(value: Char) = when (reverse) {
        true -> Internals.unsafe.putChar(_pointer + _position, value.swapEndian())
        false -> Internals.unsafe.putChar(_pointer + _position, value)
    }

    override fun writeShort(value: Short) = when (reverse) {
        true -> Internals.unsafe.putShort(_pointer + _position, value.swapEndian())
        false -> Internals.unsafe.putShort(_pointer + _position, value)
    }

    override fun writeUShort(value: UShort) = when (reverse) {
        true -> Internals.unsafe.putShort(_pointer + _position, value.swapEndian().toShort())
        false -> Internals.unsafe.putShort(_pointer + _position, value.toShort())
    }

    override fun writeInt(value: Int) = when (reverse) {
        true -> Internals.unsafe.putInt(_pointer + _position, value.swapEndian())
        false -> Internals.unsafe.putInt(_pointer + _position, value)
    }

    override fun writeUInt(value: UInt) = when (reverse) {
        true -> Internals.unsafe.putInt(_pointer + _position, value.swapEndian().toInt())
        false -> Internals.unsafe.putInt(_pointer + _position, value.toInt())
    }

    override fun writeLong(value: Long) = when (reverse) {
        true -> Internals.unsafe.putLong(_pointer + _position, value.swapEndian())
        false -> Internals.unsafe.putLong(_pointer + _position, value)
    }

    override fun writeULong(value: ULong) = when (reverse) {
        true -> Internals.unsafe.putLong(_pointer + _position, value.swapEndian().toLong())
        false -> Internals.unsafe.putLong(_pointer + _position, value.toLong())
    }

    override fun writeFloat(value: Float) = when (reverse) {
        true -> Internals.unsafe.putFloat(_pointer + _position, value.swapEndian())
        false -> Internals.unsafe.putFloat(_pointer + _position, value)
    }

    override fun writeDouble(value: Double) = when (reverse) {
        true -> Internals.unsafe.putDouble(_pointer + _position, value.swapEndian())
        false -> Internals.unsafe.putDouble(_pointer + _position, value)
    }

    override fun loadByte(index: Int): Byte = Internals.unsafe.getByte(_pointer + index)

    override fun loadLong(index: Int): Long = Internals.unsafe.getLong(_pointer + index)

    override fun readByte(): Byte = Internals.unsafe.getByte(_pointer + _position)

    override fun readUByte(): UByte = Internals.unsafe.getByte(_pointer + _position).toUByte()

    override fun readChar(): Char = when (reverse) {
        true -> Internals.unsafe.getChar(_pointer + _position).swapEndian()
        false -> Internals.unsafe.getChar(_pointer + _position)
    }

    override fun readShort(): Short = when (reverse) {
        true -> Internals.unsafe.getShort(_pointer + _position).swapEndian()
        false -> Internals.unsafe.getShort(_pointer + _position)
    }

    override fun readUShort(): UShort = when (reverse) {
        true -> Internals.unsafe.getShort(_pointer + _position).swapEndian()
        false -> Internals.unsafe.getShort(_pointer + _position)
    }.toUShort()

    override fun readInt(): Int = when (reverse) {
        true -> Internals.unsafe.getInt(_pointer + _position).swapEndian()
        false -> Internals.unsafe.getInt(_pointer + _position)
    }

    override fun readUInt(): UInt = when (reverse) {
        true -> Internals.unsafe.getInt(_pointer + _position).swapEndian()
        false -> Internals.unsafe.getInt(_pointer + _position)
    }.toUInt()

    override fun readLong(): Long = when (reverse) {
        true -> Internals.unsafe.getLong(_pointer + _position).swapEndian()
        false -> Internals.unsafe.getLong(_pointer + _position)
    }

    override fun readULong(): ULong = when (reverse) {
        true -> Internals.unsafe.getLong(_pointer + _position).swapEndian()
        false -> Internals.unsafe.getLong(_pointer + _position)
    }.toULong()

    override fun readFloat(): Float = when (reverse) {
        true -> Internals.unsafe.getFloat(_pointer + _position).swapEndian()
        false -> Internals.unsafe.getFloat(_pointer + _position)
    }

    override fun readDouble(): Double = when (reverse) {
        true -> Internals.unsafe.getDouble(_pointer + _position).swapEndian()
        false -> Internals.unsafe.getDouble(_pointer + _position)
    }

    override fun copyInto(destination: MutableStreamBuffer, destinationOffset: Int, startIndex: Int, endIndex: Int) =
        when (destination) {
            is AbstractMutableStreamBuffer -> copyInto(destination, destinationOffset, startIndex, endIndex)
            else -> error("Only handles AbstractMutableBuffer.")
        }

    override fun getPointer(): TypePointer<Byte> = _pointer
    override fun usePinned(native: (ptr: TypePointer<Byte>) -> Unit) {
        native(getPointer())
    }

    override fun dispose() {
        Internals.unsafe.freeMemory(_pointer)
    }

    protected fun finalize() {
        dispose()
    }
}