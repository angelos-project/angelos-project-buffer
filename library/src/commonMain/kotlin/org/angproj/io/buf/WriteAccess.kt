/**
 * Copyright (c) 2024-2025 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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
 * Interface for write access to a memory block.
 *
 * This interface provides methods to write primitive data types to a memory block at specified indices.
 * It is designed to be used with buffers that support random access to their contents.
 */
public interface WriteAccess {

    /**
     * Sets the value at the specified index to the given byte value.
     *
     * @param index The index at which to set the value.
     * @param value The byte value to set.
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    public fun setByte(index: Int, value: Byte): Unit

    /**
     * Sets the value at the specified index to the given short value.
     *
     * @param index The index at which to set the value.
     * @param value The short value to set.
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    public fun setShort(index: Int, value: Short): Unit

    /**
     * Sets the value at the specified index to the given integer value.
     *
     * @param index The index at which to set the value.
     * @param value The integer value to set.
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    public fun setInt(index: Int, value: Int): Unit

    /**
     * Sets the value at the specified index to the given long value.
     *
     * @param index The index at which to set the value.
     * @param value The long value to set.
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    public fun setLong(index: Int, value: Long): Unit
}