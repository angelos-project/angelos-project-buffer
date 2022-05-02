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

import kotlin.test.Test
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

open class AbstractEndianTest {
    @Test
    fun isBig() {
        val endianness = Endianness.BIG_ENDIAN
        assertTrue(endianness.isBig())
    }

    @Test
    fun isLittle() {
        val endianness = Endianness.LITTLE_ENDIAN
        assertTrue(endianness.isLittle())
    }

    @Test
    fun testToString() {
        val endianness = Endianness.nativeOrder()
        assertNotEquals(endianness.toString(), "UNKNOWN_ENDIAN")
    }
}

expect class EndiannessTest : AbstractEndianTest {
    @Test
    fun nativeOrder()
}