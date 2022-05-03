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
 * Gettable interface to read data from a stream or buffer.
 *
 * @constructor Create empty Gettable
 */
interface Gettable {
    /**
     * Get next byte.
     *
     * @return a byte of data
     */
    fun getNextByte(): Byte

    /**
     * Get next unsigned byte.
     *
     * @return an unsigned byte of data
     */
    fun getNextUByte(): UByte

    /**
     * Get next character.
     *
     * @return a character of data
     */
    fun getNextChar(): Char

    /**
     * Get next short integer.
     *
     * @return a short integer of data
     */
    fun getNextShort(): Short

    /**
     * Get next unsigned short integer.
     *
     * @return an unsigned short integer of data
     */
    fun getNextUShort(): UShort

    /**
     * Get next integer.
     *
     * @return an integer of data
     */
    fun getNextInt(): Int

    /**
     * Get next unsigned integer.
     *
     * @return an unsigned integer of data
     */
    fun getNextUInt(): UInt

    /**
     * Get next long integer.
     *
     * @return a long integer of data.
     */
    fun getNextLong(): Long

    /**
     * Get next unsigned long integer.
     *
     * @return an unsigned long integer of data
     */
    fun getNextULong(): ULong

    /**
     * Get next float.
     *
     * @return a float of data
     */
    fun getNextFloat(): Float

    /**
     * Get next double.
     *
     * @return a double of data
     */
    fun getNextDouble(): Double
}