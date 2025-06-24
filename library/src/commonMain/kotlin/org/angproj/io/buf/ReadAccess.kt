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

import org.angproj.io.buf.seg.SegmentException

/**
 * Interface for read access to a memory block.
 *
 * This interface provides methods to read primitive data types from a memory block at specified indices.
 * It is designed to be used with buffers that support random access to their contents.
 */
public interface ReadAccess {

    /**
     * Returns the byte at the specified index.
     *
     * @param index the index of the byte to return
     * @return the byte at the specified index
     * @throws SegmentException if the index is out of bounds
     */
    public  fun getByte(index: Int): Byte

    /**
     * Returns the short at the specified index.
     *
     * @param index the index of the short to return
     * @return the short at the specified index
     * @throws SegmentException if the index is out of bounds
     */
    public fun getShort(index: Int): Short

    /**
     * Returns the int at the specified index.
     *
     * @param index the index of the int to return
     * @return the int at the specified index
     * @throws SegmentException if the index is out of bounds
     */
    public fun getInt(index: Int): Int

    /**
     * Returns the long at the specified index.
     *
     * @param index the index of the long to return
     * @return the long at the specified index
     * @throws SegmentException if the index is out of bounds
     */
    public fun getLong(index: Int): Long
}