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
import org.angproj.sec.util.TypeSize


public class BinaryBuffer internal constructor(
    segment: Segment<*>, view: Boolean = false, endian: Platform.ENDIAN
): AbstractFlowBuffer(segment, view, endian), BinaryReadable, BinaryWritable {

    override fun readByte(): Byte =
        segment.getByte(_position).also { _position += TypeSize.byteSize }

    override fun readUByte(): UByte =
        segment.getByte(_position).conv2uB().also { _position += TypeSize.uByteSize }

    override fun readShort(): Short =
        segment.getShort(_position, _isRevOrder).also { _position += TypeSize.shortSize }

    override fun readUShort(): UShort =
        segment.getShort(_position, _isRevOrder).conv2uS().also { _position += TypeSize.uShortSize }

    override fun readInt(): Int =
        segment.getInt(_position, _isRevOrder).also { _position += TypeSize.intSize }

    override fun readUInt(): UInt =
        segment.getInt(_position, _isRevOrder).conv2uI().also { _position += TypeSize.uIntSize }

    override fun readLong(): Long =
        segment.getLong(_position, _isRevOrder).also { _position += TypeSize.longSize }

    override fun readULong(): ULong =
        segment.getLong(_position, _isRevOrder).conv2uL().also { _position += TypeSize.uLongSize }

    override fun readFloat(): Float =
        segment.getInt(_position, _isRevOrder).conv2F().also { _position += TypeSize.floatSize }

    override fun readDouble(): Double =
        segment.getLong(_position, _isRevOrder).conv2D().also { _position += TypeSize.doubleSize }

    override fun writeByte(value: Byte): Unit =
        segment.setByte(_position, value).also { _position += TypeSize.byteSize }

    override fun writeUByte(value: UByte): Unit =
        segment.setByte(_position, value.conv2B()).also { _position += TypeSize.uByteSize }

    override fun writeShort(value: Short): Unit =
        segment.setShort(_position, value, _isRevOrder).also { _position += TypeSize.shortSize }

    override fun writeUShort(value: UShort): Unit =
        segment.setShort(_position, value.conv2S(), _isRevOrder).also { _position += TypeSize.uShortSize }

    override fun writeInt(value: Int): Unit =
        segment.setInt(_position, value, _isRevOrder).also { _position += TypeSize.intSize }

    override fun writeUInt(value: UInt): Unit =
        segment.setInt(_position, value.conv2I(), _isRevOrder).also { _position += TypeSize.uIntSize }

    override fun writeLong(value: Long): Unit =
        segment.setLong(_position, value, _isRevOrder).also { _position += TypeSize.longSize }

    override fun writeULong(value: ULong): Unit =
        segment.setLong(_position, value.conv2L(), _isRevOrder).also { _position += TypeSize.uLongSize }

    override fun writeFloat(value: Float): Unit =
        segment.setInt(_position, value.conv2I(), _isRevOrder).also { _position += TypeSize.floatSize }

    override fun writeDouble(value: Double): Unit =
        segment.setLong(_position, value.conv2L(), _isRevOrder).also { _position += TypeSize.longSize }
}