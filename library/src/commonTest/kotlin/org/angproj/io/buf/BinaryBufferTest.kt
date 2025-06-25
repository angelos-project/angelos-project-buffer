/**
 * Copyright (c) 2024-2025 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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

import org.angproj.io.buf.seg.SegmentException
import org.angproj.io.buf.util.DataSize
import org.angproj.sec.util.TypeSize
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith


class BinaryBufferTest: AbstractFlowBufferTest<BinaryBuffer>() {

    override fun setInput(): BinaryBuffer {
        val buf = BufMgr.bin(DataSize._1K.toInt()).asBinaryBuffer()

        buf.writeByte(TestInformationStub.refByte)
        buf.writeUByte(TestInformationStub.refUByte)
        buf.writeShort(TestInformationStub.refShort)
        buf.writeUShort(TestInformationStub.refUShort)
        buf.writeInt(TestInformationStub.refInt)
        buf.writeUInt(TestInformationStub.refUInt)
        buf.writeLong(TestInformationStub.refLong)
        buf.writeULong(TestInformationStub.refULong)
        buf.writeFloat(TestInformationStub.refFloat)
        buf.writeDouble(TestInformationStub.refDouble)

        return buf
    }

    override val posValue: Int = 42

    @Test
    fun readWriteByte() {
        val buf = setInput()
        buf.reset()
        buf.writeByte(TestInformationStub.refByte)
        buf.flip()
        assertEquals(buf.readByte(), TestInformationStub.refByte)
    }

    @Test
    fun readWriteUByte() {
        val buf = setInput()
        buf.reset()
        buf.writeUByte(TestInformationStub.refUByte)
        buf.flip()
        assertEquals(buf.readUByte(), TestInformationStub.refUByte)
    }

    @Test
    fun readWriteShort() {
        val buf = setInput()
        buf.reset()
        buf.writeShort(TestInformationStub.refShort)
        buf.flip()
        assertEquals(buf.readShort(), TestInformationStub.refShort)
    }

    @Test
    fun readWriteUShort() {
        val buf = setInput()
        buf.reset()
        buf.writeUShort(TestInformationStub.refUShort)
        buf.flip()
        assertEquals(buf.readUShort(), TestInformationStub.refUShort)
    }

    @Test
    fun readWriteInt() {
        val buf = setInput()
        buf.reset()
        buf.writeInt(TestInformationStub.refInt)
        buf.flip()
        assertEquals(buf.readInt(), TestInformationStub.refInt)
    }

    @Test
    fun readWriteUInt() {
        val buf = setInput()
        buf.reset()
        buf.writeUInt(TestInformationStub.refUInt)
        buf.flip()
        assertEquals(buf.readUInt(), TestInformationStub.refUInt)
    }

    @Test
    fun readWriteLong() {
        val buf = setInput()
        buf.reset()
        buf.writeLong(TestInformationStub.refLong)
        buf.flip()
        assertEquals(buf.readLong(), TestInformationStub.refLong)
    }

    @Test
    fun readWriteULong() {
        val buf = setInput()
        buf.reset()
        buf.writeULong(TestInformationStub.refULong)
        buf.flip()
        assertEquals(buf.readULong(), TestInformationStub.refULong)
    }

    @Test
    fun readWriteFloat() {
        val buf = setInput()
        buf.reset()
        buf.writeFloat(TestInformationStub.refFloat)
        buf.flip()
        assertEquals(buf.readFloat(), TestInformationStub.refFloat)
    }

    @Test
    fun readWriteDouble() {
        val buf = setInput()
        buf.reset()
        buf.writeDouble(TestInformationStub.refDouble)
        buf.flip()
        assertEquals(buf.readDouble(), TestInformationStub.refDouble)
    }

    @Test
    fun toBinary() {
        val buf = setInput()
        buf.flip()
        val bin = buf.asBinary()

        assertEquals(buf.segment.checkSum(), bin.segment.checkSum())
    }

    @Test
    fun byteRWOutbound() {
        val m = setInput()

        m.readByte()
        assertFailsWith<IllegalArgumentException> {
            m.positionAt(-1)
            m.readByte()
        }

        m.positionAt(m.limit - TypeSize.byteSize)
        m.readByte() // Won't crash
        assertFailsWith<SegmentException> {
            m.positionAt(m.limit)
            m.readByte() // Must throw
        }

        m.positionAt(0)
        m.writeByte(1)
        assertFailsWith<IllegalArgumentException> {
            m.positionAt(-1)
            m.writeByte(1)
        }

        m.positionAt(m.limit - TypeSize.byteSize)
        m.writeByte(0) // Won't crash
        assertFailsWith<SegmentException> {
            m.positionAt(m.limit)
            m.writeByte(1) // Must throw
        }
    }

    @Test
    fun shortRWOutbound() {
        val m = setInput()

        m.readShort()
        assertFailsWith<IllegalArgumentException> {
            m.positionAt(-1)
            m.readShort()
        }

        m.positionAt(m.limit - TypeSize.shortSize)
        m.readShort() // Won't crash
        assertFailsWith<SegmentException> {
            m.positionAt(m.limit-1)
            m.readShort() // Must throw
        }

        m.positionAt(0)
        m.writeShort(1)
        assertFailsWith<IllegalArgumentException> {
            m.positionAt(-1)
            m.writeShort(1)
        }

        m.positionAt(m.limit - TypeSize.shortSize)
        m.writeShort(0) // Won't crash
        assertFailsWith<SegmentException> {
            m.positionAt(m.limit-1)
            m.writeShort(1) // Must throw
        }
    }

    @Test
    fun intRWOutbound() {
        val m = setInput()

        m.readInt()
        assertFailsWith<IllegalArgumentException> {
            m.positionAt(-1)
            m.readInt()
        }

        m.positionAt(m.limit - TypeSize.intSize)
        m.readInt() // Won't crash
        assertFailsWith<SegmentException> {
            m.positionAt(m.limit-3)
            m.readInt() // Must throw
        }

        m.positionAt(0)
        m.writeInt(1)
        assertFailsWith<IllegalArgumentException> {
            m.positionAt(-1)
            m.writeInt(1)
        }

        m.positionAt(m.limit - TypeSize.intSize)
        m.writeInt(0) // Won't crash
        assertFailsWith<SegmentException> {
            m.positionAt(m.limit-3)
            m.writeInt(1) // Must throw
        }
    }

    @Test
    fun longRWOutbound() {
        val m = setInput()

        m.readLong()
        assertFailsWith<IllegalArgumentException> {
            m.positionAt(-1)
            m.readLong()
        }

        m.positionAt(m.limit - TypeSize.longSize)
        m.readLong() // Won't crash
        assertFailsWith<SegmentException> {
            m.positionAt(m.limit-7)
            m.readLong() // Must throw
        }

        m.positionAt(0)
        m.writeLong(1)
        assertFailsWith<IllegalArgumentException> {
            m.positionAt(-1)
            m.writeLong(-1)
        }

        m.positionAt(m.limit - TypeSize.longSize)
        m.writeLong(0) // Won't crash
        assertFailsWith<SegmentException> {
            m.positionAt(m.limit-7)
            m.writeLong(1) // Must throw
        }
    }
}