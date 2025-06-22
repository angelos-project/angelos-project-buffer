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

import org.angproj.io.buf.mem.BytesPool
import org.angproj.sec.SecureRandom


public class Bytes(
    private val memCtx: BytesPool,
    private val data: ByteArray
) : Segment<Bytes>(data.size) {


    override fun getByte(index: Int): Byte {
        index.checkRangeByte<Unit>()
        return data[index]
    }

    override fun getShort(index: Int): Short {
        index.checkRangeShort<Unit>()
        return getShort<Unit>(data, index)
    }

    override fun getInt(index: Int): Int {
        index.checkRangeInt<Unit>()
        return getInt<Unit>(data, index)
    }

    override fun getLong(index: Int): Long {
        index.checkRangeLong<Unit>()
        return getLong<Unit>(data, index)
    }

    override fun setByte(index: Int, value: Byte) {
        index.checkRangeByte<Unit>()
        data[index] = value
    }

    override fun setShort(index: Int, value: Short) {
        index.checkRangeShort<Unit>()
        setShort<Unit>(data, index, value)
    }

    override fun setInt(index: Int, value: Int) {
        index.checkRangeInt<Unit>()
        setInt<Unit>(data, index, value)
    }

    override fun setLong(index: Int, value: Long) {
        index.checkRangeLong<Unit>()
        setLong<Unit>(data, index, value)
    }

    override fun dispose() {
        SecureRandom.readBytes(data)
        memCtx.recycle(this)
    }
}