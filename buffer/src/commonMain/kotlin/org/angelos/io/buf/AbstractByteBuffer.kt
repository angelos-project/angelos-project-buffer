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
 * Abstract byte buffer implements the read and endianness logic directly for use.
 *
 * @constructor
 *
 * @param size
 * @param limit
 * @param position
 * @param endianness
 */
abstract class AbstractByteBuffer internal constructor(
    size: Int,
    limit: Int,
    position: Int,
    endianness: Endianness
) : AbstractBuffer(size, limit, position, endianness) {

    override fun getNextByte(): Byte {
        Buffer.hasRemaining(this, Buffer.BYTE_SIZE)
        val value = readByte(this)
        forwardPosition(this, Buffer.BYTE_SIZE)
        return value
    }

    override fun getNextUByte(): UByte {
        Buffer.hasRemaining(this, Buffer.UBYTE_SIZE)
        val value = readUByte(this)
        forwardPosition(this, Buffer.UBYTE_SIZE)
        return value
    }

    override fun getNextChar(): Char {
        Buffer.hasRemaining(this, Buffer.CHAR_SIZE)
        val value = readChar(this)
        forwardPosition(this, Buffer.CHAR_SIZE)
        return value
    }

    override fun getNextShort(): Short {
        Buffer.hasRemaining(this, Buffer.SHORT_SIZE)
        val value = readShort(this)
        forwardPosition(this, Buffer.SHORT_SIZE)
        return value
    }

    override fun getNextUShort(): UShort {
        Buffer.hasRemaining(this, Buffer.USHORT_SIZE)
        val value = readUShort(this)
        forwardPosition(this, Buffer.USHORT_SIZE)
        return value
    }

    override fun getNextInt(): Int {
        Buffer.hasRemaining(this, Buffer.INT_SIZE)
        val value = readInt(this)
        forwardPosition(this, Buffer.INT_SIZE)
        return value
    }

    override fun getNextUInt(): UInt {
        Buffer.hasRemaining(this, Buffer.UINT_SIZE)
        val value = readUInt(this)
        forwardPosition(this, Buffer.UINT_SIZE)
        return value
    }

    override fun getNextLong(): Long {
        Buffer.hasRemaining(this, Buffer.LONG_SIZE)
        val value = readLong(this)
        forwardPosition(this, Buffer.LONG_SIZE)
        return value
    }

    override fun getNextULong(): ULong {
        Buffer.hasRemaining(this, Buffer.ULONG_SIZE)
        val value = readULong(this)
        forwardPosition(this, Buffer.ULONG_SIZE)
        return value
    }

    override fun getNextFloat(): Float {
        Buffer.hasRemaining(this, Buffer.FLOAT_SIZE)
        val value = readFloat(this)
        forwardPosition(this, Buffer.FLOAT_SIZE)
        return Float.fromBits(value)
    }

    override fun getNextDouble(): Double {
        Buffer.hasRemaining(this, Buffer.DOUBLE_SIZE)
        val value = readDouble(this)
        forwardPosition(this, Buffer.DOUBLE_SIZE)
        return Double.fromBits(value)
    }

    override fun loadByte(index: Int): Byte {
        return load(index).toByte()
    }

    override fun loadLong(index: Int): Long = load(position + 7).toLong() or
            (load(position + 6).toLong() shl 8) or
            (load(position + 5).toLong() shl 16) or
            (load(position + 4).toLong() shl 24) or
            (load(position + 3).toLong() shl 32) or
            (load(position + 2).toLong() shl 40) or
            (load(position + 1).toLong() shl 48) or
            (load(position + 0).toLong() shl 56)

    /**
     * Load a certain byte at a specific offset.
     *
     * @return a byte of actual data
     */
    internal abstract fun load(index: Int): UByte

    companion object {
        internal inline fun forwardPosition(buf: AbstractByteBuffer, length: Int) {
            buf._position += length
        }

        internal inline fun readByte(buf: AbstractByteBuffer): Byte = buf.load(buf.position + 0).toByte()

        internal inline fun readUByte(buf: AbstractByteBuffer): UByte = buf.load(buf.position + 0)

        internal inline fun readChar(buf: AbstractByteBuffer): Char = when (buf.reverse) {
            true -> buf.load(buf.position + 0).toInt() or
                    (buf.load(buf.position + 1).toInt() shl 8)
            false -> buf.load(buf.position + 1).toInt() or
                    (buf.load(buf.position + 0).toInt() shl 8)
        }.toChar()

        internal inline fun readShort(buf: AbstractByteBuffer): Short = when (buf.reverse) {
            true -> buf.load(buf.position + 0).toInt() or
                    (buf.load(buf.position + 1).toInt() shl 8)
            false -> buf.load(buf.position + 1).toInt() or
                    (buf.load(buf.position + 0).toInt() shl 8)
        }.toShort()

        internal inline fun readUShort(buf: AbstractByteBuffer): UShort = when (buf.reverse) {
            true -> (buf.load(buf.position + 0).toInt() or
                    (buf.load(buf.position + 1).toInt() shl 8))
            false -> (buf.load(buf.position + 1).toInt() or
                    (buf.load(buf.position + 0).toInt() shl 8))
        }.toUShort()

        internal inline fun readInt(buf: AbstractByteBuffer): Int = when (buf.reverse) {
            true -> buf.load(buf.position + 0).toInt() or
                    (buf.load(buf.position + 1).toInt() shl 8) or
                    (buf.load(buf.position + 2).toInt() shl 16) or
                    (buf.load(buf.position + 3).toInt() shl 24)
            false -> buf.load(buf.position + 3).toInt() or
                    (buf.load(buf.position + 2).toInt() shl 8) or
                    (buf.load(buf.position + 1).toInt() shl 16) or
                    (buf.load(buf.position + 0).toInt() shl 24)
        }

        internal inline fun readUInt(buf: AbstractByteBuffer): UInt = when (buf.reverse) {
            true -> buf.load(buf.position + 0).toUInt() or
                    (buf.load(buf.position + 1).toUInt() shl 8) or
                    (buf.load(buf.position + 2).toUInt() shl 16) or
                    (buf.load(buf.position + 3).toUInt() shl 24)
            false -> buf.load(buf.position + 3).toUInt() or
                    (buf.load(buf.position + 2).toUInt() shl 8) or
                    (buf.load(buf.position + 1).toUInt() shl 16) or
                    (buf.load(buf.position + 0).toUInt() shl 24)
        }

        internal inline fun readLong(buf: AbstractByteBuffer): Long = when (buf.reverse) {
            true -> buf.load(buf.position + 0).toLong() or
                    (buf.load(buf.position + 1).toLong() shl 8) or
                    (buf.load(buf.position + 2).toLong() shl 16) or
                    (buf.load(buf.position + 3).toLong() shl 24) or
                    (buf.load(buf.position + 4).toLong() shl 32) or
                    (buf.load(buf.position + 5).toLong() shl 40) or
                    (buf.load(buf.position + 6).toLong() shl 48) or
                    (buf.load(buf.position + 7).toLong() shl 56)
            false -> buf.load(buf.position + 7).toLong() or
                    (buf.load(buf.position + 6).toLong() shl 8) or
                    (buf.load(buf.position + 5).toLong() shl 16) or
                    (buf.load(buf.position + 4).toLong() shl 24) or
                    (buf.load(buf.position + 3).toLong() shl 32) or
                    (buf.load(buf.position + 2).toLong() shl 40) or
                    (buf.load(buf.position + 1).toLong() shl 48) or
                    (buf.load(buf.position + 0).toLong() shl 56)
        }

        internal inline fun readULong(buf: AbstractByteBuffer): ULong = when (buf.reverse) {
            true -> buf.load(buf.position + 0).toULong() or
                    (buf.load(buf.position + 1).toULong() shl 8) or
                    (buf.load(buf.position + 2).toULong() shl 16) or
                    (buf.load(buf.position + 3).toULong() shl 24) or
                    (buf.load(buf.position + 4).toULong() shl 32) or
                    (buf.load(buf.position + 5).toULong() shl 40) or
                    (buf.load(buf.position + 6).toULong() shl 48) or
                    (buf.load(buf.position + 7).toULong() shl 56)
            false -> buf.load(buf.position + 7).toULong() or
                    (buf.load(buf.position + 6).toULong() shl 8) or
                    (buf.load(buf.position + 5).toULong() shl 16) or
                    (buf.load(buf.position + 4).toULong() shl 24) or
                    (buf.load(buf.position + 3).toULong() shl 32) or
                    (buf.load(buf.position + 2).toULong() shl 40) or
                    (buf.load(buf.position + 1).toULong() shl 48) or
                    (buf.load(buf.position + 0).toULong() shl 56)
        }

        internal inline fun readFloat(buf: AbstractByteBuffer): Int = when (buf.reverse) {
            true -> buf.load(buf.position + 0).toInt() or
                    (buf.load(buf.position + 1).toInt() shl 8) or
                    (buf.load(buf.position + 2).toInt() shl 16) or
                    (buf.load(buf.position + 3).toInt() shl 24)
            false -> buf.load(buf.position + 3).toInt() or
                    (buf.load(buf.position + 2).toInt() shl 8) or
                    (buf.load(buf.position + 1).toInt() shl 16) or
                    (buf.load(buf.position + 0).toInt() shl 24)
        }

        internal inline fun readDouble(buf: AbstractByteBuffer): Long = when (buf.reverse) {
            true -> buf.load(buf.position + 0).toLong() or
                    (buf.load(buf.position + 1).toLong() shl 8) or
                    (buf.load(buf.position + 2).toLong() shl 16) or
                    (buf.load(buf.position + 3).toLong() shl 24) or
                    (buf.load(buf.position + 4).toLong() shl 32) or
                    (buf.load(buf.position + 5).toLong() shl 40) or
                    (buf.load(buf.position + 6).toLong() shl 48) or
                    (buf.load(buf.position + 7).toLong() shl 56)
            false -> buf.load(buf.position + 7).toLong() or
                    (buf.load(buf.position + 6).toLong() shl 8) or
                    (buf.load(buf.position + 5).toLong() shl 16) or
                    (buf.load(buf.position + 4).toLong() shl 24) or
                    (buf.load(buf.position + 3).toLong() shl 32) or
                    (buf.load(buf.position + 2).toLong() shl 40) or
                    (buf.load(buf.position + 1).toLong() shl 48) or
                    (buf.load(buf.position + 0).toLong() shl 56)
        }
    }
}