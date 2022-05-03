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
 * Abstract mutable byte buffer implements the logic for write operations.
 *
 * @constructor
 *
 * @param size
 * @param limit
 * @param position
 * @param endianness
 */
abstract class AbstractMutableByteBuffer internal constructor(
    size: Int,
    limit: Int,
    position: Int,
    endianness: Endianness,
) : AbstractByteBuffer(size, limit, position, endianness), MutableBuffer {

    override fun setNextByte(value: Byte) {
        Buffer.hasRemaining(this, Buffer.BYTE_SIZE)
        writeByte(this, value)
        forwardPosition(this, Buffer.BYTE_SIZE)
    }

    override fun setNextUByte(value: UByte) {
        Buffer.hasRemaining(this, Buffer.UBYTE_SIZE)
        writeUByte(this, value)
        forwardPosition(this, Buffer.UBYTE_SIZE)
    }

    override fun setNextChar(value: Char) {
        Buffer.hasRemaining(this, Buffer.CHAR_SIZE)
        writeChar(this, value)
        forwardPosition(this, Buffer.CHAR_SIZE)
    }

    override fun setNextShort(value: Short) {
        Buffer.hasRemaining(this, Buffer.SHORT_SIZE)
        writeShort(this, value)
        forwardPosition(this, Buffer.SHORT_SIZE)
    }

    override fun setNextUShort(value: UShort) {
        Buffer.hasRemaining(this, Buffer.USHORT_SIZE)
        writeUShort(this, value)
        forwardPosition(this, Buffer.USHORT_SIZE)
    }

    override fun setNextInt(value: Int) {
        Buffer.hasRemaining(this, Buffer.INT_SIZE)
        writeInt(this, value)
        forwardPosition(this, Buffer.INT_SIZE)
    }

    override fun setNextUInt(value: UInt) {
        Buffer.hasRemaining(this, Buffer.UINT_SIZE)
        writeUInt(this, value)
        forwardPosition(this, Buffer.UINT_SIZE)
    }

    override fun setNextLong(value: Long) {
        Buffer.hasRemaining(this, Buffer.LONG_SIZE)
        writeLong(this, value)
        forwardPosition(this, Buffer.LONG_SIZE)
    }

    override fun setNextULong(value: ULong) {
        Buffer.hasRemaining(this, Buffer.ULONG_SIZE)
        writeULong(this, value)
        forwardPosition(this, Buffer.ULONG_SIZE)
    }

    override fun setNextFloat(value: Float) {
        Buffer.hasRemaining(this, Buffer.FLOAT_SIZE)
        writeFloat(this, value.toRawBits())
        forwardPosition(this, Buffer.FLOAT_SIZE)
    }

    override fun setNextDouble(value: Double) {
        Buffer.hasRemaining(this, Buffer.DOUBLE_SIZE)
        writeDouble(this, value.toRawBits())
        forwardPosition(this, Buffer.DOUBLE_SIZE)
    }

    override fun saveByte(index: Int, value: Byte) {
        save(value.toUByte(), index)
    }

    override fun saveLong(index: Int, value: Long) {
        save((value and 0xFF).toUByte(), index + 7)
        save(((value ushr 8) and 0xFF).toUByte(), index + 6)
        save(((value ushr 16) and 0xFF).toUByte(), index + 5)
        save(((value ushr 24) and 0xFF).toUByte(), index + 4)
        save(((value ushr 32) and 0xFF).toUByte(), index + 3)
        save(((value ushr 40) and 0xFF).toUByte(), index + 2)
        save(((value ushr 48) and 0xFF).toUByte(), index + 1)
        save(((value ushr 56) and 0xFF).toUByte(), index + 0)
    }

    /**
     * Save a specific byte in a certain position.
     *
     * @param value byte to save
     * @param index offset where to save
     */
    internal abstract fun save(value: UByte, index: Int)

    companion object {
        internal inline fun writeByte(buf: AbstractMutableByteBuffer, value: Byte) =
            buf.save((value.toInt() and 0xFF).toUByte(), buf.position + 0)

        internal inline fun writeUByte(buf: AbstractMutableByteBuffer, value: UByte) = buf.save(value, buf.position + 0)

        internal inline fun writeChar(buf: AbstractMutableByteBuffer, value: Char) = when (buf.reverse) {
            true -> {
                buf.save((value.code and 0xFF).toUByte(), buf.position + 0)
                buf.save(((value.code ushr 8) and 0xFF).toUByte(), buf.position + 1)
            }
            false -> {
                buf.save((value.code and 0xFF).toUByte(), buf.position + 1)
                buf.save(((value.code ushr 8) and 0xFF).toUByte(), buf.position + 0)
            }
        }

        internal inline fun writeShort(buf: AbstractMutableByteBuffer, value: Short) = when (buf.reverse) {
            true -> {
                buf.save((value.toInt() and 0xFF).toUByte(), buf.position + 0)
                buf.save(((value.toInt() ushr 8) and 0xFF).toUByte(), buf.position + 1)
            }
            false -> {
                buf.save((value.toInt() and 0xFF).toUByte(), buf.position + 1)
                buf.save(((value.toInt() ushr 8) and 0xFF).toUByte(), buf.position + 0)
            }
        }

        internal inline fun writeUShort(buf: AbstractMutableByteBuffer, value: UShort) = when (buf.reverse) {
            true -> {
                buf.save((value.toInt() and 0xFF).toUByte(), buf.position + 0)
                buf.save(((value.toInt() ushr 8) and 0xFF).toUByte(), buf.position + 1)
            }
            false -> {
                buf.save((value.toInt() and 0xFF).toUByte(), buf.position + 1)
                buf.save(((value.toInt() ushr 8) and 0xFF).toUByte(), buf.position + 0)
            }
        }

        internal inline fun writeInt(buf: AbstractMutableByteBuffer, value: Int) = when (buf.reverse) {
            true -> {
                buf.save((value and 0xFF).toUByte(), buf.position + 0)
                buf.save(((value ushr 8) and 0xFF).toUByte(), buf.position + 1)
                buf.save(((value ushr 16) and 0xFF).toUByte(), buf.position + 2)
                buf.save(((value ushr 24) and 0xFF).toUByte(), buf.position + 3)
            }
            false -> {
                buf.save((value and 0xFF).toUByte(), buf.position + 3)
                buf.save(((value ushr 8) and 0xFF).toUByte(), buf.position + 2)
                buf.save(((value ushr 16) and 0xFF).toUByte(), buf.position + 1)
                buf.save(((value ushr 24) and 0xFF).toUByte(), buf.position + 0)
            }
        }

        internal inline fun writeUInt(buf: AbstractMutableByteBuffer, value: UInt) = when (buf.reverse) {
            true -> {
                buf.save((value.toInt() and 0xFF).toUByte(), buf.position + 0)
                buf.save(((value.toInt() ushr 8) and 0xFF).toUByte(), buf.position + 1)
                buf.save(((value.toInt() ushr 16) and 0xFF).toUByte(), buf.position + 2)
                buf.save(((value.toInt() ushr 24) and 0xFF).toUByte(), buf.position + 3)
            }
            false -> {
                buf.save((value.toInt() and 0xFF).toUByte(), buf.position + 3)
                buf.save(((value.toInt() ushr 8) and 0xFF).toUByte(), buf.position + 2)
                buf.save(((value.toInt() ushr 16) and 0xFF).toUByte(), buf.position + 1)
                buf.save(((value.toInt() ushr 24) and 0xFF).toUByte(), buf.position + 0)
            }
        }

        internal inline fun writeLong(buf: AbstractMutableByteBuffer, value: Long) = when (buf.reverse) {
            true -> {
                buf.save((value and 0xFF).toUByte(), buf.position + 0)
                buf.save(((value ushr 8) and 0xFF).toUByte(), buf.position + 1)
                buf.save(((value ushr 16) and 0xFF).toUByte(), buf.position + 2)
                buf.save(((value ushr 24) and 0xFF).toUByte(), buf.position + 3)
                buf.save(((value ushr 32) and 0xFF).toUByte(), buf.position + 4)
                buf.save(((value ushr 40) and 0xFF).toUByte(), buf.position + 5)
                buf.save(((value ushr 48) and 0xFF).toUByte(), buf.position + 6)
                buf.save(((value ushr 56) and 0xFF).toUByte(), buf.position + 7)
            }
            false -> {
                buf.save((value and 0xFF).toUByte(), buf.position + 7)
                buf.save(((value ushr 8) and 0xFF).toUByte(), buf.position + 6)
                buf.save(((value ushr 16) and 0xFF).toUByte(), buf.position + 5)
                buf.save(((value ushr 24) and 0xFF).toUByte(), buf.position + 4)
                buf.save(((value ushr 32) and 0xFF).toUByte(), buf.position + 3)
                buf.save(((value ushr 40) and 0xFF).toUByte(), buf.position + 2)
                buf.save(((value ushr 48) and 0xFF).toUByte(), buf.position + 1)
                buf.save(((value ushr 56) and 0xFF).toUByte(), buf.position + 0)
            }
        }

        internal inline fun writeULong(buf: AbstractMutableByteBuffer, value: ULong) = when (buf.reverse) {
            true -> {
                buf.save((value.toLong() and 0xFF).toUByte(), buf.position + 0)
                buf.save(((value.toLong() ushr 8) and 0xFF).toUByte(), buf.position + 1)
                buf.save(((value.toLong() ushr 16) and 0xFF).toUByte(), buf.position + 2)
                buf.save(((value.toLong() ushr 24) and 0xFF).toUByte(), buf.position + 3)
                buf.save(((value.toLong() ushr 32) and 0xFF).toUByte(), buf.position + 4)
                buf.save(((value.toLong() ushr 40) and 0xFF).toUByte(), buf.position + 5)
                buf.save(((value.toLong() ushr 48) and 0xFF).toUByte(), buf.position + 6)
                buf.save(((value.toLong() ushr 56) and 0xFF).toUByte(), buf.position + 7)
            }
            false -> {
                buf.save((value.toLong() and 0xFF).toUByte(), buf.position + 7)
                buf.save(((value.toLong() ushr 8) and 0xFF).toUByte(), buf.position + 6)
                buf.save(((value.toLong() ushr 16) and 0xFF).toUByte(), buf.position + 5)
                buf.save(((value.toLong() ushr 24) and 0xFF).toUByte(), buf.position + 4)
                buf.save(((value.toLong() ushr 32) and 0xFF).toUByte(), buf.position + 3)
                buf.save(((value.toLong() ushr 40) and 0xFF).toUByte(), buf.position + 2)
                buf.save(((value.toLong() ushr 48) and 0xFF).toUByte(), buf.position + 1)
                buf.save(((value.toLong() ushr 56) and 0xFF).toUByte(), buf.position + 0)
            }
        }

        internal inline fun writeFloat(buf: AbstractMutableByteBuffer, value: Int) = when (buf.reverse) {
            true -> {
                buf.save((value and 0xFF).toUByte(), buf.position + 0)
                buf.save(((value ushr 8) and 0xFF).toUByte(), buf.position + 1)
                buf.save(((value ushr 16) and 0xFF).toUByte(), buf.position + 2)
                buf.save(((value ushr 24) and 0xFF).toUByte(), buf.position + 3)
            }
            false -> {
                buf.save((value and 0xFF).toUByte(), buf.position + 3)
                buf.save(((value ushr 8) and 0xFF).toUByte(), buf.position + 2)
                buf.save(((value ushr 16) and 0xFF).toUByte(), buf.position + 1)
                buf.save(((value ushr 24) and 0xFF).toUByte(), buf.position + 0)
            }
        }

        internal inline fun writeDouble(buf: AbstractMutableByteBuffer, value: Long) = when (buf.reverse) {
            true -> {
                buf.save((value and 0xFF).toUByte(), buf.position + 0)
                buf.save(((value ushr 8) and 0xFF).toUByte(), buf.position + 1)
                buf.save(((value ushr 16) and 0xFF).toUByte(), buf.position + 2)
                buf.save(((value ushr 24) and 0xFF).toUByte(), buf.position + 3)
                buf.save(((value ushr 32) and 0xFF).toUByte(), buf.position + 4)
                buf.save(((value ushr 40) and 0xFF).toUByte(), buf.position + 5)
                buf.save(((value ushr 48) and 0xFF).toUByte(), buf.position + 6)
                buf.save(((value ushr 56) and 0xFF).toUByte(), buf.position + 7)
            }
            false -> {
                buf.save((value and 0xFF).toUByte(), buf.position + 7)
                buf.save(((value ushr 8) and 0xFF).toUByte(), buf.position + 6)
                buf.save(((value ushr 16) and 0xFF).toUByte(), buf.position + 5)
                buf.save(((value ushr 24) and 0xFF).toUByte(), buf.position + 4)
                buf.save(((value ushr 32) and 0xFF).toUByte(), buf.position + 3)
                buf.save(((value ushr 40) and 0xFF).toUByte(), buf.position + 2)
                buf.save(((value ushr 48) and 0xFF).toUByte(), buf.position + 1)
                buf.save(((value ushr 56) and 0xFF).toUByte(), buf.position + 0)
            }
        }
    }
}