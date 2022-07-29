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
package org.angproj.io.buf.data

import org.angproj.io.buf.Buffer
import org.angproj.io.buf.Endianness

/**
 * Init ImmutableHeapDataBuffer from a ByteArray.
 *
 * @param array array to wrap
 * @param endian endianness
 * @return Immutable heap based buffer
 */
fun dataByteBufferOf(
    array: ByteArray,
    endian: Endianness = Buffer.nativeEndianness,
): ImmutableHeapDataBuffer = DataByteBuffer(array, array.size, array.size, endian)

/**
 * Init MutableHeapDataBuffer from a ByteArray.
 *
 * @param array array to wrap
 * @param limit initial limit
 * @param endian endianness
 * @return Mutable heap based buffer
 */
fun mutableDataByteBufferOf(
    array: ByteArray,
    limit: Int = array.size,
    endian: Endianness = Buffer.nativeEndianness,
): MutableHeapDataBuffer = MutableDataByteBuffer(array, array.size, limit, endian)

/**
 * Create a MutableHeapDataBuffer with an underlying blank ByteBuffer of certain size.
 *
 * @param size underlying array size
 * @param limit initial limit
 * @param endian endianness
 * @return Mutable heap based buffer
 */
fun mutableDataByteBufferOf(
    size: Int,
    limit: Int = size,
    endian: Endianness = Buffer.nativeEndianness,
): MutableHeapDataBuffer = MutableDataByteBuffer(ByteArray(size), size, limit, endian)

/**
 * Create an ImmutableNativeDataBuffer with an underlying blank ByteBuffer of certain size.
 *
 * @param size underlying native array size
 * @param endian endianness
 * @return
 */
fun nativeDataByteBufferOf(
    size: Int,
    endian: Endianness = Buffer.nativeEndianness,
): ImmutableNativeDataBuffer = NativeDataByteBuffer(size, size, endian)

/**
 * Create MutableNativeDataBuffer with an underlying blank ByteBuffer of certain size.
 *
 * @param size underlying native array size
 * @param limit initial limit
 * @param endian endianness
 * @return
 */
fun mutableNativeDataByteBufferOf(
    size: Int,
    limit: Int = size,
    endian: Endianness = Buffer.nativeEndianness,
): MutableNativeDataBuffer = MutableNativeDataByteBuffer(size, limit, endian)

/**
 * To MutableHeapDataBuffer.
 *
 * @return Mutable heap based copy of the current buffer
 */
fun ImmutableHeapDataBuffer.toMutableDataByteBuffer(): MutableHeapDataBuffer {
    val buf = mutableDataByteBufferOf(ByteArray(size), limit, endian)
    copyInto(buf as AbstractMutableDataBuffer)
    return buf
}

/**
 * To MutableNativeDataBuffer.
 *
 * @return Mutable native based copy of the current buffer
 */
fun ImmutableHeapDataBuffer.toMutableNativeDataByteBuffer(): MutableNativeDataBuffer {
    val buf = mutableNativeDataByteBufferOf(size, limit, endian)
    copyInto(buf as AbstractMutableDataBuffer)
    return buf
}

/**
 * To MutableHeapDataBuffer.
 *
 * @return Mutable heap based copy of the current buffer
 */
fun ImmutableNativeDataBuffer.toMutableDataByteBuffer(): MutableHeapDataBuffer {
    val buf = mutableDataByteBufferOf(size, limit, endian)
    copyInto(buf as AbstractMutableDataBuffer)
    return buf
}

/**
 * To MutableNativeDataBuffer.
 *
 * @return Mutable native based copy of the current buffer
 */
fun ImmutableNativeDataBuffer.toMutableNativeDataByteBuffer(): MutableNativeDataBuffer {
    val buf = mutableNativeDataByteBufferOf(size, limit, endian)
    copyInto(buf as AbstractMutableDataBuffer)
    return buf
}

/**
 * To MutableNativeDataBuffer.
 *
 * @return Mutable native based copy of the current buffer
 */
fun MutableHeapDataBuffer.toMutableNativeDataByteBuffer(): MutableNativeDataBuffer {
    val buf = mutableNativeDataByteBufferOf(size, limit, endian)
    copyInto(buf as AbstractMutableDataBuffer)
    return buf
}

/**
 * To MutableHeapDataBuffer.
 *
 * @return Mutable heap based copy of the current buffer
 */
fun MutableNativeDataBuffer.toMutableDataByteBuffer(): MutableHeapDataBuffer {
    val buf = mutableDataByteBufferOf(size, limit, endian)
    copyInto(buf as AbstractMutableDataBuffer)
    return buf
}