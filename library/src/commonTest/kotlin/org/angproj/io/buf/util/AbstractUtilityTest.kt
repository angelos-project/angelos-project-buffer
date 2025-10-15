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
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class AbstractUtilityTest : UtilityAware {

    val long: Long = 0x1122334455667788
    val upperInt: Int = 0x11223344
    val upperShort: Short = 0x1122
    val upperByte: Byte = 0x11

    @Test
    fun testFlipAndCheck0() {
        var byte: Byte = 0

        assertFalse(byte.checkFlag0())
        byte = byte.flipOnFlag0()
        assertTrue { byte.checkFlag0() }
        assertEquals(0x1 shl 0, byte.toInt() and 0xff)
        byte = byte.flipOffFlag0()
        assertFalse(byte.checkFlag0())
    }

    @Test
    fun testFlipAndCheck1() {
        var byte: Byte = 0

        assertFalse(byte.checkFlag1())
        byte = byte.flipOnFlag1()
        assertTrue { byte.checkFlag1() }
        assertEquals(0x1 shl 1, byte.toInt() and 0xff)

        byte = byte.flipOffFlag1()
        assertFalse(byte.checkFlag1())
    }

    @Test
    fun testFlipAndCheck2() {
        var byte: Byte = 0

        assertFalse(byte.checkFlag2())
        byte = byte.flipOnFlag2()
        assertTrue { byte.checkFlag2() }
        assertEquals(0x1 shl 2, byte.toInt() and 0xff)
        byte = byte.flipOffFlag2()
        assertFalse(byte.checkFlag2())
    }

    @Test
    fun testFlipAndCheck3() {
        var byte: Byte = 0

        assertFalse(byte.checkFlag3())
        byte = byte.flipOnFlag3()
        assertTrue { byte.checkFlag3() }
        assertEquals(0x1 shl 3, byte.toInt() and 0xff)
        byte = byte.flipOffFlag3()
        assertFalse(byte.checkFlag3())
    }

    @Test
    fun testFlipAndCheck4() {
        var byte: Byte = 0

        assertFalse(byte.checkFlag4())
        byte = byte.flipOnFlag4()
        assertTrue { byte.checkFlag4() }
        assertEquals(0x1 shl 4, byte.toInt() and 0xff)
        byte = byte.flipOffFlag4()
        assertFalse(byte.checkFlag4())
    }

    @Test
    fun testFlipAndCheck5() {
        var byte: Byte = 0

        assertFalse(byte.checkFlag5())
        byte = byte.flipOnFlag5()
        assertTrue { byte.checkFlag5() }
        assertEquals(0x1 shl 5, byte.toInt() and 0xff)
        byte = byte.flipOffFlag5()
        assertFalse(byte.checkFlag5())
    }

    @Test
    fun testFlipAndCheck6() {
        var byte: Byte = 0

        assertFalse(byte.checkFlag6())
        byte = byte.flipOnFlag6()
        assertTrue { byte.checkFlag6() }
        assertEquals(0x1 shl 6, byte.toInt() and 0xff)
        byte = byte.flipOffFlag6()
        assertFalse(byte.checkFlag6())
    }

    @Test
    fun testFlipAndCheck7() {
        var byte: Byte = 0

        assertFalse(byte.checkFlag7())
        byte = byte.flipOnFlag7()
        assertTrue { byte.checkFlag7() }
        println(byte.toString(2))
        assertEquals(0x1 shl 7, byte.toInt() and 0xff)
        byte = byte.flipOffFlag7()
        assertFalse(byte.checkFlag7())
    }

    @Test
    fun testSwap() {
        assertEquals(0x2211, upperShort.swapEndian())
        assertEquals(0x2211u, upperShort.conv2uS().swapEndian())
        assertEquals(0x44332211, upperInt.swapEndian())
        assertEquals(0x44332211u, upperInt.conv2uI().swapEndian())
        assertEquals(0x8877665544332211uL.toLong(), long.swapEndian())
        assertEquals(0x8877665544332211uL, long.conv2uL().swapEndian())
        assertEquals(0x44332211.conv2F(), upperInt.conv2F().swapEndian())
        assertEquals(0x8877665544332211uL.conv2L().conv2D(), long.conv2D().swapEndian())
    }

    @Test
    fun testConv() {
        assertEquals(long, long.conv2uL().conv2L())
        assertEquals(long.toUnitFraction(), long.toUnitFraction().conv2L().conv2D())
        assertEquals(upperInt, upperInt.conv2uI().conv2I())
        assertEquals(upperInt.toUnitFraction(), upperInt.toUnitFraction().conv2I().conv2F())
        assertEquals(upperShort, upperShort.conv2uS().conv2S())
        assertEquals(upperByte, upperByte.conv2uB().conv2B())
    }

    @Test
    fun testWriteRead() {
        val byteArray = ByteArray(8)

        byteArray.writeShortAt(0, upperShort)
        assertEquals(upperShort, byteArray.readShortAt(0))
        byteArray.writeIntAt(0, upperInt)
        assertEquals(upperInt, byteArray.readIntAt(0))
        byteArray.writeLongAt(0, long)
        assertEquals(long, byteArray.readLongAt(0))

        byteArray.writeUShortAt(0, upperShort.conv2uS())
        assertEquals(upperShort.conv2uS(), byteArray.readUShortAt(0))
        byteArray.writeUIntAt(0, upperInt.conv2uI())
        assertEquals(upperInt.conv2uI(), byteArray.readUIntAt(0))
        byteArray.writeULongAt(0, long.conv2uL())
        assertEquals(long.conv2uL(), byteArray.readULongAt(0))

        byteArray.writeFloatAt(0, upperInt.toUnitFraction())
        assertEquals(upperInt.toUnitFraction(), byteArray.readFloatAt(0))
        byteArray.writeDoubleAt(0, long.toUnitFraction())
        assertEquals(long.toUnitFraction(), byteArray.readDoubleAt(0))
    }

    @Test
    fun testWriteRevReadRev() {
        val byteArray = ByteArray(8)

        byteArray.writeRevShortAt(0, upperShort)
        assertEquals(upperShort, byteArray.readRevShortAt(0))
        byteArray.writeRevIntAt(0, upperInt)
        assertEquals(upperInt, byteArray.readRevIntAt(0))
        byteArray.writeRevLongAt(0, long)
        assertEquals(long, byteArray.readRevLongAt(0))

        byteArray.writeRevUShortAt(0, upperShort.conv2uS())
        assertEquals(upperShort.conv2uS(), byteArray.readRevUShortAt(0))
        byteArray.writeRevUIntAt(0, upperInt.conv2uI())
        assertEquals(upperInt.conv2uI(), byteArray.readRevUIntAt(0))
        byteArray.writeRevULongAt(0, long.conv2uL())
        assertEquals(long.conv2uL(), byteArray.readRevULongAt(0))

        byteArray.writeRevFloatAt(0, upperInt.toUnitFraction())
        assertEquals(upperInt.toUnitFraction(), byteArray.readRevFloatAt(0))
        byteArray.writeRevDoubleAt(0, long.toUnitFraction())
        assertEquals(long.toUnitFraction(), byteArray.readRevDoubleAt(0))
    }

    /**
     * Synchronizes org.angproj.util.Octet with AbstractUtility
     * */
    @Test
    fun testWriteReadOctet() {
        val byteArray = ByteArray(TypeSize.longSize)

        // Long
        Octet.write(long, byteArray, 0, TypeSize.longSize) { index, value ->
            byteArray[index] = value
        }
        assertEquals(long, byteArray.readLongAt(0))

       byteArray.writeLongAt(0, long)
        var result = Octet.read(byteArray, 0, byteArray.size) { index ->
            byteArray[index]
        }
        assertEquals(long, result)

        // Int
        Octet.write(upperInt.toLong(), byteArray, 4, TypeSize.intSize) { index, value ->
            byteArray[index] = value
        }
        assertEquals(upperInt, byteArray.readIntAt(4))

        byteArray.writeIntAt(4, upperInt)
        result = Octet.read(byteArray, 4, TypeSize.intSize) { index ->
            byteArray[index]
        }
        assertEquals(upperInt, result.toInt())

        // Short
        Octet.write(upperShort.toLong(), byteArray, 6, TypeSize.shortSize) { index, value ->
            byteArray[index] = value
        }
        assertEquals(upperShort, byteArray.readShortAt(6))

        byteArray.writeShortAt(6, upperShort)
        result = Octet.read(byteArray, 6, TypeSize.shortSize) { index ->
            byteArray[index]
        }
        assertEquals(upperShort, result.toShort())
    }

    /**
     * Synchronizes org.angproj.util.Octet with AbstractUtility
     * */
    @Test
    fun testWriteRevReadRevOctet() {
        val byteArray = ByteArray(TypeSize.longSize)

        byteArray.writeRevShortAt(0, upperShort)
        assertEquals(upperShort, byteArray.readRevShortAt(0))
        byteArray.writeRevIntAt(0, upperInt)
        assertEquals(upperInt, byteArray.readRevIntAt(0))
        byteArray.writeRevLongAt(0, long)
        assertEquals(long, byteArray.readRevLongAt(0))

        // Long
        Octet.writeRev(long, byteArray, 0, TypeSize.longSize) { index, value ->
            byteArray[index] = value
        }
        assertEquals(long, byteArray.readRevLongAt(0))

        byteArray.writeRevLongAt(0, long)
        var result = Octet.readRev(byteArray, 0, byteArray.size) { index ->
            byteArray[index]
        }
        assertEquals(long, result)

        // Int
        Octet.writeRev(upperInt.toLong(), byteArray, 4, TypeSize.intSize) { index, value ->
            byteArray[index] = value
        }
        assertEquals(upperInt, byteArray.readRevIntAt(4))

        byteArray.writeRevIntAt(4, upperInt)
        result = Octet.readRev(byteArray, 4, TypeSize.intSize) { index ->
            byteArray[index]
        }
        assertEquals(upperInt, result.toInt())

        // Short
        Octet.writeRev(upperShort.toLong(), byteArray, 6, TypeSize.shortSize) { index, value ->
            byteArray[index] = value
        }
        assertEquals(upperShort, byteArray.readRevShortAt(6))

        byteArray.writeRevShortAt(6, upperShort)
        result = Octet.readRev(byteArray, 6, TypeSize.shortSize) { index ->
            byteArray[index]
        }
        assertEquals(upperShort, result.toShort())
    }
}