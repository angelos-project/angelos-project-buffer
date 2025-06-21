/**
 * Copyright (c) 2024 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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

import org.angproj.io.buf.MemoryBlock
import org.angproj.io.buf.SegmentBlock

public class Memory(size: Int) : Segment<Memory>(size) {

    private val data: SegmentBlock = MemoryBlock.nullBlock as SegmentBlock

    override fun getByte(index: Int): Byte {
        index.checkRangeByte<Unit>()
        // Implementation here
        TODO("Not yet implemented")
    }

    override fun getShort(index: Int): Short {
        index.checkRangeShort<Unit>()
        // Implementation here
        TODO("Not yet implemented")
    }

    override fun getInt(index: Int): Int {
        index.checkRangeInt<Unit>()
        // Implementation here
        TODO("Not yet implemented")
    }

    override fun getLong(index: Int): Long {
        index.checkRangeLong<Unit>()
        // Implementation here
        TODO("Not yet implemented")
    }

    override fun setByte(index: Int, value: Byte) {
        index.checkRangeByte<Unit>()
        // Implementation here
        TODO("Not yet implemented")
    }

    override fun setShort(index: Int, value: Short) {
        index.checkRangeShort<Unit>()
        // Implementation here
        TODO("Not yet implemented")
    }

    override fun setInt(index: Int, value: Int) {
        index.checkRangeInt<Unit>()
        // Implementation here
        TODO("Not yet implemented")
    }

    override fun setLong(index: Int, value: Long) {
        index.checkRangeLong<Unit>()
        // Implementation here
        TODO("Not yet implemented")
    }
}