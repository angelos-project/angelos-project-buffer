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
 * Init ImmutableHeapBuffer from a ByteArray.
 *
 * @param array array to wrap
 * @param endian endianness
 * @return Immutable heap based buffer
 */
fun byteBufferOf(
    array: ByteArray,
    endian: Endianness = Buffer.nativeEndianness,
): ImmutableHeapBuffer = ByteBuffer(array, array.size, array.size, 0, endian)

/**
 * Init MutableHeapBuffer from a ByteArray.
 *
 * @param array array to wrap
 * @param limit initial limit
 * @param endian endianness
 * @return Mutable heap based buffer
 */
fun mutableByteBufferOf(
    array: ByteArray,
    limit: Int = array.size,
    endian: Endianness = Buffer.nativeEndianness,
): MutableHeapBuffer = MutableByteBuffer(array, array.size, limit, 0, endian)

/**
 * Create a MutableHeapBuffer with an underlying blank ByteBuffer of certain size.
 *
 * @param size underlying array size
 * @param limit initial limit
 * @param endian endianness
 * @return Mutable heap based buffer
 */
fun mutableByteBufferOf(
    size: Int,
    limit: Int = size,
    endian: Endianness = Buffer.nativeEndianness,
): MutableHeapBuffer = MutableByteBuffer(ByteArray(size), size, limit, 0, endian)

/**
 * Create an ImmutableNativeBuffer with an underlying blank ByteBuffer of certain size.
 *
 * @param size underlying native array size
 * @param endian endianness
 * @return
 */
fun nativeByteBufferOf(
    size: Int,
    endian: Endianness = Buffer.nativeEndianness,
): ImmutableNativeBuffer = NativeByteBuffer(size, size, 0, endian)

/**
 * Wrap a native piece of allocated memory into a NativeByteBuffer using pointer.
 * To be supported on all platforms except JS in the future.
 *
 * @param pointer native pointer to allocated memory
 * @param size size in bytes of allocated memory
 * @param limit an initial limit
 * @param endian endianness
 * @return Immutable native based buffer
 */
fun wrapIntoNativeByteBufferOf(
    pointer: Long,
    size: Int,
    limit: Int = size,
    endian: Endianness = Buffer.nativeEndianness,
): ImmutableNativeBuffer {
    throw UnsupportedOperationException()
}

/**
 * Create MutableNativeBuffer with an underlying blank ByteBuffer of certain size.
 *
 * @param size underlying native array size
 * @param limit initial limit
 * @param endian endianness
 * @return
 */
fun mutableNativeByteBufferOf(
    size: Int,
    limit: Int = size,
    endian: Endianness = Buffer.nativeEndianness,
): MutableNativeBuffer = MutableNativeByteBuffer(size, limit, 0, endian)

/**
 * Wrap a native piece of allocated memory into a MutableNativeByteBuffer using pointer.
 * To be supported on all platforms except JS in the future.
 *
 * @param pointer native pointer to allocated memory
 * @param size size in bytes of allocated memory
 * @param limit an initial limit
 * @param endian endianness
 * @return Mutable native based buffer
 */
fun wrapIntoMutableNativeByteBufferOf(
    pointer: Long,
    size: Int,
    limit: Int = size,
    endian: Endianness = Buffer.nativeEndianness,
): MutableNativeBuffer {
    throw UnsupportedOperationException()
}

/**
 * To mutable heap byte buffer.
 *
 * @return Mutable heap based copy of the current buffer
 */
fun ImmutableHeapBuffer.toMutableByteBuffer(): MutableHeapBuffer {
    val buf = mutableByteBufferOf(ByteArray(size), limit, endian)
    copyInto(buf as AbstractMutableBuffer)
    return buf
}

/**
 * To mutable native byte buffer.
 *
 * @return Mutable native based copy of the current buffer
 */
fun ImmutableHeapBuffer.toMutableNativeByteBuffer(): MutableNativeBuffer {
    val buf = mutableNativeByteBufferOf(size, limit, endian)
    copyInto(buf as AbstractMutableBuffer)
    return buf
}

/**
 * To mutable heap byte buffer.
 *
 * @return Mutable heap based copy of the current buffer
 */
fun ImmutableNativeBuffer.toMutableByteBuffer(): MutableHeapBuffer {
    val buf = mutableByteBufferOf(size, limit, endian)
    copyInto(buf as AbstractMutableBuffer)
    return buf
}

/**
 * To mutable native byte buffer.
 *
 * @return Mutable native based copy of the current buffer
 */
fun ImmutableNativeBuffer.toMutableNativeByteBuffer(): MutableNativeBuffer {
    val buf = mutableNativeByteBufferOf(size, limit, endian)
    copyInto(buf as AbstractMutableBuffer)
    return buf
}

/**
 * To mutable native byte buffer
 *
 * @return Mutable native based copy of the current buffer
 */
fun MutableHeapBuffer.toMutableNativeByteBuffer(): MutableNativeBuffer {
    val buf = mutableNativeByteBufferOf(size, limit, endian)
    copyInto(buf as AbstractMutableBuffer)
    return buf
}

/**
 * To mutable byte buffer
 *
 * @return Mutable heap based copy of the current buffer
 */
fun MutableNativeBuffer.toMutableByteBuffer(): MutableHeapBuffer {
    val buf = mutableByteBufferOf(size, limit, endian)
    copyInto(buf as AbstractMutableBuffer)
    return buf
}
