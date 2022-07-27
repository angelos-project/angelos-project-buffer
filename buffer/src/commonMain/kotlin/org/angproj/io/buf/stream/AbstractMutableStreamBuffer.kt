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
package org.angproj.io.buf.stream

import org.angproj.io.buf.Buffer
import org.angproj.io.buf.Endianness

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
abstract class AbstractMutableStreamBuffer internal constructor(
    size: Int,
    limit: Int,
    position: Int,
    endianness: Endianness,
) : AbstractStreamBuffer(size, limit, position, endianness), MutableStreamBuffer {

    override fun setWriteByte(value: Byte) {
        hasRemaining(this, Buffer.BYTE_SIZE)
        writeByte(value)
        forwardPosition(this, Buffer.BYTE_SIZE)
    }

    override fun setWriteUByte(value: UByte) {
        hasRemaining(this, Buffer.UBYTE_SIZE)
        writeUByte(value)
        forwardPosition(this, Buffer.UBYTE_SIZE)
    }

    override fun setWriteChar(value: Char) {
        hasRemaining(this, Buffer.CHAR_SIZE)
        writeChar(value)
        forwardPosition(this, Buffer.CHAR_SIZE)
    }

    override fun setWriteShort(value: Short) {
        hasRemaining(this, Buffer.SHORT_SIZE)
        writeShort(value)
        forwardPosition(this, Buffer.SHORT_SIZE)
    }

    override fun setWriteUShort(value: UShort) {
        hasRemaining(this, Buffer.USHORT_SIZE)
        writeUShort(value)
        forwardPosition(this, Buffer.USHORT_SIZE)
    }

    override fun setWriteInt(value: Int) {
        hasRemaining(this, Buffer.INT_SIZE)
        writeInt(value)
        forwardPosition(this, Buffer.INT_SIZE)
    }

    override fun setWriteUInt(value: UInt) {
        hasRemaining(this, Buffer.UINT_SIZE)
        writeUInt(value)
        forwardPosition(this, Buffer.UINT_SIZE)
    }

    override fun setWriteLong(value: Long) {
        hasRemaining(this, Buffer.LONG_SIZE)
        writeLong(value)
        forwardPosition(this, Buffer.LONG_SIZE)
    }

    override fun setWriteULong(value: ULong) {
        hasRemaining(this, Buffer.ULONG_SIZE)
        writeULong(value)
        forwardPosition(this, Buffer.ULONG_SIZE)
    }

    override fun setWriteFloat(value: Float) {
        hasRemaining(this, Buffer.FLOAT_SIZE)
        writeFloat(value)
        forwardPosition(this, Buffer.FLOAT_SIZE)
    }

    override fun setWriteDouble(value: Double) {
        hasRemaining(this, Buffer.DOUBLE_SIZE)
        writeDouble(value)
        forwardPosition(this, Buffer.DOUBLE_SIZE)
    }

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