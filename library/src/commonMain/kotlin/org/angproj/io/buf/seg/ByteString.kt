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
import org.angproj.sec.Uuid
import org.angproj.sec.rand.AbstractSponge256
import org.angproj.sec.rand.InitializationVector
import org.angproj.sec.util.HashAbsorber
import org.angproj.sec.util.Octet
import org.angproj.sec.util.TypeSize
import org.angproj.sec.util.ensure
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

    /**
     * Checks if the given index is within the valid range for reading or writing a byte value.
     *
     * @throws SegmentException if the index is out of bounds for a byte value.
     */
    protected inline fun <reified R: Any> Int.checkRangeByte(): Unit = when(this) {
        !in 0..<stringLimit -> ensure { SegmentException("Out of bounds. Byte - $this") }
        else -> Unit
    }

    /**
     * Checks if the given index is within the valid range for reading or writing a short value.
     *
     * @throws SegmentException if the index is out of bounds for a short value.
     */
    protected inline fun <reified R: Any> Int.checkRangeShort(): Unit = when(this) {
        !in 0..<stringLimit-1 -> ensure { SegmentException("Out of bounds. Short - $this") }
        else -> Unit
    }

    /**
     * Checks if the given index is within the valid range for reading or writing an integer value.
     *
     * @throws SegmentException if the index is out of bounds for an integer value.
     */
    protected inline fun <reified R: Any> Int.checkRangeInt(): Unit = when(this) {
        !in 0..<stringLimit-3 -> ensure { SegmentException("Out of bounds. Int - $this") }
        else -> Unit
    }

    /**
     * Checks if the given index is within the valid range for reading or writing a long value.
     *
     * @throws SegmentException if the index is out of bounds for a long value.
     */
    protected inline fun <reified R: Any> Int.checkRangeLong(): Unit = when(this) {
        !in 0..<stringLimit-7 -> ensure { SegmentException("Out of bounds. Long - $this") }
        else -> Unit
    }

    /**
     * The current limit of the byte string segment.
     *
     * This property represents the maximum number of bytes that can be accessed or modified in the segment.
     * It can be adjusted using the [limitAt] method, but must always be within the bounds of the segment's size.
     */
    override val limit: Int
        get() = stringLimit

    /**
     * Sets a new limit for the byte string segment.
     *
     * This method allows adjusting the limit of accessible data within the segment.
     * The new limit must be between 0 and the total size of the segment.
     *
     * @param newLimit The new limit to set for the segment. Must be between 0 and [size].
     * @throws SegmentException if the new limit is out of bounds.
     */
    override fun limitAt(newLimit: Int) {
        require(newLimit in 0..size) { "New limit must be between 0 and $stringSize" }
        stringLimit = newLimit
    }

    /**
     * The total size of the byte string segment in bytes.
     *
     * This property represents the fixed size of the segment, which is defined at construction time.
     * It cannot be changed after the segment is created.
     */
    override val size: Int
        get() = stringSize

    /**
     * Gets a byte value at the specified index in the byte string segment.
     *
     * This method allows reading an 8-bit byte value from the segment at the given index.
     * The index must be within the bounds of the segment.
     *
     * @param index The index from which to read the byte value. Must be within the segment's limit.
     * @return The byte value read from the segment.
     */
    abstract override fun getByte(index: Int): Byte

    /**
     * Gets a short value at the specified index in the byte string segment.
     *
     * This method allows reading a 16-bit short value from the segment at the given index.
     * The index must be within the bounds of the segment, and it should be aligned to
     * the size of a short (2 bytes).
     *
     * @param index The index from which to read the short value. Must be a multiple of 2.
     * @return The short value read from the segment.
     */
    abstract override fun getShort(index: Int): Short

    /**
     * Gets an integer value at the specified index in the byte string segment.
     *
     * This method allows reading a 32-bit integer value from the segment at the given index.
     * The index must be within the bounds of the segment, and it should be aligned to
     * the size of an integer (4 bytes).
     *
     * @param index The index from which to read the integer value. Must be a multiple of 4.
     * @return The integer value read from the segment.
     */
    abstract override fun getInt(index: Int): Int

    /**
     * Gets a long value at the specified index in the byte string segment.
     *
     * This method allows reading a 64-bit long value from the segment at the given index.
     * The index must be within the bounds of the segment, and it should be aligned to
     * the size of a long (8 bytes).
     *
     * @param index The index from which to read the long value. Must be a multiple of 8.
     * @return The long value read from the segment.
     */
    abstract override fun getLong(index: Int): Long

    /**
     * Sets a byte value at the specified index in the byte string segment.
     *
     * This method allows writing an 8-bit byte value to the segment at the given index.
     * The index must be within the bounds of the segment.
     *
     * @param index The index at which to set the byte value. Must be within the segment's limit.
     * @param value The byte value to set in the segment.
     */
    abstract override fun setByte(index: Int, value: Byte)

    /**
     * Sets a short value at the specified index in the byte string segment.
     *
     * This method allows writing a 16-bit short value to the segment at the given index.
     * The index must be within the bounds of the segment, and it should be aligned to
     * the size of a short (2 bytes).
     *
     * @param index The index at which to set the short value. Must be a multiple of 2.
     * @param value The short value to set in the segment.
     */
    abstract override fun setShort(index: Int, value: Short)

    /**
     * Sets an integer value at the specified index in the byte string segment.
     *
     * This method allows writing a 32-bit integer value to the segment at the given index.
     * The index must be within the bounds of the segment, and it should be aligned to
     * the size of an integer (4 bytes).
     *
     * @param index The index at which to set the integer value. Must be a multiple of 4.
     * @param value The integer value to set in the segment.
     */
    abstract override fun setInt(index: Int, value: Int)

    /**
     * Sets a long value at the specified index in the byte string segment.
     *
     * This method allows writing a 64-bit long value to the segment at the given index.
     * The index must be within the bounds of the segment, and it should be aligned to
     * the size of a long (8 bytes).
     *
     * @param index The index at which to set the long value. Must be a multiple of 8.
     * @param value The long value to set in the segment.
     */
    abstract override fun setLong(index: Int, value: Long)

    /**
     * Computes a checksum for the byte string segment using a specified seed.
     *
     * The checksum is calculated by iterating through the segment's bytes and applying a series of transformations
     * based on the byte values and the provided seed. The result is a long value that represents the checksum.
     *
     * @param seed The initial seed value to start the checksum calculation. Defaults to 0.
     * @return The computed checksum as a long value.
     */
    public fun checkSum(seed: Uuid = Uuid.nil): Long {
        val sponge = object : AbstractSponge256() {}
        val hashAbsorber = HashAbsorber(sponge)
        var offset = 0

        hashAbsorber.absorb(seed.upper)
        hashAbsorber.absorb(seed.lower)

        repeat(limit.floorDiv(TypeSize.longSize)) {
            hashAbsorber.absorb(getLong(offset))
            offset += TypeSize.longSize
        }

        if(offset < limit) {
            val finalValue = Octet.read(this, offset, limit.floorMod(TypeSize.longSize)) { index ->
                getByte(index)
            }

            hashAbsorber.absorb(finalValue)
        }

        sponge.scramble()
        return sponge.squeeze(0)
    }

    /**
     * Computes a checksum for the byte string segment using a predefined initialization vector.
     *
     * The checksum is calculated by applying a series of transformations to the segment's bytes,
     * starting with a specific initialization vector. The result is a 32-bit integer that represents
     * the checksum of the segment.
     *
     * @return The computed checksum as an integer.
     */
    public fun checkSum(): Int {
        val result: Long = checkSum(Uuid(InitializationVector.IV_356C.iv, InitializationVector.IV_CA96.iv))
        return (result ushr 32).toInt() xor result.toInt()
    }

    /**
     * Securely randomizes the contents of the byte string segment.
     *
     * This method fills the segment with cryptographically secure random data.
     * It uses the `SecureFeed` to export long values and `SecureRandom` to export bytes,
     * ensuring that the segment is filled with high-quality random data.
     */
    public fun securelyRandomize() {
        SecureFeed.readLongs(this, 0, limit.floorDiv(TypeSize.longSize)) { index, value ->
            setLong(index * TypeSize.longSize, value)
        }
        val byteSize = limit.floorMod(TypeSize.longSize)
        SecureFeed.readBytes(this, limit - byteSize, byteSize) { index, value ->
            setByte(index, value)
        }
    }

    public infix fun contentEquals(other: ByteString?): Boolean {
        if (this === other) return true
        if (other !is ByteString) return false

        return checkSum() == other.checkSum()
    }
}