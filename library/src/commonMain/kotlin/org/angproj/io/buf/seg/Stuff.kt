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


/*public class Memory(
    private val memCtx: MemoryManager<Memory>,
    private val data: SegmentBlock
) : Segment<Memory>(data.size) {

    init {
        check(data.size >= stringSize) { "Memory size must be at least $stringSize bytes, but was ${data.size} bytes." }
    }

    override val limit: Int
        get() = stringLimit

    override fun limitAt(newLimit: Int) {
        super.limitAt(newLimit)
        data.limitAt(newLimit)
    }

    override val size: Int
        get() = stringSize

    override fun getByte(index: Int): Byte {
        index.checkRangeByte<Unit>()
        return data.getByte(index)
    }

    override fun getShort(index: Int): Short {
        index.checkRangeShort<Unit>()
        return data.getShort(index)
    }

    override fun getInt(index: Int): Int {
        index.checkRangeInt<Unit>()
        return data.getInt(index)
    }

    override fun getLong(index: Int): Long {
        index.checkRangeLong<Unit>()
        return data.getLong(index)
    }

    override fun setByte(index: Int, value: Byte) {
        index.checkRangeByte<Unit>()
        data.setByte(index, value)
    }

    override fun setShort(index: Int, value: Short) {
        index.checkRangeShort<Unit>()
        data.setShort(index, value)
    }

    override fun setInt(index: Int, value: Int) {
        index.checkRangeInt<Unit>()
        data.setInt(index, value)
    }

    override fun setLong(index: Int, value: Long) {
        index.checkRangeLong<Unit>()
        data.setLong(index, value)
    }

    override fun dispose() {
        clear()
        securelyRandomize()
        memCtx.recycle(this)
    }

    override fun address(): TypePointer<*> = data.getPointer()
}*/
