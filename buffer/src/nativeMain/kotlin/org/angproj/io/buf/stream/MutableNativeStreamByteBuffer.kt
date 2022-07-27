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

import kotlinx.cinterop.*
import org.angproj.io.buf.Endianness
import org.angproj.io.buf.TypePointer
import org.angproj.io.buf.swapEndian
import platform.posix.free

/**
 * Mutable native byte buffer implemented outside save memory environment as mutable.
 *
 * @constructor
 *
 * @param size
 * @param limit
 * @param position
 * @param endianness
 */
@Suppress("OVERRIDE_BY_INLINE")
actual class MutableNativeStreamByteBuffer internal actual constructor(
    size: Int,
    limit: Int,
    position: Int,
    endianness: Endianness,
) : AbstractMutableStreamBuffer(size, limit, position, endianness), MutableNativeStreamBuffer {
    private val _array = memScoped { nativeHeap.allocArray<ByteVar>(size) }
    private val _pointer = _array.pointed.ptr.toLong()

    override inline fun writeByte(value: Byte) {
        (_pointer + _position).toCPointer<ByteVar>()!!.pointed.value = value
    }

    override inline fun writeUByte(value: UByte) {
        (_pointer + _position).toCPointer<UByteVar>()!!.pointed.value = value
    }

    override inline fun writeChar(value: Char) = when (reverse) {
        true -> (_pointer + _position).toCPointer<ShortVar>()!!.pointed.value = value.swapEndian().code.toShort()
        false -> (_pointer + _position).toCPointer<ShortVar>()!!.pointed.value = value.code.toShort()
    }

    override inline fun writeShort(value: Short) = when (reverse) {
        true -> (_pointer + _position).toCPointer<ShortVar>()!!.pointed.value = value.swapEndian()
        false -> (_pointer + _position).toCPointer<ShortVar>()!!.pointed.value = value
    }

    override inline fun writeUShort(value: UShort) = when (reverse) {
        true -> (_pointer + _position).toCPointer<UShortVar>()!!.pointed.value = value.swapEndian()
        false -> (_pointer + _position).toCPointer<UShortVar>()!!.pointed.value = value
    }

    override inline fun writeInt(value: Int) = when (reverse) {
        true -> (_pointer + _position).toCPointer<IntVar>()!!.pointed.value = value.swapEndian()
        false -> (_pointer + _position).toCPointer<IntVar>()!!.pointed.value = value
    }

    override inline fun writeUInt(value: UInt) = when (reverse) {
        true -> (_pointer + _position).toCPointer<UIntVar>()!!.pointed.value = value.swapEndian()
        false -> (_pointer + _position).toCPointer<UIntVar>()!!.pointed.value = value
    }

    override inline fun writeLong(value: Long) = when (reverse) {
        true -> (_pointer + _position).toCPointer<LongVar>()!!.pointed.value = value.swapEndian()
        false -> (_pointer + _position).toCPointer<LongVar>()!!.pointed.value = value
    }

    override inline fun writeULong(value: ULong) = when (reverse) {
        true -> (_pointer + _position).toCPointer<ULongVar>()!!.pointed.value = value.swapEndian()
        false -> (_pointer + _position).toCPointer<ULongVar>()!!.pointed.value = value
    }

    override inline fun writeFloat(value: Float) = when (reverse) {
        true -> (_pointer + _position).toCPointer<FloatVar>()!!.pointed.value = value.swapEndian()
        false -> (_pointer + _position).toCPointer<FloatVar>()!!.pointed.value = value
    }

    override inline fun writeDouble(value: Double) = when (reverse) {
        true -> (_pointer + _position).toCPointer<DoubleVar>()!!.pointed.value = value.swapEndian()
        false -> (_pointer + _position).toCPointer<DoubleVar>()!!.pointed.value = value
    }

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

    override fun getPointer(): TypePointer<Byte> = _pointer

    override fun usePinned(native: (ptr: TypePointer<Byte>) -> Unit) {
        _array.usePinned { native(getPointer()) }
    }

    override fun dispose() {
        memScoped { free(_array) }
    }
}