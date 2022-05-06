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
        writeFloat(value.toRawBits())
        forwardPosition(this, Buffer.FLOAT_SIZE)
    }

    override fun setNextDouble(value: Double) {
        hasRemaining(this, Buffer.DOUBLE_SIZE)
        writeDouble(value.toRawBits())
        forwardPosition(this, Buffer.DOUBLE_SIZE)
    }

    /**
     * Save a specific byte in a certain position.
     *
     * @param value byte to save
     * @param index offset where to save
     */
    internal abstract fun save(value: UByte, index: Int)

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

    internal abstract fun writeFloat(value: Int)

    internal abstract fun writeDouble(value: Long)

    companion object {
        internal inline fun saveWriteReverseShort(buf: AbstractMutableBuffer, pos: Int, value: Int) {
            buf.save((value and 0xFF).toUByte(), pos + 0)
            buf.save(((value ushr 8) and 0xFF).toUByte(), pos + 1)
        }

        internal inline fun saveWriteShort(buf: AbstractMutableBuffer, pos: Int, value: Int) {
            buf.save((value and 0xFF).toUByte(), pos + 1)
            buf.save(((value ushr 8) and 0xFF).toUByte(), pos + 0)
        }

        internal inline fun saveWriteReverseInt(buf: AbstractMutableBuffer, pos: Int, value: Int) {
            buf.save((value and 0xFF).toUByte(), pos + 0)
            buf.save(((value ushr 8) and 0xFF).toUByte(), pos + 1)
            buf.save(((value ushr 16) and 0xFF).toUByte(), pos + 2)
            buf.save(((value ushr 24) and 0xFF).toUByte(), pos + 3)
        }

        internal inline fun saveWriteInt(buf: AbstractMutableBuffer, pos: Int, value: Int) {
            buf.save((value and 0xFF).toUByte(), pos + 3)
            buf.save(((value ushr 8) and 0xFF).toUByte(), pos + 2)
            buf.save(((value ushr 16) and 0xFF).toUByte(), pos + 1)
            buf.save(((value ushr 24) and 0xFF).toUByte(), pos + 0)
        }

        internal inline fun saveWriteReverseUInt(buf: AbstractMutableBuffer, pos: Int, value: UInt) {
            buf.save((value.toInt() and 0xFF).toUByte(), pos + 0)
            buf.save(((value.toInt() ushr 8) and 0xFF).toUByte(), pos + 1)
            buf.save(((value.toInt() ushr 16) and 0xFF).toUByte(), pos + 2)
            buf.save(((value.toInt() ushr 24) and 0xFF).toUByte(), pos + 3)
        }

        internal inline fun saveWriteUInt(buf: AbstractMutableBuffer, pos: Int, value: UInt) {
            buf.save((value.toInt() and 0xFF).toUByte(), pos + 3)
            buf.save(((value.toInt() ushr 8) and 0xFF).toUByte(), pos + 2)
            buf.save(((value.toInt() ushr 16) and 0xFF).toUByte(), pos + 1)
            buf.save(((value.toInt() ushr 24) and 0xFF).toUByte(), pos + 0)
        }

        internal inline fun saveWriteReverseLong(buf: AbstractMutableBuffer, pos: Int, value: Long) {
            buf.save((value and 0xFF).toUByte(), pos + 0)
            buf.save(((value ushr 8) and 0xFF).toUByte(), pos + 1)
            buf.save(((value ushr 16) and 0xFF).toUByte(), pos + 2)
            buf.save(((value ushr 24) and 0xFF).toUByte(), pos + 3)
            buf.save(((value ushr 32) and 0xFF).toUByte(), pos + 4)
            buf.save(((value ushr 40) and 0xFF).toUByte(), pos + 5)
            buf.save(((value ushr 48) and 0xFF).toUByte(), pos + 6)
            buf.save(((value ushr 56) and 0xFF).toUByte(), pos + 7)
        }

        internal inline fun saveWriteLong(buf: AbstractMutableBuffer, pos: Int, value: Long) {
            buf.save((value and 0xFF).toUByte(), pos + 7)
            buf.save(((value ushr 8) and 0xFF).toUByte(), pos + 6)
            buf.save(((value ushr 16) and 0xFF).toUByte(), pos + 5)
            buf.save(((value ushr 24) and 0xFF).toUByte(), pos + 4)
            buf.save(((value ushr 32) and 0xFF).toUByte(), pos + 3)
            buf.save(((value ushr 40) and 0xFF).toUByte(), pos + 2)
            buf.save(((value ushr 48) and 0xFF).toUByte(), pos + 1)
            buf.save(((value ushr 56) and 0xFF).toUByte(), pos + 0)
        }

        internal inline fun saveWriteReverseULong(buf: AbstractMutableBuffer, pos: Int, value: ULong) {
            buf.save((value.toLong() and 0xFF).toUByte(), pos + 0)
            buf.save(((value.toLong() ushr 8) and 0xFF).toUByte(), pos + 1)
            buf.save(((value.toLong() ushr 16) and 0xFF).toUByte(), pos + 2)
            buf.save(((value.toLong() ushr 24) and 0xFF).toUByte(), pos + 3)
            buf.save(((value.toLong() ushr 32) and 0xFF).toUByte(), pos + 4)
            buf.save(((value.toLong() ushr 40) and 0xFF).toUByte(), pos + 5)
            buf.save(((value.toLong() ushr 48) and 0xFF).toUByte(), pos + 6)
            buf.save(((value.toLong() ushr 56) and 0xFF).toUByte(), pos + 7)
        }

        internal inline fun saveWriteULong(buf: AbstractMutableBuffer, pos: Int, value: ULong) {
            buf.save((value.toLong() and 0xFF).toUByte(), pos + 7)
            buf.save(((value.toLong() ushr 8) and 0xFF).toUByte(), pos + 6)
            buf.save(((value.toLong() ushr 16) and 0xFF).toUByte(), pos + 5)
            buf.save(((value.toLong() ushr 24) and 0xFF).toUByte(), pos + 4)
            buf.save(((value.toLong() ushr 32) and 0xFF).toUByte(), pos + 3)
            buf.save(((value.toLong() ushr 40) and 0xFF).toUByte(), pos + 2)
            buf.save(((value.toLong() ushr 48) and 0xFF).toUByte(), pos + 1)
            buf.save(((value.toLong() ushr 56) and 0xFF).toUByte(), pos + 0)
        }
    }
}