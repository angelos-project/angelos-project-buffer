/**
 * Copyright (c) 2024-2025 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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

import org.angproj.io.buf.mem.MemoryManager

public class Model(
    private val memCtx: MemoryManager<Model>,
    private val data: LongArray
) : Segment<Model>(data.size * 8) {

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

    override fun dispose() {
        clear()
        securelyRandomize()
        memCtx.recycle(this)
    }
}