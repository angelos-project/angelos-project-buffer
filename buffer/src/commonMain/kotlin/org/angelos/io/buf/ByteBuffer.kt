/**
 * Copyright (c) 2022 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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
package org.angelos.io.buf

/**
 * Byte buffer implemented on the heap, as immutable.
 *
 * @constructor
 *
 * @param array ByteArray to wrap into a buffer
 * @param size
 * @param limit
 * @param position
 * @param endianness
 */
@Suppress("OVERRIDE_BY_INLINE")
@OptIn(ExperimentalUnsignedTypes::class)
class ByteBuffer internal constructor(
    array: ByteArray,
    size: Int,
    limit: Int,
    position: Int,
    endianness: Endianness,
) : AbstractBuffer(size, limit, position, endianness), ImmutableHeapBuffer {
    private val _array = array
    private val _view = _array.asUByteArray()

    override inline fun load(index: Int): UByte {
        return _view[index]
    }

    override fun loadByte(index: Int): Byte = load(index).toByte()

    override inline fun loadLong(index: Int): Long = loadReadLong(this, position)

    override fun getArray(): ByteArray = _array

    override inline fun readByte(): Byte = load(position + 0).toByte()

    override inline fun readUByte(): UByte = load(position + 0)

    override inline fun readChar(): Char = when (reverse) {
        true -> loadReadReverseShort(this, position)
        false -> loadReadShort(this, position)
    }.toChar()

    override inline fun readShort(): Short = when (reverse) {
        true -> loadReadReverseShort(this, position)
        false -> loadReadShort(this, position)
    }.toShort()

    override inline fun readUShort(): UShort = when (reverse) {
        true -> loadReadReverseShort(this, position)
        false -> loadReadShort(this, position)
    }.toUShort()

    override inline fun readInt(): Int = when (reverse) {
        true -> loadReadReverseInt(this, position)
        false -> loadReadInt(this, position)
    }

    override inline fun readUInt(): UInt = when (reverse) {
        true -> loadReadReverseUInt(this, position)
        false -> loadReadUInt(this, position)
    }

    override inline fun readLong(): Long = when (reverse) {
        true -> loadReadReverseLong(this, position)
        false -> loadReadLong(this, position)
    }

    override inline fun readULong(): ULong = when (reverse) {
        true -> loadReadReverseULong(this, position)
        false -> loadReadULong(this, position)
    }

    override inline fun readFloat(): Int = when (reverse) {
        true -> loadReadReverseInt(this, position)
        false -> loadReadInt(this, position)
    }

    override inline fun readDouble(): Long = when (reverse) {
        true -> loadReadReverseLong(this, position)
        false -> loadReadLong(this, position)
    }
}