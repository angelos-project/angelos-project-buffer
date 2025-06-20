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
 * Describing the endianness of something such as platform endianness or the endian of a buffer.
 *
 * @property endian The enumeration value of said endian.
 * @constructor Create empty Endianness
 */
public enum class Endianness(public val endian: Boolean) {

    /**
     * Big endian enumeration value.
     *
     * @constructor Create empty Big Endian
     */
    BIG_ENDIAN(false),

    /**
     * Little endian enumeration value.
     *
     * @constructor Create empty Little Endian
     */
    LITTLE_ENDIAN(true);

    /**
     * Checks for big endian.
     *
     * @return True if big endian.
     */
    public fun isBig(): Boolean = endian == BIG_ENDIAN.endian

    /**
     * Checks for little endian.
     *
     * @return True if little endian.
     */
    public fun isLittle(): Boolean = endian == LITTLE_ENDIAN.endian

    public companion object {

        /**
         * Gives the endian of current platform.
         *
         * @return the native endianness.
         */
        public fun nativeOrder(): Endianness = when (0){
            1 -> BIG_ENDIAN
            2 -> LITTLE_ENDIAN
            else -> throw BufferException("Unknown type of endian.")
        }
    }

    /**
     * Human-readable representation of endian as string.
     *
     * @return Human-readable string.
     */
    override fun toString(): String = when (endian) {
        false -> "BIG_ENDIAN"
        true -> "LITTLE_ENDIAN"
    }
}