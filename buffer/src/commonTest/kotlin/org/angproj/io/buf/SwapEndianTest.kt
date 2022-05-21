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

import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Testing .swapEndian() on most variable types.
 *
 * @constructor Create empty Swap endian test
 */
class SwapEndianTest {

    private val refChar: Char = 'Ö'

    private val refShort: Short = 0B1010101_10101010
    private val refUShort: UShort = 0B10101010_10101010u

    private val refInt: Int = 0B1010101_10101010_10101010_10101010
    private val refUInt: UInt = 0B10101010_10101010_10101010_10101010u

    private val refLong: Long = 0B1010101_10101010_10101010_10101010_10101010_10101010_10101010_10101010L
    private val refULong: ULong = 0B10101010_10101010_10101010_10101010_10101010_10101010_10101010_10101010u

    private val refFloat: Float = 23.43585F
    private val refDouble: Double = -0.892384774029876

    /**
     * Testing .swapEndian() on Short.
     *
     */
    @Test
    fun shortSwapEndian() {
        assertEquals(refShort.swapEndian().swapEndian(), refShort)
        /*assertEquals(Short.MIN_VALUE.swapEndian().swapEndian(), Short.MIN_VALUE)
        assertEquals(Short.MAX_VALUE.swapEndian().swapEndian(), Short.MAX_VALUE)*/
    }

    /**
     * Testing .swapEndian() on UShort.
     *
     */
    @Test
    fun ushortSwapEndian() {
        assertEquals(refUShort.swapEndian().swapEndian(), refUShort)
        /*assertEquals(UShort.MIN_VALUE.swapEndian().swapEndian(), UShort.MIN_VALUE)
        assertEquals(UShort.MAX_VALUE.swapEndian().swapEndian(), UShort.MAX_VALUE)*/
    }

    /**
     * Testing .swapEndian() on Char.
     *
     */
    @Test
    fun charSwapEndian() {
        assertEquals(refChar.swapEndian().swapEndian(), refChar)
        /*assertEquals(Char.MIN_VALUE.swapEndian().swapEndian(), Char.MIN_VALUE)
        assertEquals(Char.MAX_VALUE.swapEndian().swapEndian(), Char.MAX_VALUE)*/
    }

    /**
     * Testing .swapEndian() on Int.
     *
     */
    @Test
    fun intSwapEndian() {
        assertEquals(refInt.swapEndian().swapEndian(), refInt)
        /*assertEquals(Int.MIN_VALUE.swapEndian().swapEndian(), Int.MIN_VALUE)
        assertEquals(Int.MAX_VALUE.swapEndian().swapEndian(), Int.MAX_VALUE)*/
    }

    /**
     * Testing .swapEndian() on UInt.
     *
     */
    @Test
    fun uintSwapEndian() {
        assertEquals(refUInt.swapEndian().swapEndian(), refUInt)
        /*assertEquals(UInt.MIN_VALUE.swapEndian().swapEndian(), UInt.MIN_VALUE)
        assertEquals(UInt.MAX_VALUE.swapEndian().swapEndian(), UInt.MAX_VALUE)*/
    }

    /**
     * Testing .swapEndian() on Long.
     *
     */
    @Test
    fun longSwapEndian() {
        assertEquals(refLong.swapEndian().swapEndian(), refLong)
        /*assertEquals(Long.MIN_VALUE.swapEndian().swapEndian(), Long.MIN_VALUE)
        assertEquals(Long.MAX_VALUE.swapEndian().swapEndian(), Long.MAX_VALUE)*/
    }

    /**
     * Testing .swapEndian() on ULong.
     *
     */
    @Test
    fun ulongSwapEndian() {
        assertEquals(refULong.swapEndian().swapEndian(), refULong)
        /*assertEquals(ULong.MIN_VALUE.swapEndian().swapEndian(), ULong.MIN_VALUE)
        assertEquals(ULong.MAX_VALUE.swapEndian().swapEndian(), ULong.MAX_VALUE)*/
    }

    /**
     * Testing .swapEndian() on Float.
     *
     */
    @Test
    fun floatSwapEndian() {
        assertEquals(refFloat.swapEndian().swapEndian().toRawBits(), refFloat.toRawBits())
        /*assertEquals((Float.MIN_VALUE).swapEndian().swapEndian().toRawBits(), Float.MIN_VALUE.toRawBits()) // <-- Fix KN
        assertEquals((Float.MAX_VALUE).swapEndian().swapEndian().toRawBits(), Float.MAX_VALUE.toRawBits()) // <-- Fix KN/JVM */
    }

    /**
     * Testing .swapEndian() on Double.
     *
     */
    @Test
    fun doubleSwapEndian() {
        assertEquals(refDouble.swapEndian().swapEndian(), refDouble)
        /*assertEquals(Double.MIN_VALUE.swapEndian().swapEndian(), Double.MIN_VALUE)
        assertEquals(Double.MAX_VALUE.swapEndian().swapEndian().toRawBits(), Double.MAX_VALUE.toRawBits()) // <-- Fix KN/JVM */
    }
}