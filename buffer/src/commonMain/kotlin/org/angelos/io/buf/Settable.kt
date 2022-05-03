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
 * Settable interface to write data to a stream or buffer.
 *
 * @constructor Create empty Settable
 */
interface Settable {
    /**
     * Set next byte.
     *
     * @param value a byte of data
     */
    fun setNextByte(value: Byte)

    /**
     * Set next unsigned byte.
     *
     * @param value an unsigned byte of data
     */
    fun setNextUByte(value: UByte)

    /**
     * Set next character.
     *
     * @param value a character of data
     */
    fun setNextChar(value: Char)

    /**
     * Set next short integer.
     *
     * @param value a short integer of data.
     */
    fun setNextShort(value: Short)

    /**
     * Set next unsigned short integer.
     *
     * @param value an unsigned short integer of data.
     */
    fun setNextUShort(value: UShort)

    /**
     * Set next integer.
     *
     * @param value an integer of data
     */
    fun setNextInt(value: Int)

    /**
     * Set next unsigned integer.
     *
     * @param value an unsigned integer of data
     */
    fun setNextUInt(value: UInt)

    /**
     * Set next long integer.
     *
     * @param value a long integer of data
     */
    fun setNextLong(value: Long)

    /**
     * Set next unsigned long integer.
     *
     * @param value an unsigned long integer of data
     */
    fun setNextULong(value: ULong)

    /**
     * Set next float.
     *
     * @param value a float of data
     */
    fun setNextFloat(value: Float)

    /**
     * Set next double.
     *
     * @param value a double of data
     */
    fun setNextDouble(value: Double)
}