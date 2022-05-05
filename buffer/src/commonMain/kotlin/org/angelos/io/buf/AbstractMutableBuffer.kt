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

    internal fun writeByte(value: Byte) =
        save((value.toInt() and 0xFF).toUByte(), position + 0)

    internal fun writeUByte(value: UByte) = save(value, position + 0)

    internal fun writeChar(value: Char) = when (reverse) {
        true -> {
            save((value.code and 0xFF).toUByte(), position + 0)
            save(((value.code ushr 8) and 0xFF).toUByte(), position + 1)
        }
        false -> {
            save((value.code and 0xFF).toUByte(), position + 1)
            save(((value.code ushr 8) and 0xFF).toUByte(), position + 0)
        }
    }

    internal fun writeShort(value: Short) = when (reverse) {
        true -> {
            save((value.toInt() and 0xFF).toUByte(), position + 0)
            save(((value.toInt() ushr 8) and 0xFF).toUByte(), position + 1)
        }
        false -> {
            save((value.toInt() and 0xFF).toUByte(), position + 1)
            save(((value.toInt() ushr 8) and 0xFF).toUByte(), position + 0)
        }
    }

    internal fun writeUShort(value: UShort) = when (reverse) {
        true -> {
            save((value.toInt() and 0xFF).toUByte(), position + 0)
            save(((value.toInt() ushr 8) and 0xFF).toUByte(), position + 1)
        }
        false -> {
            save((value.toInt() and 0xFF).toUByte(), position + 1)
            save(((value.toInt() ushr 8) and 0xFF).toUByte(), position + 0)
        }
    }

    internal fun writeInt(value: Int) = when (reverse) {
        true -> {
            save((value and 0xFF).toUByte(), position + 0)
            save(((value ushr 8) and 0xFF).toUByte(), position + 1)
            save(((value ushr 16) and 0xFF).toUByte(), position + 2)
            save(((value ushr 24) and 0xFF).toUByte(), position + 3)
        }
        false -> {
            save((value and 0xFF).toUByte(), position + 3)
            save(((value ushr 8) and 0xFF).toUByte(), position + 2)
            save(((value ushr 16) and 0xFF).toUByte(), position + 1)
            save(((value ushr 24) and 0xFF).toUByte(), position + 0)
        }
    }

    internal fun writeUInt(value: UInt) = when (reverse) {
        true -> {
            save((value.toInt() and 0xFF).toUByte(), position + 0)
            save(((value.toInt() ushr 8) and 0xFF).toUByte(), position + 1)
            save(((value.toInt() ushr 16) and 0xFF).toUByte(), position + 2)
            save(((value.toInt() ushr 24) and 0xFF).toUByte(), position + 3)
        }
        false -> {
            save((value.toInt() and 0xFF).toUByte(), position + 3)
            save(((value.toInt() ushr 8) and 0xFF).toUByte(), position + 2)
            save(((value.toInt() ushr 16) and 0xFF).toUByte(), position + 1)
            save(((value.toInt() ushr 24) and 0xFF).toUByte(), position + 0)
        }
    }

    internal fun writeLong(value: Long) = when (reverse) {
        true -> {
            save((value and 0xFF).toUByte(), position + 0)
            save(((value ushr 8) and 0xFF).toUByte(), position + 1)
            save(((value ushr 16) and 0xFF).toUByte(), position + 2)
            save(((value ushr 24) and 0xFF).toUByte(), position + 3)
            save(((value ushr 32) and 0xFF).toUByte(), position + 4)
            save(((value ushr 40) and 0xFF).toUByte(), position + 5)
            save(((value ushr 48) and 0xFF).toUByte(), position + 6)
            save(((value ushr 56) and 0xFF).toUByte(), position + 7)
        }
        false -> {
            save((value and 0xFF).toUByte(), position + 7)
            save(((value ushr 8) and 0xFF).toUByte(), position + 6)
            save(((value ushr 16) and 0xFF).toUByte(), position + 5)
            save(((value ushr 24) and 0xFF).toUByte(), position + 4)
            save(((value ushr 32) and 0xFF).toUByte(), position + 3)
            save(((value ushr 40) and 0xFF).toUByte(), position + 2)
            save(((value ushr 48) and 0xFF).toUByte(), position + 1)
            save(((value ushr 56) and 0xFF).toUByte(), position + 0)
        }
    }

    internal fun writeULong(value: ULong) = when (reverse) {
        true -> {
            save((value.toLong() and 0xFF).toUByte(), position + 0)
            save(((value.toLong() ushr 8) and 0xFF).toUByte(), position + 1)
            save(((value.toLong() ushr 16) and 0xFF).toUByte(), position + 2)
            save(((value.toLong() ushr 24) and 0xFF).toUByte(), position + 3)
            save(((value.toLong() ushr 32) and 0xFF).toUByte(), position + 4)
            save(((value.toLong() ushr 40) and 0xFF).toUByte(), position + 5)
            save(((value.toLong() ushr 48) and 0xFF).toUByte(), position + 6)
            save(((value.toLong() ushr 56) and 0xFF).toUByte(), position + 7)
        }
        false -> {
            save((value.toLong() and 0xFF).toUByte(), position + 7)
            save(((value.toLong() ushr 8) and 0xFF).toUByte(), position + 6)
            save(((value.toLong() ushr 16) and 0xFF).toUByte(), position + 5)
            save(((value.toLong() ushr 24) and 0xFF).toUByte(), position + 4)
            save(((value.toLong() ushr 32) and 0xFF).toUByte(), position + 3)
            save(((value.toLong() ushr 40) and 0xFF).toUByte(), position + 2)
            save(((value.toLong() ushr 48) and 0xFF).toUByte(), position + 1)
            save(((value.toLong() ushr 56) and 0xFF).toUByte(), position + 0)
        }
    }

    internal fun writeFloat(value: Int) = when (reverse) {
        true -> {
            save((value and 0xFF).toUByte(), position + 0)
            save(((value ushr 8) and 0xFF).toUByte(), position + 1)
            save(((value ushr 16) and 0xFF).toUByte(), position + 2)
            save(((value ushr 24) and 0xFF).toUByte(), position + 3)
        }
        false -> {
            save((value and 0xFF).toUByte(), position + 3)
            save(((value ushr 8) and 0xFF).toUByte(), position + 2)
            save(((value ushr 16) and 0xFF).toUByte(), position + 1)
            save(((value ushr 24) and 0xFF).toUByte(), position + 0)
        }
    }

    internal fun writeDouble(value: Long) = when (reverse) {
        true -> {
            save((value and 0xFF).toUByte(), position + 0)
            save(((value ushr 8) and 0xFF).toUByte(), position + 1)
            save(((value ushr 16) and 0xFF).toUByte(), position + 2)
            save(((value ushr 24) and 0xFF).toUByte(), position + 3)
            save(((value ushr 32) and 0xFF).toUByte(), position + 4)
            save(((value ushr 40) and 0xFF).toUByte(), position + 5)
            save(((value ushr 48) and 0xFF).toUByte(), position + 6)
            save(((value ushr 56) and 0xFF).toUByte(), position + 7)
        }
        false -> {
            save((value and 0xFF).toUByte(), position + 7)
            save(((value ushr 8) and 0xFF).toUByte(), position + 6)
            save(((value ushr 16) and 0xFF).toUByte(), position + 5)
            save(((value ushr 24) and 0xFF).toUByte(), position + 4)
            save(((value ushr 32) and 0xFF).toUByte(), position + 3)
            save(((value ushr 40) and 0xFF).toUByte(), position + 2)
            save(((value ushr 48) and 0xFF).toUByte(), position + 1)
            save(((value ushr 56) and 0xFF).toUByte(), position + 0)
        }
    }
}