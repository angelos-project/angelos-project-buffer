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
import org.angproj.io.buf.TypePointer
import org.angproj.io.buf.swapEndian

actual class MutableNativeDataByteBuffer actual constructor(
    size: Int,
    limit: Int,
    endianness: Endianness,
) : AbstractMutableDataBuffer(size, limit, endianness), MutableNativeDataBuffer {
    private val _pointer = Internals.unsafe.allocateMemory(size.toLong())

    override fun writeByte(position: Int, value: Byte) = Internals.unsafe.putByte(_pointer + position, value)

    override fun writeUByte(position: Int, value: UByte) = Internals.unsafe.putByte(_pointer + position, value.toByte())

    override fun writeChar(position: Int, value: Char) = when (reverse) {
        true -> Internals.unsafe.putChar(_pointer + position, value.swapEndian())
        false -> Internals.unsafe.putChar(_pointer + position, value)
    }

    override fun writeShort(position: Int, value: Short) = when (reverse) {
        true -> Internals.unsafe.putShort(_pointer + position, value.swapEndian())
        false -> Internals.unsafe.putShort(_pointer + position, value)
    }

    override fun writeUShort(position: Int, value: UShort) = when (reverse) {
        true -> Internals.unsafe.putShort(_pointer + position, value.swapEndian().toShort())
        false -> Internals.unsafe.putShort(_pointer + position, value.toShort())
    }

    override fun writeInt(position: Int, value: Int) = when (reverse) {
        true -> Internals.unsafe.putInt(_pointer + position, value.swapEndian())
        false -> Internals.unsafe.putInt(_pointer + position, value)
    }

    override fun writeUInt(position: Int, value: UInt) = when (reverse) {
        true -> Internals.unsafe.putInt(_pointer + position, value.swapEndian().toInt())
        false -> Internals.unsafe.putInt(_pointer + position, value.toInt())
    }

    override fun writeLong(position: Int, value: Long) = when (reverse) {
        true -> Internals.unsafe.putLong(_pointer + position, value.swapEndian())
        false -> Internals.unsafe.putLong(_pointer + position, value)
    }

    override fun writeULong(position: Int, value: ULong) = when (reverse) {
        true -> Internals.unsafe.putLong(_pointer + position, value.swapEndian().toLong())
        false -> Internals.unsafe.putLong(_pointer + position, value.toLong())
    }

    override fun writeFloat(position: Int, value: Float) = when (reverse) {
        true -> Internals.unsafe.putFloat(_pointer + position, value.swapEndian())
        false -> Internals.unsafe.putFloat(_pointer + position, value)
    }

    override fun writeDouble(position: Int, value: Double) = when (reverse) {
        true -> Internals.unsafe.putDouble(_pointer + position, value.swapEndian())
        false -> Internals.unsafe.putDouble(_pointer + position, value)
    }

    override fun readByte(position: Int): Byte = Internals.unsafe.getByte(_pointer + position)

    override fun readUByte(position: Int): UByte = Internals.unsafe.getByte(_pointer + position).toUByte()

    override fun readChar(position: Int): Char = when (reverse) {
        true -> Internals.unsafe.getChar(_pointer + position).swapEndian()
        false -> Internals.unsafe.getChar(_pointer + position)
    }

    override fun readShort(position: Int): Short = when (reverse) {
        true -> Internals.unsafe.getShort(_pointer + position).swapEndian()
        false -> Internals.unsafe.getShort(_pointer + position)
    }

    override fun readUShort(position: Int): UShort = when (reverse) {
        true -> Internals.unsafe.getShort(_pointer + position).swapEndian()
        false -> Internals.unsafe.getShort(_pointer + position)
    }.toUShort()

    override fun readInt(position: Int): Int = when (reverse) {
        true -> Internals.unsafe.getInt(_pointer + position).swapEndian()
        false -> Internals.unsafe.getInt(_pointer + position)
    }

    override fun readUInt(position: Int): UInt = when (reverse) {
        true -> Internals.unsafe.getInt(_pointer + position).swapEndian()
        false -> Internals.unsafe.getInt(_pointer + position)
    }.toUInt()

    override fun readLong(position: Int): Long = when (reverse) {
        true -> Internals.unsafe.getLong(_pointer + position).swapEndian()
        false -> Internals.unsafe.getLong(_pointer + position)
    }

    override fun readULong(position: Int): ULong = when (reverse) {
        true -> Internals.unsafe.getLong(_pointer + position).swapEndian()
        false -> Internals.unsafe.getLong(_pointer + position)
    }.toULong()

    override fun readFloat(position: Int): Float = when (reverse) {
        true -> Internals.unsafe.getFloat(_pointer + position).swapEndian()
        false -> Internals.unsafe.getFloat(_pointer + position)
    }

    override fun readDouble(position: Int): Double = when (reverse) {
        true -> Internals.unsafe.getDouble(_pointer + position).swapEndian()
        false -> Internals.unsafe.getDouble(_pointer + position)
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