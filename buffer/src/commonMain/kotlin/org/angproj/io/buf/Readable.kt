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
 * Readable interface to read data from a buffer.
 *
 * @constructor Create empty Readable
 */
interface Readable {
    /**
     * Read next byte.
     *
     * @return a byte of data
     */
    fun getReadByte(): Byte

    /**
     * Read next unsigned byte.
     *
     * @return an unsigned byte of data
     */
    fun getReadUByte(): UByte

    /**
     * Read next character.
     *
     * @return a character of data
     */
    fun getReadChar(): Char

    /**
     * Read next short integer.
     *
     * @return a short integer of data
     */
    fun getReadShort(): Short

    /**
     * Read next unsigned short integer.
     *
     * @return an unsigned short integer of data
     */
    fun getReadUShort(): UShort

    /**
     * Read next integer.
     *
     * @return an integer of data
     */
    fun getReadInt(): Int

    /**
     * Read next unsigned integer.
     *
     * @return an unsigned integer of data
     */
    fun getReadUInt(): UInt

    /**
     * Read next long integer.
     *
     * @return a long integer of data.
     */
    fun getReadLong(): Long

    /**
     * Read next unsigned long integer.
     *
     * @return an unsigned long integer of data
     */
    fun getReadULong(): ULong

    /**
     * Read next float.
     *
     * @return a float of data
     */
    fun getReadFloat(): Float

    /**
     * Read next double.
     *
     * @return a double of data
     */
    fun getReadDouble(): Double
}