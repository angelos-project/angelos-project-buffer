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
import kotlin.test.assertEquals

/**
 * Native reference test for the ReferenceMutableBuffer.
 *
 * @constructor Create empty Reference mutable buffer test
 */
@OptIn(ExperimentalUnsignedTypes::class)
class ReferenceMutableBufferTest: MutableBufferTest() {

    /**
     * Populate a ByteArray using the native set*At() methods as a reference against ReferenceMutableBuffer.
     *
     * @param array
     * @return
     */
    private fun populateNativeByteArray(array: ByteArray): ByteArray {
        array[0] = refByte
        array[1] = refUByte.toByte()
        array.setCharAt(2, refChar)
        array.setShortAt(4, refShort)
        array.setUShortAt(6, refUShort)
        array.setIntAt(8, refInt)
        array.setUIntAt(12, refUInt)
        array.setLongAt(16, refLong)
        array.setULongAt(24, refULong)
        array.setFloatAt(32, refFloat)
        array.setDoubleAt(36, refDouble)
        return array
    }

    /**
     * Test ReferenceMutableBuffer
     *
     */
    @Test
    fun testReferenceMutableBuffer() {
        testMutableBufferRead(refMutableBufferOf(populateNativeByteArray(createArray())))
        testMutableBufferWrite(refMutableBufferOf(createArray()))
        testMutableBufferWriteReverse(refMutableBufferOf(createArray()))

    }

    /**
     * Reference testing of ByteArray setters.
     *
     */
    @Test
    fun testReferenceByteArraySet() {
        val array = createArray()
        array[0] = refByte
        array[1] = refUByte.toByte()
        array.setCharAt(2, refChar)
        array.setShortAt(4, refShort)
        array.setUShortAt(6, refUShort)
        array.setIntAt(8, refInt)
        array.setUIntAt(12, refUInt)
        array.setLongAt(16, refLong)
        array.setULongAt(24, refULong)
        array.setFloatAt(32, refFloat)
        array.setDoubleAt(36, refDouble)

        assertEquals(array[0], refByte)
        assertEquals(array[1].toUByte(), refUByte)
        assertEquals(array.readCharAt(2), refChar)
        assertEquals(array.readShortAt(4), refShort)
        assertEquals(array.readUShortAt(6), refUShort)
        assertEquals(array.readIntAt(8), refInt)
        assertEquals(array.readUIntAt(12), refUInt)
        assertEquals(array.readLongAt(16), refLong)
        assertEquals(array.readULongAt(24), refULong)
        assertEquals(array.readFloatAt(32), refFloat)
        assertEquals(array.readDoubleAt(36), refDouble)
    }

    /**
     * Reference testing of ByteArray getters.
     *
     */
    @Test
    fun testReferenceByteArrayGet() {
        val array = createArray()
        array[0] = refByte
        array[1] = refUByte.toByte()
        array.writeCharAt(2, refChar)
        array.writeShortAt(4, refShort)
        array.writeUShortAt(6, refUShort)
        array.writeIntAt(8, refInt)
        array.writeUIntAt(12, refUInt)
        array.writeLongAt(16, refLong)
        array.writeULongAt(24, refULong)
        array.writeFloatAt(32, refFloat)
        array.writeDoubleAt(36, refDouble)

        assertEquals(array[0], refByte)
        assertEquals(array.getUByteAt(1), refUByte)
        assertEquals(array.getCharAt(2), refChar)
        assertEquals(array.getShortAt(4), refShort)
        assertEquals(array.getUShortAt(6), refUShort)
        assertEquals(array.getIntAt(8), refInt)
        assertEquals(array.getUIntAt(12), refUInt)
        assertEquals(array.getLongAt(16), refLong)
        assertEquals(array.getULongAt(24), refULong)
        assertEquals(array.getFloatAt(32), refFloat)
        assertEquals(array.getDoubleAt(36), refDouble)
    }

    /**
     * Test Buffer.reverse*() in double on all types.
     * This test certifies that all public domain *.reverseBytes() methods reverses properly.
     */
    @Test
    fun testReverse() {
        assertEquals(refShort.swapEndian(), refShort.swapEndian())
        assertEquals(refUShort.swapEndian(), refUShort.swapEndian())
        assertEquals(refChar.swapEndian(), refChar.swapEndian())
        assertEquals(refInt.swapEndian(), refInt.swapEndian())
        assertEquals(refUInt.swapEndian(), refUInt.swapEndian())
        assertEquals(refLong.swapEndian(), refLong.swapEndian())
        assertEquals(refULong.swapEndian(), refULong.swapEndian())
        assertEquals(refFloat.swapEndian(), refFloat.swapEndian())
        assertEquals(refDouble.swapEndian(), refDouble.swapEndian())
    }
}