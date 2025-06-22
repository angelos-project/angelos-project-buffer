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

import org.angproj.io.buf.ReadAccess
import org.angproj.io.buf.WriteAccess
import org.angproj.io.buf.util.AbstractUtilityAware
import org.angproj.io.buf.util.Limitable


/**
 * A [ByteString] is a string of bytes that can be read and written to.
 * It has a fixed size and a limit that can be set.
 *
 * @property stringSize The size of the segment in bytes.
 * @property stringLimit The limit of the segment in bytes, defaults to [stringSize].
 */
public abstract class ByteString(
    protected val stringSize: Int,
    protected var stringLimit: Int = stringSize
): AbstractUtilityAware(), Limitable, ReadAccess, WriteAccess {

    protected inline fun <reified R: Any> Int.checkRangeByte(): Unit = when(this) {
        !in 0..<stringLimit -> throw IllegalArgumentException("Out of bounds. Byte - $this")
        else -> Unit
    }

    protected inline fun <reified R: Any> Int.checkRangeShort(): Unit = when(this) {
        !in 0..<stringLimit-1 -> throw IllegalArgumentException("Out of bounds. Short - $this")
        else -> Unit
    }

    protected inline fun <reified R: Any> Int.checkRangeInt(): Unit = when(this) {
        !in 0..<stringLimit-3 -> throw IllegalArgumentException("Out of bounds. Int - $this")
        else -> Unit
    }

    protected inline fun <reified R: Any> Int.checkRangeLong(): Unit = when(this) {
        !in 0..<stringLimit-7 -> throw IllegalArgumentException("Out of bounds. Long - $this")
        else -> Unit
    }

    override val limit: Int
        get() = stringLimit

    override fun limitAt(newLimit: Int) {
        require(newLimit in 0..size) { "New limit must be between 0 and $stringSize" }
        stringLimit = newLimit
    }

    override val size: Int
        get() = stringSize

    abstract override fun getByte(index: Int): Byte

    abstract override fun getShort(index: Int): Short

    abstract override fun getInt(index: Int): Int

    abstract override fun getLong(index: Int): Long

    abstract override fun setByte(index: Int, value: Byte)

    abstract override fun setShort(index: Int, value: Short)

    abstract override fun setInt(index: Int, value: Int)

    abstract override fun setLong(index: Int, value: Long)

}