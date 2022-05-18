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
 * Abstract mutable buffer that implements mutability on top of AbstractBuffer.
 *
 * @constructor
 *
 * @param size max size of the buffer
 * @param limit initial limit if partial data already exists
 * @param position initial position in an already existing data stream
 * @param endianness endian of the buffered data
 */
abstract class AbstractMutableBuffer internal constructor(
    size: Int,
    limit: Int,
    position: Int,
    endianness: Endianness,
) : AbstractBuffer(size, limit, position, endianness), MutableBuffer {

    override fun setNextByte(value: Byte) {
        hasRemaining(this, Buffer.BYTE_SIZE)
        writeByte(value)
        forwardPosition(this, Buffer.BYTE_SIZE)
    }

    override fun setNextUByte(value: UByte) {
        hasRemaining(this, Buffer.UBYTE_SIZE)
        writeUByte(value)
        forwardPosition(this, Buffer.UBYTE_SIZE)
    }

    override fun setNextChar(value: Char) {
        hasRemaining(this, Buffer.CHAR_SIZE)
        writeChar(value)
        forwardPosition(this, Buffer.CHAR_SIZE)
    }

    override fun setNextShort(value: Short) {
        hasRemaining(this, Buffer.SHORT_SIZE)
        writeShort(value)
        forwardPosition(this, Buffer.SHORT_SIZE)
    }

    override fun setNextUShort(value: UShort) {
        hasRemaining(this, Buffer.USHORT_SIZE)
        writeUShort(value)
        forwardPosition(this, Buffer.USHORT_SIZE)
    }

    override fun setNextInt(value: Int) {
        hasRemaining(this, Buffer.INT_SIZE)
        writeInt(value)
        forwardPosition(this, Buffer.INT_SIZE)
    }

    override fun setNextUInt(value: UInt) {
        hasRemaining(this, Buffer.UINT_SIZE)
        writeUInt(value)
        forwardPosition(this, Buffer.UINT_SIZE)
    }

    override fun setNextLong(value: Long) {
        hasRemaining(this, Buffer.LONG_SIZE)
        writeLong(value)
        forwardPosition(this, Buffer.LONG_SIZE)
    }

    override fun setNextULong(value: ULong) {
        hasRemaining(this, Buffer.ULONG_SIZE)
        writeULong(value)
        forwardPosition(this, Buffer.ULONG_SIZE)
    }

    override fun setNextFloat(value: Float) {
        hasRemaining(this, Buffer.FLOAT_SIZE)
        writeFloat(value)
        forwardPosition(this, Buffer.FLOAT_SIZE)
    }

    override fun setNextDouble(value: Double) {
        hasRemaining(this, Buffer.DOUBLE_SIZE)
        writeDouble(value)
        forwardPosition(this, Buffer.DOUBLE_SIZE)
    }

    /**
     * save one byte to underlying memory.
     *
     * @param index index in memory
     * @param value byte to save
     */
    internal abstract fun saveByte(index: Int, value: Byte)

    /**
     * save long to underlying memory.
     *
     * @param index index in memory
     * @param value long to save
     */
    internal abstract fun saveLong(index: Int, value: Long)

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
}