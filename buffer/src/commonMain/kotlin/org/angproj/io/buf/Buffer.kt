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
package org.angproj.io.buf

/**
 * Buffer interface in the Angelos system.
 *
 * @constructor Create empty Buffer
 */
interface Buffer {

    /**
     * Total size of the buffer.
     */
    val size: Int

    /**
     * Limitation of how far to operate into the buffer. Must never exceed the capacity.
     */
    val limit: Int

    /**
     * Endianness of the buffer.
     */
    var endian: Endianness

    /**
     * Whether reversed byte order of the buffer compared to native endianness.
     */
    val reverse: Boolean

    /**
     * Copy into a mutable buffer.
     *
     * @param destination destination mutable buffer to copy into
     * @param destinationOffset offset where to start inside mutable buffer
     * @param startIndex where to start copy from in source buffer
     * @param endIndex when to stop copying from the source buffer
     */
    fun copyInto(destination: MutableBuffer, destinationOffset: Int = 0, startIndex: Int = 0, endIndex: Int = limit) {
        Internals.copyInto(destination, destinationOffset, this, startIndex, endIndex)
    }

    fun getArray(): ByteArray

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

        /**
         * Endianness native to the platform.
         */
        val nativeEndianness = Endianness.nativeOrder()

        /**
         * Verifies the contracted requirements for Buffer.copyInto().
         *
         * @param destination destination buffer
         * @param destinationOffset offset in destination
         * @param source source buffer to copy from
         * @param startIndex start index to copy from at source
         * @param endIndex end index copy to copy from at source
         */
        inline fun copyIntoContract(
            destination: MutableBuffer, destinationOffset: Int,
            source: Buffer, startIndex: Int, endIndex: Int,
        ) {
            require(destinationOffset >= 0)
            require(startIndex >= 0)
            require(endIndex >= startIndex)
            require(destination.limit >= endIndex - startIndex + destinationOffset)
            require(source.limit >= endIndex)
        }
    }
}