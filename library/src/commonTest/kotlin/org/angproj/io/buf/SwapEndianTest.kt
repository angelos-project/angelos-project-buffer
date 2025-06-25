/**
 * Copyright (c) 2022-2025 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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

import org.angproj.io.buf.TestInformationStub.refDouble
import org.angproj.io.buf.TestInformationStub.refFloat
import org.angproj.io.buf.TestInformationStub.refInt
import org.angproj.io.buf.TestInformationStub.refLong
import org.angproj.io.buf.TestInformationStub.refShort
import org.angproj.io.buf.TestInformationStub.refUInt
import org.angproj.io.buf.TestInformationStub.refULong
import org.angproj.io.buf.TestInformationStub.refUShort
import org.angproj.io.buf.util.UtilityAware
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Testing .swapEndian() on most variable types.
 *
 * @constructor Create empty Swap endian test
 */
class SwapEndianTest : UtilityAware {

    /**
     * Testing .swapEndian() on Short.
     *
     */
    @Test
    fun shortSwapEndian() {
        assertEquals(refShort.swapEndian().swapEndian(), refShort)
    }

    /**
     * Testing .swapEndian() on UShort.
     *
     */
    @Test
    fun ushortSwapEndian() {
        assertEquals(refUShort.swapEndian().swapEndian(), refUShort)
    }

    /**
     * Testing .swapEndian() on Int.
     *
     */
    @Test
    fun intSwapEndian() {
        assertEquals(refInt.swapEndian().swapEndian(), refInt)
    }

    /**
     * Testing .swapEndian() on UInt.
     *
     */
    @Test
    fun uintSwapEndian() {
        assertEquals(refUInt.swapEndian().swapEndian(), refUInt)
    }

    /**
     * Testing .swapEndian() on Long.
     *
     */
    @Test
    fun longSwapEndian() {
        assertEquals(refLong.swapEndian().swapEndian(), refLong)
    }

    /**
     * Testing .swapEndian() on ULong.
     *
     */
    @Test
    fun ulongSwapEndian() {
        assertEquals(refULong.swapEndian().swapEndian(), refULong)
    }

    /**
     * Testing .swapEndian() on Float.
     *
     */
    @Test
    fun floatSwapEndian() {
        assertEquals(refFloat.swapEndian().swapEndian().toRawBits(), refFloat.toRawBits())
    }

    /**
     * Testing .swapEndian() on Double.
     *
     */
    @Test
    fun doubleSwapEndian() {
        assertEquals(refDouble.swapEndian().swapEndian(), refDouble)
    }
}