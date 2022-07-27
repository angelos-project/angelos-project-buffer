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
        true -> Internals.unsafe.putChar(_array, Internals.byteArrayOffset + position, value.swapEndian())
        false -> Internals.unsafe.putChar(_array, Internals.byteArrayOffset + position, value)
    }

    override fun writeShort(position: Int, value: Short) = when (reverse) {
        true -> Internals.unsafe.putShort(_array, Internals.byteArrayOffset + position, value.swapEndian())
        false -> Internals.unsafe.putShort(_array, Internals.byteArrayOffset + position, value)
    }

    override fun writeUShort(position: Int, value: UShort) = when (reverse) {
        true -> Internals.unsafe.putShort(_array, Internals.byteArrayOffset + position, value.toShort().swapEndian())
        false -> Internals.unsafe.putShort(_array, Internals.byteArrayOffset + position, value.toShort())
    }

    override fun writeInt(position: Int, value: Int) = when (reverse) {
        true -> Internals.unsafe.putInt(_array, Internals.byteArrayOffset + position, value.swapEndian())
        false -> Internals.unsafe.putInt(_array, Internals.byteArrayOffset + position, value)
    }

    override fun writeUInt(position: Int, value: UInt) = when (reverse) {
        true -> Internals.unsafe.putInt(_array, Internals.byteArrayOffset + position, value.toInt().swapEndian())
        false -> Internals.unsafe.putInt(_array, Internals.byteArrayOffset + position, value.toInt())
    }

    override fun writeLong(position: Int, value: Long) = when (reverse) {
        true -> Internals.unsafe.putLong(_array, Internals.byteArrayOffset + position, value.swapEndian())
        false -> Internals.unsafe.putLong(_array, Internals.byteArrayOffset + position, value)
    }

    override fun writeULong(position: Int, value: ULong) = when (reverse) {
        true -> Internals.unsafe.putLong(_array, Internals.byteArrayOffset + position, value.toLong().swapEndian())
        false -> Internals.unsafe.putLong(_array, Internals.byteArrayOffset + position, value.toLong())
    }

    override fun writeFloat(position: Int, value: Float) = when (reverse) {
        true -> Internals.unsafe.putFloat(_array, Internals.byteArrayOffset + position, value.swapEndian())
        false -> Internals.unsafe.putFloat(_array, Internals.byteArrayOffset + position, value)
    }

    override fun writeDouble(position: Int, value: Double) = when (reverse) {
        true -> Internals.unsafe.putDouble(_array, Internals.byteArrayOffset + position, value.swapEndian())
        false -> Internals.unsafe.putDouble(_array, Internals.byteArrayOffset + position, value)
    }

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