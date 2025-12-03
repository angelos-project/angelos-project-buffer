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

import org.angproj.io.buf.mem.SingleMemoryPool
import org.angproj.io.buf.util.DataSize
import org.angproj.sec.util.TypeSize
import kotlin.test.*

class MemoryTest : SegmentTest<Memory>() {
    override fun createSegment(): Memory = SingleMemoryPool(DataSize._32B).allocate()

    @Test
    fun testCreateDispose() {
        assertFalse(segment.isNull(), "Bytes should not be disposed after creation")
    }

    @Test
    fun testCopyIntoArbitraryLength() {
        val destSeg = createSegment()

        for (i in segment.size - TypeSize.longSize until segment.size) {
            segment.securelyRandomize()
            segment.copyInto(destSeg, 0, 0, i)
            repeat(i) {
                assertEquals(segment.getByte(it), destSeg.getByte(it))
            }
        }
        destSeg.close()
    }
}
