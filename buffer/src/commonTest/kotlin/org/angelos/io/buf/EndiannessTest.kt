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

/**
 * Containing common tests for Endianness.
 *
 * @constructor Create empty Abstract endian test
 */
open class AbstractEndianTest {

    /**
     * Running a test for isBig().
     *
     */
    @Test
    fun isBig() {
        val endianness = Endianness.BIG_ENDIAN
        assertTrue(endianness.isBig())
    }

    /**
     * Running a test for isLittle().
     *
     */
    @Test
    fun isLittle() {
        val endianness = Endianness.LITTLE_ENDIAN
        assertTrue(endianness.isLittle())
    }

    /**
     * Runing a test for toString().
     *
     */
    @Test
    fun testToString() {
        val endianness = Endianness.nativeOrder()
        assertNotEquals(endianness.toString(), "UNKNOWN_ENDIAN")
    }
}

/**
 * Running tests for Endianness.
 *
 * @constructor Create empty Endianness test
 */
expect class EndiannessTest : AbstractEndianTest {

    /**
     * Testing the native order compared to the real running environment.
     */
    @Test
    fun nativeOrder()
}