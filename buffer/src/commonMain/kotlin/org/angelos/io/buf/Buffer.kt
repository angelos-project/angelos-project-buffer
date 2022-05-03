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
 * Buffer interface in the Angelos system.
 *
 * @constructor Create empty Buffer
 */
interface Buffer: Gettable {

    /**
     * Total size of the buffer.
     */
    val size: Int

    /**
     * Limitation of how far to operate into the buffer. Must never exceed the capacity.
     */
    val limit: Int

    /**
     * Current position for operations in the buffer. Must never exceed the limit.
     */
    val position: Int

    /**
     * Endianness of the buffer.
     */
    var endian: Endianness

    /**
     * Whether reversed byte order of the buffer compared to native endianness.
     */
    val reverse: Boolean

    /**
     * Whether the current implementation of the class is optimized.
     * Used internally to make the most efficient decisions when copying data between buffers.
     */
    val optimized: Boolean

    /**
     * Clears the buffer by setting position to beginning and limit to capacity, for performing
     * a reuse of the buffer for operations from scratch.
     */
    fun clear()

    /**
     * Flips the buffer by setting limit to the current position, and then setting the position
     * to the beginning, for performing getting operations after setting values.
     *
     */
    fun flip()

    /**
     * Rewinds the buffer by setting position to the beginning without touching the limit, for
     * rereading operations on the buffer.
     */
    fun rewind()

    /**
     * Remaining space between the current position and limit.
     *
     * @return number of bytes remaining
     */
    fun remaining(): Int = remaining(this)

    /**
     * Has enough remaining bytes left or throws BufferException.
     *
     * @param size needed space
     */
    fun hasRemaining(size: Int) = hasRemaining(this, size)

    /**
     * Copy into a mutable buffer.
     *
     * @param destination destination mutable buffer to copy into
     * @param destinationOffset offset where to start inside mutable buffer
     * @param startIndex where to start copy from in source buffer
     * @param endIndex when to stop copying from the source buffer
     */
    fun copyInto(destination: MutableBuffer, destinationOffset: Int = 0, startIndex: Int = 0, endIndex: Int = size) {
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

    /**
     * Load one byte from underlying memory.
     *
     * @param index index in memory
     * @return a byte from memory
     */
    fun loadByte(index: Int): Byte

    /**
     * Load one long from underlying memory.
     *
     * @param index index in memory
     * @return a long from memory
     */
    fun loadLong(index: Int): Long

    companion object {
        const val BYTE_SIZE = Byte.SIZE_BYTES
        const val UBYTE_SIZE = UByte.SIZE_BYTES
        const val CHAR_SIZE = Char.SIZE_BYTES
        const val SHORT_SIZE = Short.SIZE_BYTES
        const val USHORT_SIZE = UShort.SIZE_BYTES
        const val INT_SIZE = Int.SIZE_BYTES
        const val UINT_SIZE = UInt.SIZE_BYTES
        const val LONG_SIZE = Long.SIZE_BYTES
        const val ULONG_SIZE = ULong.SIZE_BYTES
        const val FLOAT_SIZE = Float.SIZE_BYTES
        const val DOUBLE_SIZE = Double.SIZE_BYTES

        val nativeEndianness = Endianness.nativeOrder()

        internal inline fun remaining(buf: Buffer): Int {
            return buf.limit - buf.position + 1
        }

        internal inline fun hasRemaining(buf: Buffer, size: Int) {
            if (remaining(buf) <= size)
                throw BufferException("Not enough space left over in buffer, needs $size bytes")
        }

        internal inline fun copyNonOptimized(
            src: Buffer, srcOffset: Int,
            dst: MutableBuffer, dstOffset: Int, length: Int
        ) {
            for (index in 0 until length)
                dst.saveByte(dstOffset + index, src.loadByte(srcOffset + index))
        }

        internal inline fun copySemiOptimized(
            src: Buffer, srcOffset: Int,
            dst: MutableBuffer, dstOffset: Int, length: Int
        ) = copyFullyOptimized(src, srcOffset, dst, dstOffset, length)

        internal inline fun copyFullyOptimized(
            src: Buffer, srcOffset: Int,
            dst: MutableBuffer, dstOffset: Int, length: Int
        ) {
            val breakPoint = length - length % LONG_SIZE
            for (index in 0 until breakPoint step LONG_SIZE)
                dst.saveLong(dstOffset + index, src.loadLong(srcOffset + index))
            for (index in breakPoint until length)
                dst.saveByte(dstOffset + index, src.loadByte(srcOffset + index))
        }
    }
}