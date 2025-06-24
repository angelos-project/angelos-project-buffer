/**
 * Copyright (c) 2022-2024 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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
import org.angproj.io.buf.TestInformationStub.refRevDouble
import org.angproj.io.buf.TestInformationStub.refRevFloat
import org.angproj.io.buf.TestInformationStub.refRevInt
import org.angproj.io.buf.TestInformationStub.refRevLong
import org.angproj.io.buf.TestInformationStub.refRevShort
import org.angproj.io.buf.TestInformationStub.refRevUInt
import org.angproj.io.buf.TestInformationStub.refRevULong
import org.angproj.io.buf.TestInformationStub.refRevUShort
import org.angproj.io.buf.TestInformationStub.refShort
import org.angproj.io.buf.TestInformationStub.refUInt
import org.angproj.io.buf.TestInformationStub.refULong
import org.angproj.io.buf.TestInformationStub.refUShort
import org.angproj.io.buf.util.UtilityAware
import kotlin.experimental.ExperimentalNativeApi
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.collections.fill
import kotlin.native.getDoubleAt
import kotlin.native.getFloatAt
import kotlin.native.getIntAt
import kotlin.native.getLongAt
import kotlin.native.getShortAt
import kotlin.native.getUIntAt
import kotlin.native.getULongAt
import kotlin.native.getUShortAt
import kotlin.native.setDoubleAt
import kotlin.native.setFloatAt
import kotlin.native.setIntAt
import kotlin.native.setLongAt
import kotlin.native.setShortAt
import kotlin.native.setUIntAt
import kotlin.native.setULongAt
import kotlin.native.setUShortAt
import kotlin.toRawBits


/**
 * Unit tests for verifying correct reading and writing of primitive types
 * (`Short`, `UShort`, `Int`, `UInt`, `Long`, `ULong`, `Float`, `Double`)
 * to a `ByteArray` on Kotlin/Native.
 *
 * Tests both native and reversed byte order, comparing custom extension
 * functions with Kotlin/Native's built-in array operations to ensure consistency.
 *
 * @see org.angproj.io.buf.readShortAt
 * @see org.angproj.io.buf.writeShortAt
 * @see org.angproj.io.buf.readIntAt
 * @see org.angproj.io.buf.writeIntAt
 * @see org.angproj.io.buf.readLongAt
 * @see org.angproj.io.buf.writeLongAt
 * @see org.angproj.io.buf.readFloatAt
 * @see org.angproj.io.buf.writeFloatAt
 * @see org.angproj.io.buf.readDoubleAt
 * @see org.angproj.io.buf.writeDoubleAt
 */
@OptIn(ExperimentalNativeApi::class)
class NativeByteArrayTest: UtilityAware {


    @Test
    fun short() {
        val array = ByteArray(8)
        array.setShortAt(0, refShort)
        assertEquals(array.readShortAt(0), refShort)

        array.fill(0)
        array.writeShortAt(0, refShort)
        assertEquals(array.getShortAt(0), refShort)
    }

    @Test
    fun shortRev() {
        val array = ByteArray(8)
        array.setShortAt(0, refShort)
        assertEquals(array.readRevShortAt(0), refRevShort)

        array.fill(0)
        array.writeRevShortAt(0, refRevShort)
        assertEquals(array.getShortAt(0), refShort)
    }

    @Test
    fun uShort() {
        val array = ByteArray(8)
        array.setUShortAt(0, refUShort)
        assertEquals(array.readUShortAt(0), refUShort)

        array.fill(0)
        array.writeUShortAt(0, refUShort)
        assertEquals(array.getUShortAt(0), refUShort)
    }

    @Test
    fun uShortRev() {
        val array = ByteArray(8)
        array.setUShortAt(0, refUShort)
        assertEquals(array.readRevUShortAt(0), refRevUShort)

        array.fill(0)
        array.writeRevUShortAt(0, refRevUShort)
        assertEquals(array.getUShortAt(0), refUShort)
    }

    @Test
    fun int() {
        val array = ByteArray(8)
        array.setIntAt(0, refInt)
        assertEquals(array.readIntAt(0), refInt)

        array.fill(0)
        array.writeIntAt(0, refInt)
        assertEquals(array.getIntAt(0), refInt)
    }

    @Test
    fun intRev() {
        val array = ByteArray(8)
        array.setIntAt(0, refInt)
        assertEquals(array.readRevIntAt(0), refRevInt)

        array.fill(0)
        array.writeRevIntAt(0, refRevInt)
        assertEquals(array.getIntAt(0), refInt)
    }

    @Test
    fun uInt() {
        val array = ByteArray(8)
        array.setUIntAt(0, refUInt)
        assertEquals(array.readUIntAt(0), refUInt)

        array.fill(0)
        array.writeUIntAt(0, refUInt)
        assertEquals(array.getUIntAt(0), refUInt)
    }

    @Test
    fun uIntRev() {
        val array = ByteArray(8)
        array.setUIntAt(0, refUInt)
        assertEquals(array.readRevUIntAt(0), refRevUInt)

        array.fill(0)
        array.writeRevUIntAt(0, refRevUInt)
        assertEquals(array.getUIntAt(0), refUInt)
    }

    @Test
    fun long() {
        val array = ByteArray(8)
        array.setLongAt(0, refLong)
        assertEquals(array.readLongAt(0), refLong)

        array.fill(0)
        array.writeLongAt(0, refLong)
        assertEquals(array.getLongAt(0), refLong)
    }

    @Test
    fun longRev() {
        val array = ByteArray(8)
        array.setLongAt(0, refLong)
        assertEquals(array.readRevLongAt(0), refRevLong)

        array.fill(0)
        array.writeRevLongAt(0, refRevLong)
        assertEquals(array.getLongAt(0), refLong)
    }

    @Test
    fun uLong() {
        val array = ByteArray(8)
        array.setULongAt(0, refULong)
        assertEquals(array.readULongAt(0), refULong)

        array.fill(0)
        array.writeULongAt(0, refULong)
        assertEquals(array.getULongAt(0), refULong)
    }

    @Test
    fun uLongRev() {
        val array = ByteArray(8)
        array.setULongAt(0, refULong)
        assertEquals(array.readRevULongAt(0), refRevULong)

        array.fill(0)
        array.writeRevULongAt(0, refRevULong)
        assertEquals(array.getULongAt(0), refULong)
    }

    @Test
    fun float() {
        val array = ByteArray(8)
        array.setFloatAt(0, refFloat)
        assertEquals(array.readFloatAt(0).toRawBits(), refFloat.toRawBits())

        array.fill(0)
        array.writeFloatAt(0, refFloat)
        assertEquals(array.getFloatAt(0).toRawBits(), refFloat.toRawBits())
    }

    @Test
    fun floatRev() {
        val array = ByteArray(8)
        array.setFloatAt(0, refFloat)
        assertEquals(array.readRevFloatAt(0).toRawBits(), refRevFloat.toRawBits())

        array.fill(0)
        array.writeRevFloatAt(0, refRevFloat)
        assertEquals(array.getFloatAt(0).toRawBits(), refFloat.toRawBits())
    }

    @Test
    fun double() {
        val array = ByteArray(8)
        array.setDoubleAt(0, refDouble)
        assertEquals(array.readDoubleAt(0), refDouble)

        array.fill(0)
        array.writeDoubleAt(0, refDouble)
        assertEquals(array.getDoubleAt(0), refDouble)
    }

    @Test
    fun doubleRev() {
        val array = ByteArray(8)
        array.setDoubleAt(0, refDouble)
        assertEquals(array.readRevDoubleAt(0), refRevDouble)

        array.fill(0)
        array.writeRevDoubleAt(0, refRevDouble)
        assertEquals(array.getDoubleAt(0), refDouble)
    }
}