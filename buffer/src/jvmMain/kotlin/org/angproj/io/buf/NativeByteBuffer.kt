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
package org.angproj.io.buf

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
actual class NativeByteBuffer internal actual constructor(
    size: Int,
    limit: Int,
    position: Int,
    endianness: Endianness,
) : AbstractBuffer(size, limit, position, endianness), ImmutableNativeBuffer {
    private val _pointer = Internals.unsafe.allocateMemory(size.toLong())

    override fun loadByte(index: Int): Byte = Internals.unsafe.getByte(_pointer + index)

    override fun loadLong(index: Int): Long = Internals.unsafe.getLong(_pointer + index)

    override fun readByte(): Byte = Internals.unsafe.getByte(_pointer + _position)

    override fun readUByte(): UByte = Internals.unsafe.getByte(_pointer + _position).toUByte()

    override fun readChar(): Char = when (reverse) {
        true -> Internals.unsafe.getChar(_pointer + _position).swapEndian()
        false -> Internals.unsafe.getChar(_pointer + _position)
    }

    override fun readShort(): Short = when (reverse) {
        true -> Internals.unsafe.getShort(_pointer + _position).swapEndian()
        false -> Internals.unsafe.getShort(_pointer + _position)
    }

    override fun readUShort(): UShort = when (reverse) {
        true -> Internals.unsafe.getShort(_pointer + _position).swapEndian()
        false -> Internals.unsafe.getShort(_pointer + _position)
    }.toUShort()

    override fun readInt(): Int = when (reverse) {
        true -> Internals.unsafe.getInt(_pointer + _position).swapEndian()
        false -> Internals.unsafe.getInt(_pointer + _position)
    }

    override fun readUInt(): UInt = when (reverse) {
        true -> Internals.unsafe.getInt(_pointer + _position).swapEndian()
        false -> Internals.unsafe.getInt(_pointer + _position)
    }.toUInt()

    override fun readLong(): Long = when (reverse) {
        true -> Internals.unsafe.getLong(_pointer + _position).swapEndian()
        false -> Internals.unsafe.getLong(_pointer + _position)
    }

    override fun readULong(): ULong = when (reverse) {
        true -> Internals.unsafe.getLong(_pointer + _position).swapEndian()
        false -> Internals.unsafe.getLong(_pointer + _position)
    }.toULong()

    override fun readFloat(): Float = when (reverse) {
        true -> Internals.unsafe.getFloat(_pointer + _position).swapEndian()
        false -> Internals.unsafe.getFloat(_pointer + _position)
    }

    override fun readDouble(): Double = when (reverse) {
        true -> Internals.unsafe.getDouble(_pointer + _position).swapEndian()
        false -> Internals.unsafe.getDouble(_pointer + _position)
    }

    override fun copyInto(destination: MutableBuffer, destinationOffset: Int, startIndex: Int, endIndex: Int) =
        when (destination) {
            is AbstractMutableBuffer -> copyInto(destination, destinationOffset, startIndex, endIndex)
            else -> error("Only handles AbstractMutableBuffer.")
        }

    override fun getPointer(): TypePointer<Byte> = _pointer
    override fun usePinned(native: (ptr: TypePointer<Byte>) -> Unit) {
        native(getPointer())
    }

    override fun dispose() {
        Internals.unsafe.freeMemory(_pointer)
    }

    protected fun finalize() {
        dispose()
    }
}