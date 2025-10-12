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
package org.angproj.io.buf.seg

import kotlin.test.*


// Minimal concrete implementation for testing
class TestByteString(size: Int, limit: Int = size) : ByteString(size, limit) {
    private val data = ByteArray(size)

    override fun getByte(index: Int): Byte {
        index.checkRangeByte<Byte>()
        return data[index]
    }

    override fun getShort(index: Int): Short {
        index.checkRangeShort<Short>()
        return ((data[index].toInt() and 0xFF) or
                ((data[index + 1].toInt() and 0xFF) shl 8)).toShort()
    }

    override fun getInt(index: Int): Int {
        index.checkRangeInt<Int>()
        return (data[index].toInt() and 0xFF) or
                ((data[index + 1].toInt() and 0xFF) shl 8) or
                ((data[index + 2].toInt() and 0xFF) shl 16) or
                ((data[index + 3].toInt() and 0xFF) shl 24)
    }

    override fun getLong(index: Int): Long {
        index.checkRangeLong<Long>()
        return (0..7).fold(0L) { acc, i ->
            acc or ((data[index + i].toLong() and 0xFF) shl (8 * i))
        }
    }

    override fun setByte(index: Int, value: Byte) {
        index.checkRangeByte<Byte>()
        data[index] = value
    }

    override fun setShort(index: Int, value: Short) {
        index.checkRangeShort<Short>()
        data[index] = (value.toInt() and 0xFF).toByte()
        data[index + 1] = ((value.toInt() shr 8) and 0xFF).toByte()
    }

    override fun setInt(index: Int, value: Int) {
        index.checkRangeInt<Int>()
        for (i in 0..3) data[index + i] = ((value shr (8 * i)) and 0xFF).toByte()
    }

    override fun setLong(index: Int, value: Long) {
        index.checkRangeLong<Long>()
        for (i in 0..7) data[index + i] = ((value shr (8 * i)) and 0xFF).toByte()
    }

    fun getRawData(): ByteArray = data.copyOf()
}

class ByteStringTest {
    @Test
    fun testConstructionAndProperties() {
        val bs = TestByteString(16)
        assertEquals(16, bs.size)
        assertEquals(16, bs.limit)
    }

    @Test
    fun testLimitAtValidAndInvalid() {
        val bs = TestByteString(8)
        bs.limitAt(4)
        assertEquals(4, bs.limit)
        bs.limitAt(8)
        assertEquals(8, bs.limit)
        assertFailsWith<IllegalArgumentException> { bs.limitAt(-1) }
        assertFailsWith<IllegalArgumentException> { bs.limitAt(9) }
    }

    @Test
    fun testSetGetByte() {
        val bs = TestByteString(4)
        bs.setByte(0, 0x7F)
        assertEquals(0x7F.toByte(), bs.getByte(0))
    }

    @Test
    fun testSetGetShort() {
        val bs = TestByteString(4)
        bs.setShort(1, 0x1234)
        assertEquals(0x1234.toShort(), bs.getShort(1))
    }

    @Test
    fun testSetGetInt() {
        val bs = TestByteString(8)
        bs.setInt(2, 0xCAFEBABE.toInt())
        assertEquals(0xCAFEBABE.toInt(), bs.getInt(2))
    }

    @Test
    fun testSetGetLong() {
        val bs = TestByteString(16)
        bs.setLong(4, 0x1122334455667788L)
        assertEquals(0x1122334455667788L, bs.getLong(4))
    }

    @Test
    fun testBoundsChecking() {
        val bs = TestByteString(8)
        assertFailsWith<SegmentException> { bs.getByte(8) }
        assertFailsWith<SegmentException> { bs.setShort(7, 0) }
        assertFailsWith<SegmentException> { bs.getInt(6) }
        assertFailsWith<SegmentException> { bs.setLong(2, 0L) }
    }

    @Test
    fun testCheckSumConsistency() {
        val bs1 = TestByteString(8)
        val bs2 = TestByteString(8)
        for (i in 0 until 8) {
            bs1.setByte(i, i.toByte())
            bs2.setByte(i, i.toByte())
        }
        assertEquals(bs1.checkSum(), bs2.checkSum())
        bs2.setByte(0, 42)
        assertNotEquals(bs1.checkSum(), bs2.checkSum())
    }

    @Test
    fun testSecurelyRandomizeChangesData() {
        val bs = TestByteString(16)
        val before = bs.getRawData()
        bs.securelyRandomize()
        val after = bs.getRawData()
        // Not all bytes may change, but at least one should
        assertTrue(before.indices.any { before[it] != after[it] })
    }

    @Test
    fun testEqualsAndHashCode() {
        val bs1 = TestByteString(8)
        val bs2 = TestByteString(8)
        for (i in 0 until 8) {
            bs1.setByte(i, (i * 2).toByte())
            bs2.setByte(i, (i * 2).toByte())
        }
        assertEquals(bs1, bs2)
        assertEquals(bs1.hashCode(), bs2.hashCode())
        bs2.setByte(0, 99)
        assertNotEquals(bs1, bs2)
    }

    @Test
    fun testRandomizeSecurely() {
        val bs = TestByteString(65)
        val originalData = bs.getRawData().copyOf()
        bs.securelyRandomize()
        val randomizedData = bs.getRawData()
        assertNotEquals(originalData.contentToString(), randomizedData.contentToString())
        // Ensure that the data is still within byte range
        for (byte in randomizedData) {
            assertTrue(byte in Byte.MIN_VALUE..Byte.MAX_VALUE)
        }
    }

    @Test
    fun testChecksum() {
        val bs = TestByteString(16)
        for (i in 0 until 16) {
            bs.setByte(i, i.toByte())
        }
        val checksum = bs.checkSum()
        assertTrue(checksum > 0) // Checksum should be a positive value
        // Modify one byte and check if checksum changes
        bs.setByte(0, 99)
        assertNotEquals(checksum, bs.checkSum())
    }
}