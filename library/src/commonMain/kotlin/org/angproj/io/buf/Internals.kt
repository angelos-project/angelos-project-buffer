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

internal expect class Internals {
    companion object {
        fun getEndian(): Int

        fun copyInto(destination: MutableBuffer, destinationOffset: Int, source: Buffer, startIndex: Int, endIndex: Int)

        fun reset(destination: MutableBuffer)
    }
}

/**
 * Reverse byte order of Short.
 *
 * @param value
 * @return
 */
public fun reverseShort(value: Short): Short = (
        (value.toInt() shl 8 and 0xFF00) or (value.toInt() shr 8 and 0xFF)).toShort()

/**
 * Reverse byte order of Int.
 *
 * @param value
 * @return
 */
public fun reverseInt(value: Int): Int = (value shl 24 and -0x1000000) or
        (value shl 8 and 0xFF0000) or
        (value shr 8 and 0xFF00) or
        (value shr 24 and 0xFF)

/**
 * Reverse byte order of Long.
 *
 * @param value
 * @return
 */
public fun reverseLong(value: Long): Long = (value shl 56 and -0x1000000_00000000) or
        (value shl 40 and 0xFF0000_00000000) or
        (value shl 24 and 0xFF00_00000000) or
        (value shl 8 and 0xFF_00000000) or
        (value shr 8 and 0xFF000000) or
        (value shr 24 and 0xFF0000) or
        (value shr 40 and 0xFF00) or
        (value shr 56 and 0xFF)