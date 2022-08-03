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

import kotlinx.cinterop.*
import org.angproj.io.buf.Endianness
import org.angproj.io.buf.TypePointer
import org.angproj.io.buf.swapEndian
import platform.posix.free

/**
 * The Kotlin/Native implementation of the NativeDataByteBuffer class uses a ByteVar array to access the
 * underlying memory using C pointer arithmetic. The memory is allocated and freed using kotlinx.cinterop.
 *
 * @constructor
 *
 * @param size
 * @param limit
 * @param endianness
 */
@Suppress("OVERRIDE_BY_INLINE")
actual class NativeDataByteBuffer internal actual constructor(
    size: Int,
    limit: Int,
    endianness: Endianness,
) : AbstractDataBuffer(size, limit, endianness), ImmutableNativeDataBuffer {
    private val _array = memScoped { nativeHeap.allocArray<ByteVar>(size) }
    private val _pointer = _array.pointed.ptr.toLong()

    override inline fun readByte(position: Int): Byte = (_pointer + position).toCPointer<ByteVar>()!!.pointed.value

    override inline fun readUByte(position: Int): UByte = (_pointer + position).toCPointer<UByteVar>()!!.pointed.value

    override inline fun readChar(position: Int): Char = when (reverse) {
        true -> (_pointer + position).toCPointer<ShortVar>()!!.pointed.value.swapEndian()
        false -> (_pointer + position).toCPointer<ShortVar>()!!.pointed.value
    }.toInt().toChar()

    override inline fun readShort(position: Int): Short = when (reverse) {
        true -> (_pointer + position).toCPointer<ShortVar>()!!.pointed.value.swapEndian()
        false -> (_pointer + position).toCPointer<ShortVar>()!!.pointed.value
    }

    override inline fun readUShort(position: Int): UShort = when (reverse) {
        true -> (_pointer + position).toCPointer<UShortVar>()!!.pointed.value.swapEndian()
        false -> (_pointer + position).toCPointer<UShortVar>()!!.pointed.value
    }

    override inline fun readInt(position: Int): Int = when (reverse) {
        true -> (_pointer + position).toCPointer<IntVar>()!!.pointed.value.swapEndian()
        false -> (_pointer + position).toCPointer<IntVar>()!!.pointed.value
    }

    override inline fun readUInt(position: Int): UInt = when (reverse) {
        true -> (_pointer + position).toCPointer<UIntVar>()!!.pointed.value.swapEndian()
        false -> (_pointer + position).toCPointer<UIntVar>()!!.pointed.value
    }

    override inline fun readLong(position: Int): Long = when (reverse) {
        true -> (_pointer + position).toCPointer<LongVar>()!!.pointed.value.swapEndian()
        false -> (_pointer + position).toCPointer<LongVar>()!!.pointed.value
    }

    override inline fun readULong(position: Int): ULong = when (reverse) {
        true -> (_pointer + position).toCPointer<ULongVar>()!!.pointed.value.swapEndian()
        false -> (_pointer + position).toCPointer<ULongVar>()!!.pointed.value
    }

    override inline fun readFloat(position: Int): Float = when (reverse) {
        true -> (_pointer + position).toCPointer<FloatVar>()!!.pointed.value.swapEndian()
        false -> (_pointer + position).toCPointer<FloatVar>()!!.pointed.value
    }

    override inline fun readDouble(position: Int): Double = when (reverse) {
        true -> (_pointer + position).toCPointer<DoubleVar>()!!.pointed.value.swapEndian()
        false -> (_pointer + position).toCPointer<DoubleVar>()!!.pointed.value
    }

    override fun getPointer(): TypePointer<Byte> = _pointer

    override fun dispose() {
        memScoped { free(_array) }
    }
}