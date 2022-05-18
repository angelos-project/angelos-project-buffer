package org.angelos.io.buf

import kotlinx.cinterop.*
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
actual class MutableNativeByteBuffer internal actual constructor(
    size: Int,
    limit: Int,
    position: Int,
    endianness: Endianness,
) : AbstractMutableBuffer(size, limit, position, endianness), MutableNativeBuffer {
    private val _pointer = memScoped { allocArray<ByteVar>(size).toLong() }

    override fun saveByte(index: Int, value: Byte) { _pointer.toCPointer<ByteVar>()!!.set(index, value) }
    //(_pointer + index).toCPointer<ByteVar>()!!.pointed.value = value

    override fun saveLong(index: Int, value: Long) { _pointer.toCPointer<LongVar>()!!.set(index, value) }
    // (_pointer + index).toCPointer<LongVar>()!!.pointed.value = value

    override inline fun writeByte(value: Byte) { (_pointer + _position).toCPointer<ByteVar>()!!.pointed.value = value }

    override inline fun writeUByte(value: UByte) { (_pointer + _position).toCPointer<UByteVar>()!!.pointed.value = value }

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