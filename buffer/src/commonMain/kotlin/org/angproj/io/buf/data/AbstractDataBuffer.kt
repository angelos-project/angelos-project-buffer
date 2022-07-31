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

import org.angproj.io.buf.AbstractBuffer
import org.angproj.io.buf.Buffer
import org.angproj.io.buf.BufferOverflowWarning
import org.angproj.io.buf.Endianness

/**
 * Abstract data buffer base class.
 *
 * @constructor
 *
 * @param size Full size of buffer
 * @param limit Limit lesser than size
 * @param endianness Endianness
 */
abstract class AbstractDataBuffer internal constructor(
    size: Int,
    limit: Int,
    endianness: Endianness,
) : AbstractBuffer(size, limit, endianness), DataBuffer {
    override fun limit(l: Int) {
        check(size >= limit)
        _limit = limit
    }

    override fun remaining(position: Int): Int = remaining(this, position)

    override fun hasRemaining(position: Int, size: Int) = hasRemaining(this, position, size)

    override fun getRetrieveByte(position: Int): Byte {
        hasRemaining(this, position, Buffer.BYTE_SIZE)
        return readByte(position)
    }

    override fun getRetrieveUByte(position: Int): UByte {
        hasRemaining(this, position, Buffer.UBYTE_SIZE)
        return readUByte(position)
    }

    override fun getRetrieveChar(position: Int): Char {
        hasRemaining(this, position, Buffer.CHAR_SIZE)
        return readChar(position)
    }

    override fun getRetrieveShort(position: Int): Short {
        hasRemaining(this, position, Buffer.SHORT_SIZE)
        return readShort(position)
    }

    override fun getRetrieveUShort(position: Int): UShort {
        hasRemaining(this, position, Buffer.USHORT_SIZE)
        return readUShort(position)
    }

    override fun getRetrieveInt(position: Int): Int {
        hasRemaining(this, position, Buffer.INT_SIZE)
        return readInt(position)
    }

    override fun getRetrieveUInt(position: Int): UInt {
        hasRemaining(this, position, Buffer.UINT_SIZE)
        return readUInt(position)
    }

    override fun getRetrieveLong(position: Int): Long {
        hasRemaining(this, position, Buffer.LONG_SIZE)
        return readLong(position)
    }

    override fun getRetrieveULong(position: Int): ULong {
        hasRemaining(this, position, Buffer.ULONG_SIZE)
        return readULong(position)
    }

    override fun getRetrieveFloat(position: Int): Float {
        hasRemaining(this, position, Buffer.FLOAT_SIZE)
        return readFloat(position)
    }

    override fun getRetrieveDouble(position: Int): Double {
        hasRemaining(this, position, Buffer.DOUBLE_SIZE)
        return readDouble(position)
    }

    internal abstract fun readByte(position: Int): Byte

    internal abstract fun readUByte(position: Int): UByte

    internal abstract fun readChar(position: Int): Char

    internal abstract fun readShort(position: Int): Short

    internal abstract fun readUShort(position: Int): UShort

    internal abstract fun readInt(position: Int): Int

    internal abstract fun readUInt(position: Int): UInt

    internal abstract fun readLong(position: Int): Long

    internal abstract fun readULong(position: Int): ULong

    internal abstract fun readFloat(position: Int): Float

    internal abstract fun readDouble(position: Int): Double

    companion object {
        internal inline fun remaining(buf: DataBuffer, position: Int): Int {
            return buf.limit - position + 1
        }

        internal inline fun hasRemaining(buf: DataBuffer, position: Int, size: Int) {
            if (remaining(buf, position) <= size)
                throw BufferOverflowWarning()
        }
    }
}