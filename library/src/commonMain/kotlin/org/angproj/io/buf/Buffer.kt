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
 * The buffer interface used for all buffers in Angelos™.
 *
 * @constructor Create empty Buffer
 */
public interface Buffer {

    /**
     * Total size of the buffer.
     */
    public val size: Int

    /**
     * Limitation of how far to operate into the buffer. Must never exceed the size.
     */
    public val limit: Int

    /**
     * The current endianness of the buffer.
     */
    public var endian: Endianness

    /**
     * Whether the buffer endianness is reversed contrary to the platform native endianness of the operating system.
     */
    public val reverse: Boolean

    /**
     * Copy this buffer into another said mutable buffer.
     *
     * @param destination The destination buffer to which copying takes place.
     * @param destinationOffset The offset where the copying starts.
     * @param startIndex Start copying from this index of current buffer.
     * @param endIndex End copying at this index of current buffer.
     */
    public fun copyInto(destination: MutableBuffer, destinationOffset: Int = 0, startIndex: Int = 0, endIndex: Int = limit) {
        Internals.copyInto(destination, destinationOffset, this, startIndex, endIndex)
    }

    /**
     * Not for external API usage, may throw UnsupportedOperationException.
     *
     * @return the actual ByteBuffer if any.
     */
    public fun getArray(): ByteArray

    public companion object {
        public const val BYTE_SIZE: Int = Byte.SIZE_BYTES
        public const val UBYTE_SIZE: Int = UByte.SIZE_BYTES
        public const val CHAR_SIZE: Int = Char.SIZE_BYTES
        public const val SHORT_SIZE: Int = Short.SIZE_BYTES
        public const val USHORT_SIZE: Int = UShort.SIZE_BYTES
        public const val INT_SIZE: Int = Int.SIZE_BYTES
        public const val UINT_SIZE: Int = UInt.SIZE_BYTES
        public const val LONG_SIZE: Int = Long.SIZE_BYTES
        public const val ULONG_SIZE: Int = ULong.SIZE_BYTES
        public const val FLOAT_SIZE: Int = Float.SIZE_BYTES
        public const val DOUBLE_SIZE: Int = Double.SIZE_BYTES

        /**
         * Endianness native to the platform.
         */
        public val nativeEndianness: Endianness = Endianness.nativeOrder()

        /**
         * Verifies the contracted requirements for Buffer.copyInto().
         *
         * @param destination destination buffer
         * @param destinationOffset offset in destination
         * @param source source buffer to copy from
         * @param startIndex start index to copy from at source
         * @param endIndex end index copy to copy from at source
         */
        public fun copyIntoContract(
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