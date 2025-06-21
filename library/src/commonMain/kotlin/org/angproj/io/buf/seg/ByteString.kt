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

import org.angproj.io.buf.ReadAccess
import org.angproj.io.buf.WriteAccess
import org.angproj.io.buf.util.Limitable


public abstract class ByteString: Limitable, ReadAccess, WriteAccess {

    protected inline fun <reified R: Any> Int.checkRangeByte(): Unit = when(this) {
        !in 0..<limit -> throw IllegalArgumentException("Out of bounds. Byte - $this")
        else -> Unit
    }

    protected inline fun <reified R: Any> Int.checkRangeShort(): Unit = when(this) {
        !in 0..<limit-1 -> throw IllegalArgumentException("Out of bounds. Short - $this")
        else -> Unit
    }

    protected inline fun <reified R: Any> Int.checkRangeInt(): Unit = when(this) {
        !in 0..<limit-3 -> throw IllegalArgumentException("Out of bounds. Int - $this")
        else -> Unit
    }

    protected inline fun <reified R: Any> Int.checkRangeLong(): Unit = when(this) {
        !in 0..<limit-7 -> throw IllegalArgumentException("Out of bounds. Long - $this")
        else -> Unit
    }

    override val limit: Int
        get() = TODO("Not yet implemented")

    override fun limitAt(newLimit: Int) {
        TODO("Not yet implemented")
    }

    override val size: Int
        get() = TODO("Not yet implemented")

    abstract override fun getByte(index: Int): Byte

    abstract override fun getShort(index: Int): Short

    abstract override fun getInt(index: Int): Int

    abstract override fun getLong(index: Int): Long

    abstract override fun setByte(index: Int, value: Byte)

    abstract override fun setShort(index: Int, value: Short)

    abstract override fun setInt(index: Int, value: Int)

    abstract override fun setLong(index: Int, value: Long)

}