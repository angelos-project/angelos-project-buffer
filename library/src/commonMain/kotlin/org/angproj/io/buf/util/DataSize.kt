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
package org.angproj.io.buf.util

import kotlin.math.max


/**
 * Represents a data size in bytes, with predefined sizes that are powers of two.
 * The sizes range from 32 bytes to 1 gigabyte, with an UNKNOWN size for uninitialized or invalid states.
 *
 * @property capacity The size in bytes.
 */
public enum class DataSize(private val capacity: Int) {
    UNKNOWN(-1),
    _32B(32),
    _64B(_32B.capacity * 2),
    _128B(_64B.capacity * 2),
    _256B(_128B.capacity * 2),
    _512B(_256B.capacity * 2),
    _1K(_512B.capacity * 2),
    _2K(_1K.capacity * 2),
    _4K(_2K.capacity * 2),
    _8K(_4K.capacity * 2),
    _16K(_8K.capacity * 2),
    _32K(_16K.capacity * 2),
    _64K(_32K.capacity * 2),
    _128K(_64K.capacity * 2),
    _256K(_128K.capacity * 2),
    _512K(_256K.capacity * 2),
    _1M(_512K.capacity * 2),
    _2M(_1M.capacity * 2),
    _4M(_2M.capacity * 2),
    _8M(_4M.capacity * 2),
    _16M(_8M.capacity * 2),
    _32M(_16M.capacity * 2),
    _64M(_32M.capacity * 2),
    _128M(_64M.capacity * 2),
    _256M(_128M.capacity * 2),
    _512M(_256M.capacity * 2),
    _1G(_512M.capacity * 2);

    /**
     * Converts the [DataSize] to an integer representing the size in bytes.
     *
     * @return The size in bytes as an [Int].
     */
    public fun toInt(): Int = capacity

    /**
     * Converts the [DataSize] to a long representing the size in bytes.
     *
     * @return The size in bytes as a [Long].
     */
    public fun toLong(): Long = capacity.toLong()

    /**
     * Checks if the [DataSize] is unknown.
     *
     * @return true if the size is UNKNOWN, false otherwise.
     */
    public fun isUnknown(): Boolean = this == UNKNOWN

    public companion object {

        /**
         * Finds the lowest [DataSize] that is greater than or equal to the specified size.
         *
         * @param size The size in bytes to compare against.
         * @return The [DataSize] that is the lowest size above the specified size.
         * @throws IllegalArgumentException if the size is not within the valid range (0 to 1GB).
         */
        public fun findLowestAbove(size: Int): DataSize {
            require(size in 0.._1G.capacity) { "Invalid range" }
            val real = if(size.countOneBits() == 1 && size > 17) size else 1 shl(32 - max(size, 31).countLeadingZeroBits())
            return entries.find { it.capacity == real } ?: error("Invalid")
        }
    }
}

