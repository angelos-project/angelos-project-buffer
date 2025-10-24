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

import kotlin.test.BeforeTest
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue
import kotlin.test.assertFalse

abstract class SegmentTest<S: Segment<S>> {

    abstract fun createSegment(): S

    protected lateinit var segment: S

    @BeforeTest
    fun setUp() {
        segment = createSegment()
    }

    @AfterTest
    fun tearDown() {
        segment.close()
    }

    @Test
    fun testGetSetByte() {
        segment.setByte(0, 0x7F)
        assertEquals(0x7F.toByte(), segment.getByte(0))
    }

    @Test
    fun testGetSetShort() {
        segment.setShort(1, 0x1234)
        assertEquals(0x1234.toShort(), segment.getShort(1))
    }

    @Test
    fun testGetSetInt() {
        segment.setInt(2, 0xCAFEBABE.toInt())
        assertEquals(0xCAFEBABE.toInt(), segment.getInt(2))
    }

    @Test
    fun testGetSetLong() {
        segment.setLong(4, 0x1122334455667788L)
        assertEquals(0x1122334455667788L, segment.getLong(4))
    }

    @Test
    fun testBoundsCheck() {
        assertFailsWith<SegmentException> { segment.getByte(32) }
        assertFailsWith<SegmentException> { segment.setShort(31, 0) }
        assertFailsWith<SegmentException> { segment.getInt(29) }
        assertFailsWith<SegmentException> { segment.setLong(25, 0L) }

        assertFailsWith<SegmentException> { segment.getByte(-1) }
        assertFailsWith<SegmentException> { segment.setShort(-1, 0) }
        assertFailsWith<SegmentException> { segment.getInt(-1) }
        assertFailsWith<SegmentException> { segment.setLong(-1, 0L) }
    }

    @Test
    fun testCheckSum32EmptyBytes() {
        assertEquals(0x3c75d83e, segment.checkSum())
    }

    @Test
    fun testIsOpenAndClose() {
        assertTrue(segment.isOpen())
        segment.close()
        assertFalse(segment.isOpen())
        segment.close()
    }
}