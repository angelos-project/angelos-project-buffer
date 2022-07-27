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

@Suppress("OVERRIDE_BY_INLINE")
actual class MutableNativeDataByteBuffer actual constructor(
    size: Int,
    limit: Int,
    endianness: Endianness,
) : AbstractMutableDataBuffer(size, limit, endianness), MutableNativeDataBuffer {
    private val _array = memScoped { nativeHeap.allocArray<ByteVar>(size) }
    private val _pointer = _array.pointed.ptr.toLong()

    override inline fun writeByte(position: Int, value: Byte) {
        (_pointer + position).toCPointer<ByteVar>()!!.pointed.value = value
    }

    override inline fun writeUByte(position: Int, value: UByte) {
        (_pointer + position).toCPointer<UByteVar>()!!.pointed.value = value
    }

    override inline fun writeChar(position: Int, value: Char) = when (reverse) {
        true -> (_pointer + position).toCPointer<ShortVar>()!!.pointed.value = value.swapEndian().code.toShort()
        false -> (_pointer + position).toCPointer<ShortVar>()!!.pointed.value = value.code.toShort()
    }

    override inline fun writeShort(position: Int, value: Short) = when (reverse) {
        true -> (_pointer + position).toCPointer<ShortVar>()!!.pointed.value = value.swapEndian()
        false -> (_pointer + position).toCPointer<ShortVar>()!!.pointed.value = value
    }

    override inline fun writeUShort(position: Int, value: UShort) = when (reverse) {
        true -> (_pointer + position).toCPointer<UShortVar>()!!.pointed.value = value.swapEndian()
        false -> (_pointer + position).toCPointer<UShortVar>()!!.pointed.value = value
    }

    override inline fun writeInt(position: Int, value: Int) = when (reverse) {
        true -> (_pointer + position).toCPointer<IntVar>()!!.pointed.value = value.swapEndian()
        false -> (_pointer + position).toCPointer<IntVar>()!!.pointed.value = value
    }

    override inline fun writeUInt(position: Int, value: UInt) = when (reverse) {
        true -> (_pointer + position).toCPointer<UIntVar>()!!.pointed.value = value.swapEndian()
        false -> (_pointer + position).toCPointer<UIntVar>()!!.pointed.value = value
    }

    override inline fun writeLong(position: Int, value: Long) = when (reverse) {
        true -> (_pointer + position).toCPointer<LongVar>()!!.pointed.value = value.swapEndian()
        false -> (_pointer + position).toCPointer<LongVar>()!!.pointed.value = value
    }

    override inline fun writeULong(position: Int, value: ULong) = when (reverse) {
        true -> (_pointer + position).toCPointer<ULongVar>()!!.pointed.value = value.swapEndian()
        false -> (_pointer + position).toCPointer<ULongVar>()!!.pointed.value = value
    }

    override inline fun writeFloat(position: Int, value: Float) = when (reverse) {
        true -> (_pointer + position).toCPointer<FloatVar>()!!.pointed.value = value.swapEndian()
        false -> (_pointer + position).toCPointer<FloatVar>()!!.pointed.value = value
    }

    override inline fun writeDouble(position: Int, value: Double) = when (reverse) {
        true -> (_pointer + position).toCPointer<DoubleVar>()!!.pointed.value = value.swapEndian()
        false -> (_pointer + position).toCPointer<DoubleVar>()!!.pointed.value = value
    }

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

    override fun usePinned(native: (ptr: TypePointer<Byte>) -> Unit) {
        _array.usePinned { native(getPointer()) }
    }

    override fun dispose() {
        memScoped { free(_array) }
    }
}