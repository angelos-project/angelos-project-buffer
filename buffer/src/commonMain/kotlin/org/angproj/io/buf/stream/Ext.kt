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
package org.angproj.io.buf.stream

import org.angproj.io.buf.Buffer
import org.angproj.io.buf.Endianness

/**
 * Init ImmutableHeapStreamBuffer from a ByteArray.
 *
 * @param array array to wrap
 * @param endian endianness
 * @return Immutable heap based buffer
 */
fun streamByteBufferOf(
    array: ByteArray,
    endian: Endianness = Buffer.nativeEndianness,
): ImmutableHeapStreamBuffer = StreamByteBuffer(array, array.size, array.size, 0, endian)

/**
 * Init MutableHeapStreamBuffer from a ByteArray.
 *
 * @param array array to wrap
 * @param limit initial limit
 * @param endian endianness
 * @return Mutable heap based buffer
 */
fun mutableStreamByteBufferOf(
    array: ByteArray,
    limit: Int = array.size,
    endian: Endianness = Buffer.nativeEndianness,
): MutableHeapStreamBuffer = MutableStreamByteBuffer(array, array.size, limit, 0, endian)

/**
 * Create a MutableHeapStreamBuffer with an underlying blank ByteBuffer of certain size.
 *
 * @param size underlying array size
 * @param limit initial limit
 * @param endian endianness
 * @return Mutable heap based buffer
 */
fun mutableStreamByteBufferOf(
    size: Int,
    limit: Int = size,
    endian: Endianness = Buffer.nativeEndianness,
): MutableHeapStreamBuffer = MutableStreamByteBuffer(ByteArray(size), size, limit, 0, endian)

/**
 * Create an ImmutableNativeStreamBuffer with an underlying blank ByteBuffer of certain size.
 *
 * @param size underlying native array size
 * @param endian endianness
 * @return
 */
fun nativeStreamByteBufferOf(
    size: Int,
    endian: Endianness = Buffer.nativeEndianness,
): ImmutableNativeStreamBuffer = NativeStreamByteBuffer(size, size, 0, endian)

/**
 * Create MutableNativeStreamBuffer with an underlying blank ByteBuffer of certain size.
 *
 * @param size underlying native array size
 * @param limit initial limit
 * @param endian endianness
 * @return
 */
fun mutableNativeStreamByteBufferOf(
    size: Int,
    limit: Int = size,
    endian: Endianness = Buffer.nativeEndianness,
): MutableNativeStreamBuffer = MutableNativeStreamByteBuffer(size, limit, 0, endian)

/**
 * To mutable heap stream byte buffer.
 *
 * @return Mutable heap based copy of the current buffer
 */
fun ImmutableHeapStreamBuffer.toMutableStreamByteBuffer(): MutableHeapStreamBuffer {
    val buf = mutableStreamByteBufferOf(ByteArray(size), limit, endian)
    copyInto(buf as AbstractMutableStreamBuffer)
    return buf
}

/**
 * To mutable native stream byte buffer.
 *
 * @return Mutable native based copy of the current buffer
 */
fun ImmutableHeapStreamBuffer.toMutableNativeStreamByteBuffer(): MutableNativeStreamBuffer {
    val buf = mutableNativeStreamByteBufferOf(size, limit, endian)
    copyInto(buf as AbstractMutableStreamBuffer)
    return buf
}

/**
 * To mutable heap stream byte buffer.
 *
 * @return Mutable heap based copy of the current buffer
 */
fun ImmutableNativeStreamBuffer.toMutableStreamByteBuffer(): MutableHeapStreamBuffer {
    val buf = mutableStreamByteBufferOf(size, limit, endian)
    copyInto(buf as AbstractMutableStreamBuffer)
    return buf
}

/**
 * To mutable native stream byte buffer.
 *
 * @return Mutable native based copy of the current buffer
 */
fun ImmutableNativeStreamBuffer.toMutableNativeStreamByteBuffer(): MutableNativeStreamBuffer {
    val buf = mutableNativeStreamByteBufferOf(size, limit, endian)
    copyInto(buf as AbstractMutableStreamBuffer)
    return buf
}

/**
 * To mutable native stream byte buffer
 *
 * @return Mutable native based copy of the current buffer
 */
fun MutableHeapStreamBuffer.toMutableNativeStreamByteBuffer(): MutableNativeStreamBuffer {
    val buf = mutableNativeStreamByteBufferOf(size, limit, endian)
    copyInto(buf as AbstractMutableStreamBuffer)
    return buf
}

/**
 * To mutable stream byte buffer
 *
 * @return Mutable heap based copy of the current buffer
 */
fun MutableNativeStreamBuffer.toMutableStreamByteBuffer(): MutableHeapStreamBuffer {
    val buf = mutableStreamByteBufferOf(size, limit, endian)
    copyInto(buf as AbstractMutableStreamBuffer)
    return buf
}