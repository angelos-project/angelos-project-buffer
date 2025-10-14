/**
 * Copyright (c) 2025 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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

import org.angproj.sec.util.Octet
import org.angproj.sec.util.TypeSize
import org.angproj.sec.util.toUnitFraction
import kotlin.test.Test
import kotlin.test.assertEquals

class AbstractUtilityAwareTest : AbstractUtilityAware() {

    val long: Long = 0x1122334455667788

    val upperInt: Int = 0x11223344
    val lowerInt: Int = 0x55667788

    val upperShort: Short = 0x1122
    val lowerShort: Short = 0x3344

    val upperByte: Byte = 0x11
    val lowerByte: Byte = 0x22

    @Test
    fun testJoin() {
        assertEquals(long, joinLong<Unit>(upperInt, lowerInt))
        assertEquals(upperInt, joinInt<Unit>(upperShort, lowerShort))
        assertEquals(upperShort, joinShort<Unit>(upperByte, lowerByte))
        assertEquals(upperInt, upperLong<Unit>(long))
        assertEquals(upperShort, upperInt<Unit>(upperInt))
        assertEquals(upperByte, upperShort<Unit>(upperShort))
        assertEquals(lowerInt, lowerLong<Unit>(long))
        assertEquals(lowerShort, lowerInt<Unit>(upperInt))
        assertEquals(lowerByte, lowerShort<Unit>(upperShort))
    }

    @Test
    fun testSwap() {
        assertEquals(0x8877665544332211uL.toLong(), swapLong<Unit>(long))
    }

    @Test
    fun testConv() {
        assertEquals(long, convUL2L<Unit>(convL2UL<Unit>(long)))
        assertEquals(long.toUnitFraction(), convL2D<Unit>(convD2L<Unit>(long.toUnitFraction())))
        assertEquals(upperInt, convUI2I<Unit>(convI2UI<Unit>(upperInt)))
        assertEquals(upperInt.toUnitFraction(), convI2F<Unit>(convF2I<Unit>(upperInt.toUnitFraction())))
        assertEquals(upperShort, convUS2S<Unit>(convS2US<Unit>(upperShort)))
        assertEquals(upperByte, convUB2B<Unit>(convB2UB<Unit>(upperByte)))
    }

    @Test
    fun testSetGet() {
        val byteArray = ByteArray(8)

        setLong<Unit>(byteArray, 0, long)
        assertEquals(long, getLong<Unit>(byteArray, 0))

        setInt<Unit>(byteArray, 4, upperInt)
        assertEquals(upperInt, getInt<Unit>(byteArray, 4))

        setShort<Unit>(byteArray, 6, upperShort)
        assertEquals(upperShort, getShort<Unit>(byteArray, 6))
    }

    @Test
    fun testSetRevGetRev() {
        val byteArray = ByteArray(8)

        setRevLong<Unit>(byteArray, 0, long)
        assertEquals(long, getRevLong<Unit>(byteArray, 0))

        setRevInt<Unit>(byteArray, 4, upperInt)
        assertEquals(upperInt, getRevInt<Unit>(byteArray, 4))

        setRevShort<Unit>(byteArray, 6, upperShort)
        assertEquals(upperShort, getRevShort<Unit>(byteArray, 6))
    }

    /**
     * Synchronizes org.angproj.util.Octet with AbstractUtilityAware
     * */
    @Test
    fun testSetGetOctet() {
        val byteArray = ByteArray(TypeSize.longSize)

        // Long
        Octet.write(long, byteArray, 0, TypeSize.longSize) { index, value ->
            byteArray[index] = value
        }
        assertEquals(long, getLong<Unit>(byteArray, 0))

        setLong<Unit>(byteArray, 0, long)
        var result = Octet.read(byteArray, 0, byteArray.size) { index ->
            byteArray[index]
        }
        assertEquals(long, result)

        // Int
        Octet.write(upperInt.toLong(), byteArray, 4, TypeSize.intSize) { index, value ->
            byteArray[index] = value
        }
        assertEquals(upperInt, getInt<Unit>(byteArray, 4))

        setInt<Unit>(byteArray, 4, upperInt)
        result = Octet.read(byteArray, 4, TypeSize.intSize) { index ->
            byteArray[index]
        }
        assertEquals(upperInt, result.toInt())

        // Short
        Octet.write(upperShort.toLong(), byteArray, 6, TypeSize.shortSize) { index, value ->
            byteArray[index] = value
        }
        assertEquals(upperShort, getShort<Unit>(byteArray, 6))

        setShort<Unit>(byteArray, 6, upperShort)
        result = Octet.read(byteArray, 6, TypeSize.shortSize) { index ->
            byteArray[index]
        }
        assertEquals(upperShort, result.toShort())
    }

    /**
     * Synchronizes org.angproj.util.Octet with AbstractUtilityAware
     * */
    @Test
    fun testSetRevGetRevOctet() {
        val byteArray = ByteArray(TypeSize.longSize)

        // Long
        Octet.writeRev(long, byteArray, 0, TypeSize.longSize) { index, value ->
            byteArray[index] = value
        }
        assertEquals(long, getRevLong<Unit>(byteArray, 0))

        setRevLong<Unit>(byteArray, 0, long)
        var result = Octet.readRev(byteArray, 0, byteArray.size) { index ->
            byteArray[index]
        }
        assertEquals(long, result)

        // Int
        Octet.writeRev(upperInt.toLong(), byteArray, 4, TypeSize.intSize) { index, value ->
            byteArray[index] = value
        }
        assertEquals(upperInt, getRevInt<Unit>(byteArray, 4))

        setRevInt<Unit>(byteArray, 4, upperInt)
        result = Octet.readRev(byteArray, 4, TypeSize.intSize) { index ->
            byteArray[index]
        }
        assertEquals(upperInt, result.toInt())

        // Short
        Octet.writeRev(upperShort.toLong(), byteArray, 6, TypeSize.shortSize) { index, value ->
            byteArray[index] = value
        }
        assertEquals(upperShort, getRevShort<Unit>(byteArray, 6))

        setRevShort<Unit>(byteArray, 6, upperShort)
        result = Octet.readRev(byteArray, 6, TypeSize.shortSize) { index ->
            byteArray[index]
        }
        assertEquals(upperShort, result.toShort())
    }
}