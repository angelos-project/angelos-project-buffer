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
 * Representing endianness in systems and data buffers or streams.
 *
 * @property endian
 * @constructor Create empty Endianness
 */
enum class Endianness(val endian: Boolean) {

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
     * Checks if the endianness is big.
     *
     * @return
     */
    fun isBig(): Boolean = endian == BIG_ENDIAN.endian

    /**
     * Checks if the endianness is little.
     *
     * @return
     */
    fun isLittle(): Boolean = endian == LITTLE_ENDIAN.endian

    companion object {

        /**
         * Native order of the running environment.
         *
         * @return the endian
         */
        fun nativeOrder(): Endianness = when (Internals.getEndian()) {
            1 -> BIG_ENDIAN
            2 -> LITTLE_ENDIAN
            else -> throw BufferException("Unknown type of endian.")
        }
    }

    /**
     * String representation of the endianness.
     *
     * @return human readable endianness
     */
    override fun toString(): String = when (endian) {
        false -> "BIG_ENDIAN"
        true -> "LITTLE_ENDIAN"
    }
}