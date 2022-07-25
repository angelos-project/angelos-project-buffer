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
import org.angproj.io.buf.stream.MutableStreamBuffer

abstract class AbstractDataBuffer internal constructor(
    size: Int,
    limit: Int,
    endianness: Endianness,
): AbstractBuffer(size, limit, endianness), DataBuffer {
    override fun reset(limit: Int, zeroing: Boolean) {
        TODO("Not yet implemented")
    }

    fun remaining(position: Int): Int = AbstractDataBuffer.remaining(this, position)

    fun hasRemaining(position: Int, size: Int) = AbstractDataBuffer.hasRemaining(this, position, size)

    override fun copyInto(destination: MutableStreamBuffer, destinationOffset: Int, startIndex: Int, endIndex: Int) {
        TODO("Not yet implemented")
    }

    override fun getRetrieveByte(position: Int): Byte {
        hasRemaining(this, position, Buffer.BYTE_SIZE)
        return readByte()
    }

    override fun getRetrieveUByte(position: Int): UByte {
        hasRemaining(this, position, Buffer.UBYTE_SIZE)
        return readUByte()
    }

    override fun getRetrieveChar(position: Int): Char {
        hasRemaining(this, position, Buffer.CHAR_SIZE)
        return readChar()
    }

    override fun getRetrieveShort(position: Int): Short {
        hasRemaining(this, position, Buffer.SHORT_SIZE)
        return readShort()
    }

    override fun getRetrieveUShort(position: Int): UShort {
        hasRemaining(this, position, Buffer.USHORT_SIZE)
        return readUShort()
    }

    override fun getRetrieveInt(position: Int): Int {
        hasRemaining(this, position, Buffer.INT_SIZE)
        return readInt()
    }

    override fun getRetrieveUInt(position: Int): UInt {
        hasRemaining(this, position, Buffer.UINT_SIZE)
        return readUInt()
    }

    override fun getRetrieveLong(position: Int): Long {
        hasRemaining(this, position, Buffer.LONG_SIZE)
        return readLong()
    }

    override fun getRetrieveULong(position: Int): ULong {
        hasRemaining(this, position, Buffer.ULONG_SIZE)
        return readULong()
    }

    override fun getRetrieveFloat(position: Int): Float {
        hasRemaining(this, position, Buffer.FLOAT_SIZE)
        return readFloat()
    }

    override fun getRetrieveDouble(position: Int): Double {
        hasRemaining(this, position, Buffer.DOUBLE_SIZE)
        return readDouble()
    }

    override fun setStoreByte(position: Int, value: Byte) {
        hasRemaining(this, position, Buffer.BYTE_SIZE)
        writeByte(value)
    }

    override fun setStoreUByte(position: Int, value: UByte) {
        hasRemaining(this, position, Buffer.UBYTE_SIZE)
        writeUByte(value)
    }

    override fun setStoreChar(position: Int, value: Char) {
        hasRemaining(this, position, Buffer.CHAR_SIZE)
        writeChar(value)
    }

    override fun setStoreShort(position: Int, value: Short) {
        hasRemaining(this, position, Buffer.SHORT_SIZE)
        writeShort(value)
    }

    override fun setStoreUShort(position: Int, value: UShort) {
        hasRemaining(this, position, Buffer.USHORT_SIZE)
        writeUShort(value)
    }

    override fun setStoreInt(position: Int, value: Int) {
        hasRemaining(this, position, Buffer.INT_SIZE)
        writeInt(value)
    }

    override fun setStoreUInt(position: Int, value: UInt) {
        hasRemaining(this, position, Buffer.UINT_SIZE)
        writeUInt(value)
    }

    override fun setStoreLong(position: Int, value: Long) {
        hasRemaining(this, position, Buffer.LONG_SIZE)
        writeLong(value)
    }

    override fun setStoreULong(position: Int, value: ULong) {
        hasRemaining(this, position, Buffer.ULONG_SIZE)
        writeULong(value)
    }

    override fun setStoreFloat(position: Int, value: Float) {
        hasRemaining(this, position, Buffer.FLOAT_SIZE)
        writeFloat(value)
    }

    override fun setStoreDouble(position: Int, value: Double) {
        hasRemaining(this, position, Buffer.DOUBLE_SIZE)
        writeDouble(value)
    }

    internal abstract fun readByte(): Byte

    internal abstract fun readUByte(): UByte

    internal abstract fun readChar(): Char

    internal abstract fun readShort(): Short

    internal abstract fun readUShort(): UShort

    internal abstract fun readInt(): Int

    internal abstract fun readUInt(): UInt

    internal abstract fun readLong(): Long

    internal abstract fun readULong(): ULong

    internal abstract fun readFloat(): Float

    internal abstract fun readDouble(): Double

    internal abstract fun writeByte(value: Byte)

    internal abstract fun writeUByte(value: UByte)

    internal abstract fun writeChar(value: Char)

    internal abstract fun writeShort(value: Short)

    internal abstract fun writeUShort(value: UShort)

    internal abstract fun writeInt(value: Int)

    internal abstract fun writeUInt(value: UInt)

    internal abstract fun writeLong(value: Long)

    internal abstract fun writeULong(value: ULong)

    internal abstract fun writeFloat(value: Float)

    internal abstract fun writeDouble(value: Double)

    companion object {
        internal inline fun remaining(buf: DataBuffer, position: Int): Int {
            return buf.limit - position + 1
        }

        internal inline fun hasRemaining(buf: DataBuffer, position: Int, size: Int) {
            if (remaining(buf, position) <= size)
                throw BufferOverflowWarning()
        }
    }
}