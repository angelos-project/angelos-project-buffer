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
 * Writable interface to write data to a buffer.
 *
 * @constructor Create empty Writable
 */
interface Writable {
    /**
     * Write next byte.
     *
     * @param value a byte of data
     */
    fun setWriteByte(value: Byte)

    /**
     * Write next unsigned byte.
     *
     * @param value an unsigned byte of data
     */
    fun setWriteUByte(value: UByte)

    /**
     * Write next character.
     *
     * @param value a character of data
     */
    fun setWriteChar(value: Char)

    /**
     * Write next short integer.
     *
     * @param value a short integer of data.
     */
    fun setWriteShort(value: Short)

    /**
     * Write next unsigned short integer.
     *
     * @param value an unsigned short integer of data.
     */
    fun setWriteUShort(value: UShort)

    /**
     * Write next integer.
     *
     * @param value an integer of data
     */
    fun setWriteInt(value: Int)

    /**
     * Write next unsigned integer.
     *
     * @param value an unsigned integer of data
     */
    fun setWriteUInt(value: UInt)

    /**
     * Write next long integer.
     *
     * @param value a long integer of data
     */
    fun setWriteLong(value: Long)

    /**
     * Write next unsigned long integer.
     *
     * @param value an unsigned long integer of data
     */
    fun setWriteULong(value: ULong)

    /**
     * Write next float.
     *
     * @param value a float of data
     */
    fun setWriteFloat(value: Float)

    /**
     * Write next double.
     *
     * @param value a double of data
     */
    fun setWriteDouble(value: Double)
}