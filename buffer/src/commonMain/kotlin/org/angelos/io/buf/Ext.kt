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
 * Swap endian on Short.
 *
 * @return
 */
inline fun Short.swapEndian(): Short = reverseShort(this)

/**
 * Swap endian on UShort.
 *
 * @return
 */
inline fun UShort.swapEndian(): UShort = reverseShort(this.toShort()).toUShort()

/**
 * Swap endian on Char.
 *
 * @return
 */
inline fun Char.swapEndian(): Char = reverseShort(this.code.toShort()).toInt().toChar()

/**
 * Swap endian on Int.
 *
 * @return
 */
inline fun Int.swapEndian(): Int = reverseInt(this)

/**
 * Swap endian on UInt.
 *
 * @return
 */
inline fun UInt.swapEndian(): UInt = reverseInt(this.toInt()).toUInt()

/**
 * Swap endian on Long.
 *
 * @return
 */
inline fun Long.swapEndian(): Long = reverseLong(this)

/**
 * Swap endian on ULong.
 *
 * @return
 */
inline fun ULong.swapEndian(): ULong = reverseLong(this.toLong()).toULong()

/**
 * Swap endian on Float.
 *
 * @return
 */
inline fun Float.swapEndian(): Float = Float.fromBits(reverseInt(this.toBits()))

/**
 * Swap endian on Double.
 *
 * @return
 */
inline fun Double.swapEndian(): Double = Double.fromBits(reverseLong(this.toBits()))

/**
 * Read Short at offset.
 *
 * @param offset
 * @return
 */
inline fun ByteArray.readShortAt(offset: Int): Short = (
        (this[offset + 1].toInt() shl 8 and 0xFF00) or (this[offset + 0].toInt() and 0xFF)
        ).toShort()

/**
 * Read UShort at offset.
 *
 * @param offset
 * @return
 */
inline fun ByteArray.readUShortAt(offset: Int): UShort = readShortAt(offset).toUShort()

/**
 * Read Char at offset.
 *
 * @param offset
 * @return
 */
inline fun ByteArray.readCharAt(offset: Int): Char = readShortAt(offset).toInt().toChar()

/**
 * Read Int at offset.
 *
 * @param offset
 * @return
 */
inline fun ByteArray.readIntAt(offset: Int): Int = (this[offset + 3].toInt() shl 24 and -0x1000000) or
        (this[offset + 2].toInt() shl 16 and 0xFF0000) or
        (this[offset + 1].toInt() shl 8 and 0xFF00) or
        (this[offset + 0].toInt() and 0xFF)

/**
 * Read UInt at offset.
 *
 * @param offset
 * @return
 */
inline fun ByteArray.readUIntAt(offset: Int): UInt = readIntAt(offset).toUInt()

/**
 * Read Long at offset.
 *
 * @param offset
 * @return
 */
inline fun ByteArray.readLongAt(offset: Int): Long = (this[offset + 7].toLong() shl 56 and -0x1000000_00000000) or
        (this[offset + 6].toLong() shl 48 and 0xFF0000_00000000) or
        (this[offset + 5].toLong() shl 40 and 0xFF00_00000000) or
        (this[offset + 4].toLong() shl 32 and 0xFF_00000000) or
        (this[offset + 3].toLong() shl 24 and 0xFF000000) or
        (this[offset + 2].toLong() shl 16 and 0xFF0000) or
        (this[offset + 1].toLong() shl 8 and 0xFF00) or
        (this[offset + 0].toLong() and 0xFF)

/**
 * Read ULong at offset.
 *
 * @param offset
 * @return
 */
inline fun ByteArray.readULongAt(offset: Int): ULong = readLongAt(offset).toULong()

/**
 * Read Float at offset.
 *
 * @param offset
 * @return
 */
inline fun ByteArray.readFloatAt(offset: Int): Float = Float.fromBits(readIntAt(offset))

/**
 * Read Double at offset.
 *
 * @param offset
 * @return
 */
inline fun ByteArray.readDoubleAt(offset: Int): Double = Double.fromBits(readLongAt(offset))

/**
 * Write Short at offset.
 *
 * @param offset
 * @param value
 */
inline fun ByteArray.writeShortAt(offset: Int, value: Short) {
    this[offset + 1] = (value.toInt() shr 8 and 0xFF).toByte()
    this[offset] = (value.toInt() and 0xFF).toByte()
}

/**
 * Write UShort at offset.
 *
 * @param offset
 * @param value
 */
inline fun ByteArray.writeUShortAt(offset: Int, value: UShort) = writeShortAt(offset, value.toShort())

/**
 * Write Char at offset.
 *
 * @param offset
 * @param value
 */
inline fun ByteArray.writeCharAt(offset: Int, value: Char) = writeShortAt(offset, value.code.toShort())

/**
 * Write Int at offset.
 *
 * @param offset
 * @param value
 */
inline fun ByteArray.writeIntAt(offset: Int, value: Int) {
    this[offset + 3] = (value shr 24 and 0xFF).toByte()
    this[offset + 2] = (value shr 16 and 0xFF).toByte()
    this[offset + 1] = (value shr 8 and 0xFF).toByte()
    this[offset] = (value and 0xFF).toByte()
}

/**
 * Write UInt at offset.
 *
 * @param offset
 * @param value
 */
inline fun ByteArray.writeUIntAt(offset: Int, value: UInt) = writeIntAt(offset, value.toInt())

/**
 * Write Long at offset.
 *
 * @param offset
 * @param value
 */
inline fun ByteArray.writeLongAt(offset: Int, value: Long) {
    this[offset + 7] = (value shr 56 and 0xFF).toByte()
    this[offset + 6] = (value shr 48 and 0xFF).toByte()
    this[offset + 5] = (value shr 40 and 0xFF).toByte()
    this[offset + 4] = (value shr 32 and 0xFF).toByte()
    this[offset + 3] = (value shr 24 and 0xFF).toByte()
    this[offset + 2] = (value shr 16 and 0xFF).toByte()
    this[offset + 1] = (value shr 8 and 0xFF).toByte()
    this[offset] = (value and 0xFF).toByte()
}

/**
 * Write ULong at offset.
 *
 * @param offset
 * @param value
 */
inline fun ByteArray.writeULongAt(offset: Int, value: ULong) = writeLongAt(offset, value.toLong())

/**
 * Write Float at offset.
 *
 * @param offset
 * @param value
 */
inline fun ByteArray.writeFloatAt(offset: Int, value: Float) = writeIntAt(offset, value.toBits())

/**
 * Write Double at offset.
 *
 * @param offset
 * @param value
 */
inline fun ByteArray.writeDoubleAt(offset: Int, value: Double) = writeLongAt(offset, value.toBits())

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