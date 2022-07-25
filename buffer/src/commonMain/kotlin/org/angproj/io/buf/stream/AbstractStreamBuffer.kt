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

import org.angproj.io.buf.*

abstract class AbstractStreamBuffer(size: Int, limit: Int, position: Int, endianness: Endianness) : AbstractBuffer(
    size,
    limit,
    endianness
), StreamBuffer, Readable {
    internal var _position: Int = position
    override val position: Int
        get() = _position

    init {
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

    override fun getReadByte(): Byte {
        hasRemaining(this, Buffer.BYTE_SIZE)
        val value = readByte()
        forwardPosition(this, Buffer.BYTE_SIZE)
        return value
    }

    override fun getReadUByte(): UByte {
        hasRemaining(this, Buffer.UBYTE_SIZE)
        val value = readUByte()
        forwardPosition(this, Buffer.UBYTE_SIZE)
        return value
    }

    override fun getReadChar(): Char {
        hasRemaining(this, Buffer.CHAR_SIZE)
        val value = readChar()
        forwardPosition(this, Buffer.CHAR_SIZE)
        return value
    }

    override fun getReadShort(): Short {
        hasRemaining(this, Buffer.SHORT_SIZE)
        val value = readShort()
        forwardPosition(this, Buffer.SHORT_SIZE)
        return value
    }

    override fun getReadUShort(): UShort {
        hasRemaining(this, Buffer.USHORT_SIZE)
        val value = readUShort()
        forwardPosition(this, Buffer.USHORT_SIZE)
        return value
    }

    override fun getReadInt(): Int {
        hasRemaining(this, Buffer.INT_SIZE)
        val value = readInt()
        forwardPosition(this, Buffer.INT_SIZE)
        return value
    }

    override fun getReadUInt(): UInt {
        hasRemaining(this, Buffer.UINT_SIZE)
        val value = readUInt()
        forwardPosition(this, Buffer.UINT_SIZE)
        return value
    }

    override fun getReadLong(): Long {
        hasRemaining(this, Buffer.LONG_SIZE)
        val value = readLong()
        forwardPosition(this, Buffer.LONG_SIZE)
        return value
    }

    override fun getReadULong(): ULong {
        hasRemaining(this, Buffer.ULONG_SIZE)
        val value = readULong()
        forwardPosition(this, Buffer.ULONG_SIZE)
        return value
    }

    override fun getReadFloat(): Float {
        hasRemaining(this, Buffer.FLOAT_SIZE)
        val value = readFloat()
        forwardPosition(this, Buffer.FLOAT_SIZE)
        return value
    }

    override fun getReadDouble(): Double {
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
    open fun copyInto(destination: AbstractMutableStreamBuffer, destinationOffset: Int, startIndex: Int, endIndex: Int) {
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

        internal inline fun remaining(buf: StreamBuffer): Int {
            return buf.limit - buf.position + 1
        }

        internal inline fun hasRemaining(buf: StreamBuffer, size: Int) {
            if (remaining(buf) <= size)
                throw BufferOverflowWarning()
        }

        internal inline fun forwardPosition(buf: AbstractStreamBuffer, length: Int) {
            buf._position += length
        }
    }
}