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

import kotlinx.cinterop.*
import platform.posix.*

/**
 * Native byte buffer implemented outside save memory environment as immutable.
 *
 * @constructor
 *
 * @param size
 * @param limit
 * @param position
 * @param endianness
 */
@Suppress("OVERRIDE_BY_INLINE")
actual class NativeByteBuffer internal actual constructor(
    size: Int,
    limit: Int,
    position: Int,
    endianness: Endianness,
) : AbstractBuffer(size, limit, position, endianness), ImmutableNativeBuffer {
    private val _pointer = memScoped { allocArray<ByteVar>(size).toLong() }

    override fun loadByte(index: Int): Byte = (_pointer + index).toCPointer<ByteVar>()!!.pointed.value

    override fun loadLong(index: Int): Long = (_pointer + index).toCPointer<LongVar>()!!.pointed.value

    override inline fun readByte(): Byte = (_pointer + _position).toCPointer<ByteVar>()!!.pointed.value

    override inline fun readUByte(): UByte = (_pointer + _position).toCPointer<UByteVar>()!!.pointed.value

    override inline fun readChar(): Char = when (reverse) {
        true -> (_pointer + _position).toCPointer<ShortVar>()!!.pointed.value.swapEndian()
        false -> (_pointer + _position).toCPointer<ShortVar>()!!.pointed.value
    }.toInt().toChar()

    override inline fun readShort(): Short = when (reverse) {
        true -> (_pointer + _position).toCPointer<ShortVar>()!!.pointed.value.swapEndian()
        false -> (_pointer + _position).toCPointer<ShortVar>()!!.pointed.value
    }

    override inline fun readUShort(): UShort = when (reverse) {
        true -> (_pointer + _position).toCPointer<UShortVar>()!!.pointed.value.swapEndian()
        false -> (_pointer + _position).toCPointer<UShortVar>()!!.pointed.value
    }

    override inline fun readInt(): Int = when (reverse) {
        true -> (_pointer + _position).toCPointer<IntVar>()!!.pointed.value.swapEndian()
        false -> (_pointer + _position).toCPointer<IntVar>()!!.pointed.value
    }

    override inline fun readUInt(): UInt = when (reverse) {
        true -> (_pointer + _position).toCPointer<UIntVar>()!!.pointed.value.swapEndian()
        false -> (_pointer + _position).toCPointer<UIntVar>()!!.pointed.value
    }

    override inline fun readLong(): Long = when (reverse) {
        true -> (_pointer + _position).toCPointer<LongVar>()!!.pointed.value.swapEndian()
        false -> (_pointer + _position).toCPointer<LongVar>()!!.pointed.value
    }

    override inline fun readULong(): ULong = when (reverse) {
        true -> (_pointer + _position).toCPointer<ULongVar>()!!.pointed.value.swapEndian()
        false -> (_pointer + _position).toCPointer<ULongVar>()!!.pointed.value
    }

    override inline fun readFloat(): Float = when (reverse) {
        true -> (_pointer + _position).toCPointer<FloatVar>()!!.pointed.value.swapEndian()
        false -> (_pointer + _position).toCPointer<FloatVar>()!!.pointed.value
    }

    override inline fun readDouble(): Double = when (reverse) {
        true -> (_pointer + _position).toCPointer<DoubleVar>()!!.pointed.value.swapEndian()
        false -> (_pointer + _position).toCPointer<DoubleVar>()!!.pointed.value
    }

    override fun copyInto(destination: MutableBuffer, destinationOffset: Int, startIndex: Int, endIndex: Int) = when(destination) {
        is AbstractMutableBuffer -> memScoped { copyInto(destination, destinationOffset, startIndex, endIndex) }
        else -> error("Only handles AbstractMutableBuffer.")
    }

    override fun getPointer(): TypePointer<Byte> =_pointer

    override fun dispose() { memScoped { free(_pointer.toCPointer<ByteVar>()!!.pointed.ptr) } }
}