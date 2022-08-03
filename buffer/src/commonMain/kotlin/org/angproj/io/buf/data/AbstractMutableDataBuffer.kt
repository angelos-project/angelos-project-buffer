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

import org.angproj.io.buf.Buffer
import org.angproj.io.buf.Endianness
import org.angproj.io.buf.Internals

/**
 * Abstract base class for all mutable data-buffers.
 *
 * @constructor
 *
 * @param size Total size of the buffer.
 * @param limit The initial limitation of how far to operate into the buffer. Must never exceed the size.
 * @param endianness The initial current endianness of the buffer.
 */
abstract class AbstractMutableDataBuffer(size: Int, limit: Int, endianness: Endianness) : AbstractDataBuffer(
    size,
    limit,
    endianness
), MutableDataBuffer {
    override fun reset() {
        _limit = size
        Internals.reset(this)
    }
    override fun setStoreByte(position: Int, value: Byte) {
        hasRemaining(this, position, Buffer.BYTE_SIZE)
        writeByte(position, value)
    }

    override fun setStoreUByte(position: Int, value: UByte) {
        hasRemaining(this, position, Buffer.UBYTE_SIZE)
        writeUByte(position, value)
    }

    override fun setStoreChar(position: Int, value: Char) {
        hasRemaining(this, position, Buffer.CHAR_SIZE)
        writeChar(position, value)
    }

    override fun setStoreShort(position: Int, value: Short) {
        hasRemaining(this, position, Buffer.SHORT_SIZE)
        writeShort(position, value)
    }

    override fun setStoreUShort(position: Int, value: UShort) {
        hasRemaining(this, position, Buffer.USHORT_SIZE)
        writeUShort(position, value)
    }

    override fun setStoreInt(position: Int, value: Int) {
        hasRemaining(this, position, Buffer.INT_SIZE)
        writeInt(position, value)
    }

    override fun setStoreUInt(position: Int, value: UInt) {
        hasRemaining(this, position, Buffer.UINT_SIZE)
        writeUInt(position, value)
    }

    override fun setStoreLong(position: Int, value: Long) {
        hasRemaining(this, position, Buffer.LONG_SIZE)
        writeLong(position, value)
    }

    override fun setStoreULong(position: Int, value: ULong) {
        hasRemaining(this, position, Buffer.ULONG_SIZE)
        writeULong(position, value)
    }

    override fun setStoreFloat(position: Int, value: Float) {
        hasRemaining(this, position, Buffer.FLOAT_SIZE)
        writeFloat(position, value)
    }

    override fun setStoreDouble(position: Int, value: Double) {
        hasRemaining(this, position, Buffer.DOUBLE_SIZE)
        writeDouble(position, value)
    }

    internal abstract fun writeByte(position: Int, value: Byte)

    internal abstract fun writeUByte(position: Int, value: UByte)

    internal abstract fun writeChar(position: Int, value: Char)

    internal abstract fun writeShort(position: Int, value: Short)

    internal abstract fun writeUShort(position: Int, value: UShort)

    internal abstract fun writeInt(position: Int, value: Int)

    internal abstract fun writeUInt(position: Int, value: UInt)

    internal abstract fun writeLong(position: Int, value: Long)

    internal abstract fun writeULong(position: Int, value: ULong)

    internal abstract fun writeFloat(position: Int, value: Float)

    internal abstract fun writeDouble(position: Int, value: Double)
}