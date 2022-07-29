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
package org.angproj.io.buf

import kotlin.experimental.and
import kotlin.experimental.or

/**
 * Setting bit 0.
 *
 * @return
 */
inline fun Byte.flipOnFlag0(): Byte = this or 0B00000001

/**
 * Setting bit 1.
 *
 * @return
 */
inline fun Byte.flipOnFlag1(): Byte = this or 0B00000010

/**
 * Setting bit 2.
 *
 * @return
 */
inline fun Byte.flipOnFlag2(): Byte = this or 0B00000100

/**
 * Setting bit 3.
 *
 * @return
 */
inline fun Byte.flipOnFlag3(): Byte = this or 0B00001000

/**
 * Setting bit 4.
 *
 * @return
 */
inline fun Byte.flipOnFlag4(): Byte = this or 0B00010000

/**
 * Setting bit 5.
 *
 * @return
 */
inline fun Byte.flipOnFlag5(): Byte = this or 0B00100000

/**
 * Setting bit 6.
 *
 * @return
 */
inline fun Byte.flipOnFlag6(): Byte = this or 0B01000000

/**
 * Setting bit 7.
 *
 * @return
 */
inline fun Byte.flipOnFlag7(): Byte = this or -0B10000000

/**
 * Clearing bit 0.
 *
 * @return
 */
inline fun Byte.flipOffFlag0(): Byte = this and 0B11111110.toByte()

/**
 * Clearing bit 1.
 *
 * @return
 */
inline fun Byte.flipOffFlag1(): Byte = this and 0B11111101.toByte()

/**
 * Clearing bit 2.
 *
 * @return
 */
inline fun Byte.flipOffFlag2(): Byte = this and 0B11111011.toByte()

/**
 * Clearing bit 3.
 *
 * @return
 */
inline fun Byte.flipOffFlag3(): Byte = this and 0B11110111.toByte()

/**
 * Clearing bit 4.
 *
 * @return
 */
inline fun Byte.flipOffFlag4(): Byte = this and 0B11101111.toByte()

/**
 * Clearing bit 5.
 *
 * @return
 */
inline fun Byte.flipOffFlag5(): Byte = this and 0B11011111.toByte()

/**
 * Clearing bit 6.
 *
 * @return
 */
inline fun Byte.flipOffFlag6(): Byte = this and 0B10111111.toByte()

/**
 * Clearing bit 7.
 *
 * @return
 */
inline fun Byte.flipOffFlag7(): Byte = this and 0B01111111.toByte()

/**
 * Verify state of bit 0.
 *
 * @return
 */
inline fun Byte.checkFlag0(): Boolean = (this and 0B00000001).toInt() == 1

/**
 * Verify state of bit 1.
 *
 * @return
 */
inline fun Byte.checkFlag1(): Boolean = (this and 0B00000010).toInt() == 2

/**
 * Verify state of bit 2.
 *
 * @return
 */
inline fun Byte.checkFlag2(): Boolean = (this and 0B00000100).toInt() == 4

/**
 * Verify state of bit 3.
 *
 * @return
 */
inline fun Byte.checkFlag3(): Boolean = (this and 0B00001000).toInt() == 8

/**
 * Verify state of bit 4.
 *
 * @return
 */
inline fun Byte.checkFlag4(): Boolean = (this and 0B00010000).toInt() == 16

/**
 * Verify state of bit 5.
 *
 * @return
 */
inline fun Byte.checkFlag5(): Boolean = (this and 0B00100000).toInt() == 32

/**
 * Verify state of bit 6.
 *
 * @return
 */
inline fun Byte.checkFlag6(): Boolean = (this and 0B01000000).toInt() == 64

/**
 * Verify state of bit 7.
 *
 * @return
 */
inline fun Byte.checkFlag7(): Boolean = (this and -0B10000000).toInt() == -128

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

