/**
 * Copyright (c) 2021-2022 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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
 * Reference implementation of a MutableByteBuffer that works on all targets.
 * It may be slow but can be optimized. The Idea is to use this for benchmarking against.
 *
 * @constructor
 *
 * @param array
 * @param size
 * @param limit
 * @param position
 * @param endianness
 */
@Suppress("OVERRIDE_BY_INLINE")
@OptIn(ExperimentalUnsignedTypes::class)
class ReferenceMutableBuffer internal constructor(
    array: ByteArray,
    size: Int,
    limit: Int,
    position: Int,
    endianness: Endianness,
): AbstractMutableBuffer(size, limit, position, endianness), MutableHeapBuffer {
    private val _array = array
    private val _view = _array.asUByteArray()

    override inline fun load(index: Int): UByte {
        return _view[index]
    }

    override fun loadByte(index: Int): Byte = load(index).toByte()

    override inline fun loadLong(index: Int): Long = loadReadLong(this, position)

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

    override inline fun save(value: UByte, index: Int) {
        _view[index] = value
    }

    override fun saveByte(index: Int, value: Byte) = save(value.toUByte(), index)

    override fun saveLong(index: Int, value: Long) = saveWriteLong(this, index, value)

    override fun getArray(): ByteArray = _array

    override fun writeByte(value: Byte) =
        save((value.toInt() and 0xFF).toUByte(), position + 0)

    override fun writeUByte(value: UByte) = save(value, position + 0)

    override fun writeChar(value: Char) = when (reverse) {
        true -> saveWriteReverseShort(this, position, value.code)
        false -> saveWriteShort(this, position, value.code)
    }

    override fun writeShort(value: Short) = when (reverse) {
        true -> saveWriteReverseShort(this, position, value.toInt())
        false -> saveWriteShort(this, position, value.toInt())
    }

    override fun writeUShort(value: UShort) = when (reverse) {
        true -> saveWriteReverseShort(this, position, value.toInt())
        false -> saveWriteShort(this, position, value.toInt())
    }

    override fun writeInt(value: Int) = when (reverse) {
        true -> saveWriteReverseInt(this, position, value)
        false -> saveWriteInt(this, position, value)
    }

    override fun writeUInt(value: UInt) = when (reverse) {
        true -> saveWriteReverseUInt(this, position, value)
        false -> saveWriteUInt(this, position, value)
    }

    override fun writeLong(value: Long) = when (reverse) {
        true -> saveWriteReverseLong(this, position, value)
        false -> saveWriteLong(this, position, value)
    }

    override fun writeULong(value: ULong) = when (reverse) {
        true -> saveWriteReverseULong(this, position, value)
        false -> saveWriteULong(this, position, value)
    }

    override fun writeFloat(value: Int) = when (reverse) {
        true -> saveWriteReverseInt(this, position, value)
        false -> saveWriteInt(this, position, value)
    }

    override fun writeDouble(value: Long) = when (reverse) {
        true -> saveWriteReverseLong(this, position, value)
        false -> saveWriteLong(this, position, value)
    }
}


/**
 * Buffer benchmark is a test setup for the sole purpose of benchmarking different data swapping
 * operations for the sake of optimization, and offers an internal standard to compare buffer
 * data speeds. It is important to know which implementation of data copying that is the fastest
 * for each target and of type: native or heap memory.
 *
 * The sake of these tests are not general unit testing, but should be generally ignored by measuring setups.
 *
 * @constructor Create empty Buffer benchmark
 */
class BufferBenchmarker {
}