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
 * Writable interface for writing data to any type of writable interface including buffers.
 *
 * @constructor Create Writable
 */
public interface Writable {
    /**
     * Write next byte.
     *
     * @param value A byte of data.
     */
    public fun setWriteByte(value: Byte)

    /**
     * Write next unsigned byte.
     *
     * @param value An unsigned byte of data.
     */
    public fun setWriteUByte(value: UByte)

    /**
     * Write next character.
     *
     * @param value A character of data.
     */
    public fun setWriteChar(value: Char)

    /**
     * Write next short integer.
     *
     * @param value A short integer of data.
     */
    public fun setWriteShort(value: Short)

    /**
     * Write next unsigned short integer.
     *
     * @param value An unsigned short integer of data.
     */
    public fun setWriteUShort(value: UShort)

    /**
     * Write next integer.
     *
     * @param value An integer of data.
     */
    public fun setWriteInt(value: Int)

    /**
     * Write next unsigned integer.
     *
     * @param value An unsigned integer of data.
     */
    public fun setWriteUInt(value: UInt)

    /**
     * Write next long integer.
     *
     * @param value A long integer of data.
     */
    public fun setWriteLong(value: Long)

    /**
     * Write next unsigned long integer.
     *
     * @param value An unsigned long integer of data.
     */
    public fun setWriteULong(value: ULong)

    /**
     * Write next float.
     *
     * @param value A float of data.
     */
    public fun setWriteFloat(value: Float)

    /**
     * Write next double.
     *
     * @param value A double of data.
     */
    public fun setWriteDouble(value: Double)
}