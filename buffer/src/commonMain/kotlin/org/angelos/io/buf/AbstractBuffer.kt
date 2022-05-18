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
    override val size: Int = size

    private var _limit: Int = limit
    override val limit: Int
        get() = _limit

    internal var _position: Int = position
    override val position: Int
        get() = _position

    private var _endian: Endianness = endianness
    override var endian: Endianness
        get() = _endian
        set(value) {
            _endian = value
            _reverse = _endian != Buffer.nativeEndianness
        }

    private var _reverse: Boolean = _endian != Buffer.nativeEndianness
    override val reverse: Boolean
        get() = _reverse

    init {
        require(size >= limit)
        require(limit >= position)
        require(position >= 0)
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
        return value
    }

    override fun getNextDouble(): Double {
        hasRemaining(this, Buffer.DOUBLE_SIZE)
        val value = readDouble()
        forwardPosition(this, Buffer.DOUBLE_SIZE)
        return value
    }

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

    /**
     * Copy into for buffers implemented in this package.
     * Targets may override this implementation by a specialized one.
     *
     * @param destination
     * @param destinationOffset
     * @param startIndex
     * @param endIndex
     */
    open fun copyInto(destination: AbstractMutableBuffer, destinationOffset: Int, startIndex: Int, endIndex: Int) {
        Buffer.copyIntoContract(destination, destinationOffset, this, startIndex, endIndex)

        val length = endIndex - startIndex

        val l = length.floorDiv(Buffer.LONG_SIZE) * Buffer.LONG_SIZE

        for (idx in 0 until l step Buffer.LONG_SIZE) {
            destination.saveLong(destinationOffset + idx, this.loadLong(startIndex + idx))
        }

        for (idx in l until length) {
            destination.saveByte(destinationOffset + idx, this.loadByte(startIndex + idx))
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

    internal abstract fun readFloat(): Float

    internal abstract fun readDouble(): Double

    companion object {
        internal inline fun remaining(buf: Buffer): Int {
            return buf.limit - buf.position + 1
        }

        internal inline fun hasRemaining(buf: Buffer, size: Int) {
            if (remaining(buf) <= size)
                throw ByteBufferOverflowWarning()
        }

        internal inline fun forwardPosition(buf: AbstractBuffer, length: Int) {
            buf._position += length
        }
    }
}