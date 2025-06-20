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
 * Storable interface for saving data to any type of storable interface including buffers.
 *
 * @constructor Create Storable
 */
public interface Storable {
    /**
     * Store a byte unto said position.
     *
     * @param value A byte of data.
     */
    public fun setStoreByte(position: Int, value: Byte)

    /**
     * Store a unsigned byte unto said position.
     *
     * @param value An unsigned byte of data.
     */
    public fun setStoreUByte(position: Int, value: UByte)

    /**
     * Store character unto said position.
     *
     * @param value A character of data.
     */
    public fun setStoreChar(position: Int, value: Char)

    /**
     * Store a short integer unto said position.
     *
     * @param value A short integer of data.
     */
    public fun setStoreShort(position: Int, value: Short)

    /**
     * Store an unsigned short integer unto said position.
     *
     * @param value An unsigned short integer of data.
     */
    public fun setStoreUShort(position: Int, value: UShort)

    /**
     * Store an integer unto said position.
     *
     * @param value An integer of data.
     */
    public fun setStoreInt(position: Int, value: Int)

    /**
     * Store an unsigned integer unto said position.
     *
     * @param value An unsigned integer of data.
     */
    public fun setStoreUInt(position: Int, value: UInt)

    /**
     * Store a long integer unto said position.
     *
     * @param value A long integer of data.
     */
    public fun setStoreLong(position: Int, value: Long)

    /**
     * Store an unsigned long integer unto said position.
     *
     * @param value An unsigned long integer of data.
     */
    public fun setStoreULong(position: Int, value: ULong)

    /**
     * Store a float unto said position.
     *
     * @param value A float of data.
     */
    public fun setStoreFloat(position: Int, value: Float)

    /**
     * Store a double unto said position.
     *
     * @param value A double of data.
     */
    public fun setStoreDouble(position: Int, value: Double)
}