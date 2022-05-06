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

import kotlin.math.absoluteValue
import kotlin.math.min

/**
 * Abstract buffer from which all buffer implementations must inherit.
 * Implements the basic logic regarding size, position and limit of reading and writing space.
 *
 * @constructor
 *
 * @param size max size of the buffer
 * @param limit initial limit if partial data already exists
 * @param position initial position in an already existing data stream
 * @param endianness endian of the buffered data
 */
abstract class AbstractBuffer internal constructor(
    size: Int,
    limit: Int,
    position: Int,
    endianness: Endianness,
) : Buffer, Gettable {
    override val size: Int = size.absoluteValue

    private var _limit: Int
    override val limit: Int
        get() = _limit

    internal var _position: Int
    override val position: Int
        get() = _position

    private var _reverse: Boolean
    override val reverse: Boolean
        get() = _reverse

    private var _endian: Endianness
    override var endian: Endianness
        get() = _endian
        set(value) {
            _endian = value
            _reverse = _endian != Buffer.nativeEndianness
        }

    override val optimized
        get() = false

    init {
        _limit = min(size.absoluteValue, limit.absoluteValue)
        _position = min(limit.absoluteValue, position.absoluteValue)
        _endian = endianness
        _reverse = _endian != Buffer.nativeEndianness
    }

    override fun clear() {
        _limit = size
        _position = 0
    }

    override fun flip() {
        _limit = _position
        _position = 0
    }

    override fun rewind() {
        _position = 0
    }

    override fun remaining(): Int = remaining(this)

    override fun hasRemaining(size: Int) = hasRemaining(this, size)

    override fun getNextByte(): Byte {
        hasRemaining(this, Buffer.BYTE_SIZE)
        val value = readByte()
        forwardPosition(this, Buffer.BYTE_SIZE)
        return value
    }

    override fun getNextUByte(): UByte {
        hasRemaining(this, Buffer.UBYTE_SIZE)
        val value = readUByte()
        forwardPosition(this, Buffer.UBYTE_SIZE)
        return value
    }

    override fun getNextChar(): Char {
        hasRemaining(this, Buffer.CHAR_SIZE)
        val value = readChar()
        forwardPosition(this, Buffer.CHAR_SIZE)
        return value
    }

    override fun getNextShort(): Short {
        hasRemaining(this, Buffer.SHORT_SIZE)
        val value = readShort()
        forwardPosition(this, Buffer.SHORT_SIZE)
        return value
    }

    override fun getNextUShort(): UShort {
        hasRemaining(this, Buffer.USHORT_SIZE)
        val value = readUShort()
        forwardPosition(this, Buffer.USHORT_SIZE)
        return value
    }

    override fun getNextInt(): Int {
        hasRemaining(this, Buffer.INT_SIZE)
        val value = readInt()
        forwardPosition(this, Buffer.INT_SIZE)
        return value
    }

    override fun getNextUInt(): UInt {
        hasRemaining(this, Buffer.UINT_SIZE)
        val value = readUInt()
        forwardPosition(this, Buffer.UINT_SIZE)
        return value
    }

    override fun getNextLong(): Long {
        hasRemaining(this, Buffer.LONG_SIZE)
        val value = readLong()
        forwardPosition(this, Buffer.LONG_SIZE)
        return value
    }

    override fun getNextULong(): ULong {
        hasRemaining(this, Buffer.ULONG_SIZE)
        val value = readULong()
        forwardPosition(this, Buffer.ULONG_SIZE)
        return value
    }

    override fun getNextFloat(): Float {
        hasRemaining(this, Buffer.FLOAT_SIZE)
        val value = readFloat()
        forwardPosition(this, Buffer.FLOAT_SIZE)
        return Float.fromBits(value)
    }

    override fun getNextDouble(): Double {
        hasRemaining(this, Buffer.DOUBLE_SIZE)
        val value = readDouble()
        forwardPosition(this, Buffer.DOUBLE_SIZE)
        return Double.fromBits(value)
    }

    internal abstract fun load(index: Int): UByte

    /**
     * Load one byte from underlying memory.
     *
     * @param index index in memory
     * @return a byte from memory
     */
    internal abstract fun loadByte(index: Int): Byte

    /**
     * Load one long from underlying memory.
     *
     * @param index index in memory
     * @return a long from memory
     */
    internal abstract fun loadLong(index: Int): Long

    override fun copyInto(destination: AbstractMutableBuffer, destinationOffset: Int, startIndex: Int, endIndex: Int) {
        if (destination == this)
            throw IllegalArgumentException("It's not allowed for a buffer to copy into itself.")
        if (0 > startIndex || startIndex > endIndex)
            throw IllegalArgumentException("Start index can not be negative or larger than the end index.")
        if (endIndex > this.size || endIndex - startIndex + destinationOffset > destination.size)
            throw BufferException("Cannot copy a range that is out of bounds.")

        when {
            !this.optimized and !destination.optimized -> copyNonOptimized(
                this, startIndex, destination, destinationOffset, endIndex - startIndex
            )
            this.optimized and destination.optimized -> copyFullyOptimized(
                this, startIndex, destination, destinationOffset, endIndex - startIndex
            )
            this.optimized or destination.optimized -> copySemiOptimized(
                this, startIndex, destination, destinationOffset, endIndex - startIndex
            )
        }
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

    internal abstract fun readFloat(): Int

    internal abstract fun readDouble(): Long

    companion object {
        internal inline fun remaining(buf: Buffer): Int {
            return buf.limit - buf.position + 1
        }

        internal inline fun hasRemaining(buf: Buffer, size: Int) {
            if (remaining(buf) <= size)
                throw ByteBufferOverflowWarning()
        }

        internal inline fun forwardPosition(buf: AbstractBuffer,length: Int) {
            buf._position += length
        }

        internal inline fun copyNonOptimized(
            src: AbstractBuffer, srcOffset: Int,
            dst: AbstractMutableBuffer, dstOffset: Int, length: Int,
        ) {
            for (index in 0 until length)
                dst.saveByte(dstOffset + index, src.loadByte(srcOffset + index))
        }

        internal inline fun copySemiOptimized(
            src: AbstractBuffer, srcOffset: Int,
            dst: AbstractMutableBuffer, dstOffset: Int, length: Int,
        ) = copyFullyOptimized(src, srcOffset, dst, dstOffset, length)

        internal inline fun copyFullyOptimized(
            src: AbstractBuffer, srcOffset: Int,
            dst: AbstractMutableBuffer, dstOffset: Int, length: Int,
        ) {
            val breakPoint = length - length % Buffer.LONG_SIZE
            for (index in 0 until breakPoint step Buffer.LONG_SIZE)
                dst.saveLong(dstOffset + index, src.loadLong(srcOffset + index))
            for (index in breakPoint until length)
                dst.saveByte(dstOffset + index, src.loadByte(srcOffset + index))
        }

        internal inline fun loadReadReverseShort(buf: AbstractBuffer, pos: Int): Int = buf.load(pos + 0).toInt() or
                (buf.load(pos + 1).toInt() shl 8)

        internal inline fun loadReadShort(buf: AbstractBuffer, pos: Int): Int = (buf.load(pos + 1).toInt() or
                (buf.load(pos + 0).toInt() shl 8))

        internal inline fun loadReadReverseInt(buf: AbstractBuffer, pos: Int): Int = buf.load(pos + 0).toInt() or
                (buf.load(pos + 1).toInt() shl 8) or
                (buf.load(pos + 2).toInt() shl 16) or
                (buf.load(pos + 3).toInt() shl 24)

        internal inline fun loadReadInt(buf: AbstractBuffer, pos: Int): Int = buf.load(pos + 3).toInt() or
                (buf.load(pos + 2).toInt() shl 8) or
                (buf.load(pos + 1).toInt() shl 16) or
                (buf.load(pos + 0).toInt() shl 24)

        internal inline fun loadReadReverseUInt(buf: AbstractBuffer, pos: Int): UInt = buf.load(pos + 0).toUInt() or
                (buf.load(pos + 1).toUInt() shl 8) or
                (buf.load(pos + 2).toUInt() shl 16) or
                (buf.load(pos + 3).toUInt() shl 24)

        internal inline fun loadReadUInt(buf: AbstractBuffer, pos: Int): UInt = buf.load(pos + 3).toUInt() or
                (buf.load(pos + 2).toUInt() shl 8) or
                (buf.load(pos + 1).toUInt() shl 16) or
                (buf.load(pos + 0).toUInt() shl 24)

        internal inline fun loadReadReverseLong(buf: AbstractBuffer, pos: Int): Long = buf.load(pos + 0).toLong() or
                (buf.load(pos + 1).toLong() shl 8) or
                (buf.load(pos + 2).toLong() shl 16) or
                (buf.load(pos + 3).toLong() shl 24) or
                (buf.load(pos + 4).toLong() shl 32) or
                (buf.load(pos + 5).toLong() shl 40) or
                (buf.load(pos + 6).toLong() shl 48) or
                (buf.load(pos + 7).toLong() shl 56)

        internal inline fun loadReadLong(buf: AbstractBuffer, pos: Int): Long = buf.load(pos + 7).toLong() or
                (buf.load(pos + 6).toLong() shl 8) or
                (buf.load(pos + 5).toLong() shl 16) or
                (buf.load(pos + 4).toLong() shl 24) or
                (buf.load(pos + 3).toLong() shl 32) or
                (buf.load(pos + 2).toLong() shl 40) or
                (buf.load(pos + 1).toLong() shl 48) or
                (buf.load(pos + 0).toLong() shl 56)

        internal inline fun loadReadReverseULong(buf: AbstractBuffer, pos: Int): ULong = buf.load(pos + 0).toULong() or
                (buf.load(pos + 1).toULong() shl 8) or
                (buf.load(pos + 2).toULong() shl 16) or
                (buf.load(pos + 3).toULong() shl 24) or
                (buf.load(pos + 4).toULong() shl 32) or
                (buf.load(pos + 5).toULong() shl 40) or
                (buf.load(pos + 6).toULong() shl 48) or
                (buf.load(pos + 7).toULong() shl 56)

        internal inline fun loadReadULong(buf: AbstractBuffer, pos: Int): ULong = buf.load(pos + 7).toULong() or
                (buf.load(pos + 6).toULong() shl 8) or
                (buf.load(pos + 5).toULong() shl 16) or
                (buf.load(pos + 4).toULong() shl 24) or
                (buf.load(pos + 3).toULong() shl 32) or
                (buf.load(pos + 2).toULong() shl 40) or
                (buf.load(pos + 1).toULong() shl 48) or
                (buf.load(pos + 0).toULong() shl 56)
    }
}