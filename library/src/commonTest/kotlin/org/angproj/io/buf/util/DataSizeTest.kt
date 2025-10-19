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

import kotlin.test.*

class DataSizeTest {

    @Test
    fun testToIntAndToLongConsistency() {
        for (ds in DataSize.values()) {
            assertEquals(ds.toInt().toLong(), ds.toLong(), "toLong must equal toInt.toLong() for $ds")
        }
    }

    @Test
    fun testUnknownValue() {
        assertTrue(DataSize.UNKNOWN.isUnknown())
        assertEquals(-1, DataSize.UNKNOWN.toInt())
        assertEquals(-1L, DataSize.UNKNOWN.toLong())
    }

    @Test
    fun testIsUnknownForKnownSizes() {
        assertFalse(DataSize._32B.isUnknown())
        assertFalse(DataSize._1G.isUnknown())
    }

    @Test
    fun testFindLowestAbove_basicCases() {
        assertEquals(DataSize._32B, DataSize.findLowestAbove(0))
        assertEquals(DataSize._32B, DataSize.findLowestAbove(1))
        assertEquals(DataSize._32B, DataSize.findLowestAbove(31))
        assertEquals(DataSize._32B, DataSize.findLowestAbove(32))
        assertEquals(DataSize._64B, DataSize.findLowestAbove(33))
        assertEquals(DataSize._64B, DataSize.findLowestAbove(63))
        assertEquals(DataSize._64B, DataSize.findLowestAbove(64))
        assertEquals(DataSize._128B, DataSize.findLowestAbove(65))
    }

    @Test
    fun testFindLowestAbove_powerOfTwoAndLargeValues() {
        // exact powers of two should return themselves (above threshold)
        assertEquals(DataSize._512B, DataSize.findLowestAbove(DataSize._512B.toInt()))
        assertEquals(DataSize._1K, DataSize.findLowestAbove(DataSize._1K.toInt()))
        assertEquals(DataSize._1G, DataSize.findLowestAbove(DataSize._1G.toInt()))

        // non-power values map to next higher defined DataSize
        assertEquals(DataSize._1K, DataSize.findLowestAbove(1000))
        assertEquals(DataSize._2K, DataSize.findLowestAbove(1025))
    }

    @Test
    fun testFindLowestAbove_invalidRange() {
        assertFailsWith<IllegalArgumentException> { DataSize.findLowestAbove(-1) }
        assertFailsWith<IllegalArgumentException> { DataSize.findLowestAbove(DataSize._1G.toInt() + 1) }
    }
}