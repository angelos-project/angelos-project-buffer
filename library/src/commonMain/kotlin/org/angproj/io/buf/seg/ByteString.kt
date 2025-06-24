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
import org.angproj.sec.SecureFeed
import org.angproj.sec.SecureRandom
import org.angproj.sec.rand.InitializationVector
import org.angproj.sec.util.TypeSize
import org.angproj.sec.util.floorMod


/**
 * Abstract base class representing a fixed-size, limitable byte string segment with read and write access.
 *
 * The `ByteString` class provides a foundation for managing a sequence of bytes with a defined size and an adjustable limit.
 * It implements [Limitable], [ReadAccess], and [WriteAccess], allowing for controlled access and modification of the underlying data.
 *
 * Key features:
 * - Enforces bounds checking for byte, short, int, and long access to prevent out-of-bounds operations.
 * - Allows the limit of accessible data to be adjusted within the range of the total size.
 * - Supports secure randomization of the segment's memory using cryptographically secure sources.
 *
 * Subclasses must implement the abstract methods for reading and writing primitive values at specified indices.
 *
 * @property stringSize The total fixed size of the byte string segment.
 * @property stringLimit The current limit up to which the segment can be accessed or modified.
 *
 * @constructor Creates a new `ByteString` with the specified size and optional initial limit.
 *
 * @see org.angproj.io.buf.util.Limitable
 * @see org.angproj.io.buf.ReadAccess
 * @see org.angproj.io.buf.WriteAccess
 */
public abstract class ByteString(
    protected val stringSize: Int,
    protected var stringLimit: Int = stringSize
): AbstractUtilityAware(), Limitable, ReadAccess, WriteAccess {

    protected inline fun <reified R: Any> Int.checkRangeByte(): Unit = when(this) {
        !in 0..<stringLimit -> throw SegmentException("Out of bounds. Byte - $this")
        else -> Unit
    }

    protected inline fun <reified R: Any> Int.checkRangeShort(): Unit = when(this) {
        !in 0..<stringLimit-1 -> throw SegmentException("Out of bounds. Short - $this")
        else -> Unit
    }

    protected inline fun <reified R: Any> Int.checkRangeInt(): Unit = when(this) {
        !in 0..<stringLimit-3 -> throw SegmentException("Out of bounds. Int - $this")
        else -> Unit
    }

    protected inline fun <reified R: Any> Int.checkRangeLong(): Unit = when(this) {
        !in 0..<stringLimit-7 -> throw SegmentException("Out of bounds. Long - $this")
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

    public fun checkSum(): Int {
        var result: Long = InitializationVector.IV_CA96.iv

        val totalLength = limit
        val longIndex = totalLength / TypeSize.longSize
        val byteOffset = totalLength % TypeSize.longSize

        if (longIndex > 0) {
            for (index in 0 until longIndex step 8) {
                result = (-result.inv() * 7) xor getLong(index)
            }
        }

        if (byteOffset > 0) {
            for (index in longIndex until totalLength) {
                result = (-result.inv() * 13) xor getByte(index).toLong()
            }
        }

        return (result ushr 32).toInt() xor result.toInt()
    }

    public fun securelyRandomize() {
        SecureFeed.exportLongs(this, 0, limit / TypeSize.longSize) { index, value ->
            setLong(index, value)
        }
        val byteSize = limit.floorMod(TypeSize.longSize)
        SecureRandom.exportBytes(this, limit - byteSize, byteSize) { index, value ->
            setByte(index, value)
        }
    }
}