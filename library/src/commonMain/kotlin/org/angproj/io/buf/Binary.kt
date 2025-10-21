/**
 * Copyright (c) 2024 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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

import org.angproj.io.buf.seg.Segment
import org.angproj.io.buf.util.BinHex
import org.angproj.io.buf.util.unsupported
import org.angproj.sec.Uuid


public class Binary(
    segment: Segment<*>, view: Boolean = false
) : AbstractBlockBuffer(segment, view), Retrievable, Storable {

    init {
        check(!segment.isNull()) { "Null segment forbidden!" }
    }

    override fun retrieveByte(position: Int): Byte =
        segment.getByte(position)

    override fun retrieveUByte(position: Int): UByte =
        segment.getByte(position).conv2uB()

    override fun retrieveShort(position: Int): Short =
        segment.getShort(position)

    override fun retrieveUShort(position: Int): UShort =
        segment.getShort(position).conv2uS()

    override fun retrieveInt(position: Int): Int =
        segment.getInt(position)

    override fun retrieveUInt(position: Int): UInt =
        segment.getInt(position).conv2uI()

    override fun retrieveLong(position: Int): Long =
        segment.getLong(position)

    override fun retrieveULong(position: Int): ULong =
        segment.getLong(position).conv2uL()

    override fun retrieveFloat(position: Int): Float =
       segment.getInt(position).conv2F()

    override fun retrieveDouble(position: Int): Double =
        segment.getLong(position).conv2D()

    override fun storeByte(position: Int, value: Byte): Unit =
        segment.setByte(position, value)

    override fun storeUByte(position: Int, value: UByte): Unit =
        segment.setByte(position, value.conv2B())

    override fun storeShort(position: Int, value: Short): Unit =
        segment.setShort(position, value)

    override fun storeUShort(position: Int, value: UShort): Unit =
        segment.setShort(position, value.conv2S())

    override fun storeInt(position: Int, value: Int): Unit =
        segment.setInt(position, value)

    override fun storeUInt(position: Int, value: UInt): Unit =
        segment.setInt(position, value.conv2I())

    override fun storeLong(position: Int, value: Long): Unit =
        segment.setLong(position, value)

    override fun storeULong(position: Int, value: ULong): Unit =
        segment.setLong(position, value.conv2L())

    override fun storeFloat(position: Int, value: Float): Unit =
        segment.setInt(position, value.conv2I())

    override fun storeDouble(position: Int, value: Double): Unit =
        segment.setLong(position, value.conv2L())

    public fun checkSum(key: Uuid = Uuid.nil): Long = segment.checkSum(key)

    public fun isNull(): Boolean = this === nullBinary

    public companion object {
        /**
         * It is hereby prohibited to use nullBinary for explicit use.
         * */
        public val nullBinary: Binary
            get() = unsupported("Hereby prohibited to use!")
    }
}


public fun Binary.address(): TypePointer<Nothing> = segment.address().toPointer()

public fun Binary.securelyRandomize(): Unit = segment.securelyRandomize()

public fun Binary.asBinaryBuffer(): BinaryBuffer = BinaryBuffer(segment, true)
public fun Binary.asByteBuffer(): ByteBuffer = ByteBuffer(segment, true)
public fun Binary.asUByteBuffer(): UByteBuffer = UByteBuffer(segment, true)
public fun Binary.asShortBuffer(): ShortBuffer = ShortBuffer(segment, true)
public fun Binary.asUShortBuffer(): UShortBuffer = UShortBuffer(segment, true)
public fun Binary.asIntBuffer(): IntBuffer = IntBuffer(segment, true)
public fun Binary.asUIntBuffer(): UIntBuffer = UIntBuffer(segment, true)
public fun Binary.asLongBuffer(): LongBuffer = LongBuffer(segment, true)
public fun Binary.asULongBuffer(): ULongBuffer = ULongBuffer(segment, true)
public fun Binary.asFloatBuffer(): FloatBuffer = FloatBuffer(segment, true)
public fun Binary.asDoubleBuffer(): DoubleBuffer = DoubleBuffer(segment, true)

public fun Binary.asTextBuffer(): TextBuffer = TextBuffer(segment, true)
public fun Binary.asText(): Text = Text(segment, true)


public fun <E: AbstractBuffer>E.asBinary(): Binary = Binary(this.segment, true)

public fun Binary.binToHex(): Text = BufMgr.txt(this.limit * 2).also { BinHex.binToHex(this, it) }
