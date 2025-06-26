package org.angproj.io.buf

import org.angproj.aux.util.ifJvmOrNative
import org.angproj.io.buf.seg.SegmentException
import org.angproj.io.buf.util.DataSize
import org.angproj.sec.util.TypeSize
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotEquals


class BinaryTest: AbstractBlockBufferTest<Binary>() {

    override val txtLen = TestInformationStub.refArray.size

    override fun setInput(): Binary {
        val buf = BufMgr.bin(txtLen)
        TestInformationStub.refArray.forEachIndexed { index, value ->
            buf.storeByte(index, value)
        }
        return buf
    }

    @Test
    fun retrieveStoreByte() {
        val bin = setInput()
        (0 until bin.limit step 1).forEach { bin.storeByte(it, TestInformationStub.refByte) }
        (0 until bin.limit step 1).forEach { assertEquals(bin.retrieveByte(it), TestInformationStub.refByte) }
    }

    @Test
    fun retrieveStoreUByte() {
        val bin = setInput()
        (0 until bin.limit step 1).forEach { bin.storeUByte(it, TestInformationStub.refUByte) }
        (0 until bin.limit step 1).forEach { assertEquals(bin.retrieveUByte(it), TestInformationStub.refUByte) }
    }

    @Test
    fun retrieveStoreShort() {
        val bin = setInput()
        (0 until bin.limit step 2).forEach { bin.storeShort(it, TestInformationStub.refShort) }
        (0 until bin.limit step 2).forEach { assertEquals(bin.retrieveShort(it), TestInformationStub.refShort) }
    }

    @Test
    fun retrieveStoreUShort() {
        val bin = setInput()
        (0 until bin.limit step 2).forEach { bin.storeUShort(it, TestInformationStub.refUShort) }
        (0 until bin.limit step 2).forEach { assertEquals(bin.retrieveUShort(it), TestInformationStub.refUShort) }
    }

    @Test
    fun retrieveStoreInt() {
        val bin = setInput()
        (0 until bin.limit step 4).forEach { bin.storeInt(it, TestInformationStub.refInt) }
        (0 until bin.limit step 4).forEach { assertEquals(bin.retrieveInt(it), TestInformationStub.refInt) }
    }

    @Test
    fun retrieveStoreUInt() {
        val bin = setInput()
        (0 until bin.limit step 4).forEach { bin.storeUInt(it, TestInformationStub.refUInt) }
        (0 until bin.limit step 4).forEach { assertEquals(bin.retrieveUInt(it), TestInformationStub.refUInt) }
    }

    @Test
    fun retrieveStoreLong() {
        val bin = setInput()
        (0 until bin.limit step 8).forEach { bin.storeLong(it, TestInformationStub.refLong) }
        (0 until bin.limit step 8).forEach { assertEquals(bin.retrieveLong(it), TestInformationStub.refLong) }
    }

    @Test
    fun retrieveStoreULong() {
        val bin = setInput()
        (0 until bin.limit step 8).forEach { bin.storeULong(it, TestInformationStub.refULong) }
        (0 until bin.limit step 8).forEach { assertEquals(bin.retrieveULong(it), TestInformationStub.refULong) }
    }

    @Test
    fun retrieveStoreFloat() {
        val bin = setInput()
        (0 until bin.limit step 4).forEach { bin.storeFloat(it, TestInformationStub.refFloat) }
        (0 until bin.limit step 4).forEach { assertEquals(bin.retrieveFloat(it), TestInformationStub.refFloat) }
    }

    @Test
    fun retrieveStoreDouble() {
        val bin = setInput()
        (0 until bin.limit step 8).forEach { bin.storeDouble(it, TestInformationStub.refDouble) }
        (0 until bin.limit step 8).forEach { assertEquals(bin.retrieveDouble(it), TestInformationStub.refDouble) }
    }

    @Test
    fun testNullBinary() {
        assertFailsWith<UnsupportedOperationException> { Binary.nullBinary }
        assertFailsWith<UnsupportedOperationException> { BufMgr.bin(0).isNull() }
    }

    @Test
    fun byteRWOutbound() {
        val m = setInput()

        m.retrieveByte(0)
        assertFailsWith<SegmentException> {
            m.retrieveByte(-1)
        }

        m.retrieveByte(m.limit-TypeSize.byteSize) // Won't crash
        assertFailsWith<SegmentException> {
            m.retrieveByte(m.limit) // Must throw
        }

        m.storeByte(0, 1)
        assertFailsWith<SegmentException> {
            m.storeByte(-1, 1)
        }

        m.storeByte(m.limit-TypeSize.byteSize, 0) // Won't crash
        assertFailsWith<SegmentException> {
            m.storeByte(m.limit, 1) // Must throw
        }
    }

    @Test
    fun shortRWOutbound() {
        val m = setInput()

        m.retrieveShort(0)
        assertFailsWith<SegmentException> {
            m.retrieveShort(-1)
        }

        m.retrieveShort(m.limit-TypeSize.shortSize) // Won't crash
        assertFailsWith<SegmentException> {
            m.retrieveShort(m.limit - 1) // Must throw
        }

        m.storeShort(0, 1)
        assertFailsWith<SegmentException> {
            m.storeShort(-1, 1)
        }

        m.storeShort(m.limit-TypeSize.shortSize, 0) // Won't crash
        assertFailsWith<SegmentException> {
            m.storeShort(m.limit - 1, 1) // Must throw
        }
    }

    @Test
    fun intRWOutbound() {
        val m = setInput()

        m.retrieveInt(0)
        assertFailsWith<SegmentException> {
            m.retrieveInt(-1)
        }

        m.retrieveInt(m.limit-TypeSize.intSize) // Won't crash
        assertFailsWith<SegmentException> {
            m.retrieveInt(m.limit - 3) // Must throw
        }

        m.storeInt(0, 1)
        assertFailsWith<SegmentException> {
            m.storeInt(-1, 1)
        }

        m.storeInt(m.limit-TypeSize.intSize, 0) // Won't crash
        assertFailsWith<SegmentException> {
            m.storeInt(m.limit - 3, 1) // Must throw
        }
    }

    @Test
    fun longRWOutbound() {
        val m = setInput()

        m.retrieveLong(0)
        assertFailsWith<SegmentException> {
            m.retrieveLong(-1)
        }

        m.retrieveLong(m.limit-TypeSize.longSize) // Won't crash
        assertFailsWith<SegmentException> {
            m.retrieveLong(m.limit - 7) // Must throw
        }

        m.storeLong(0, 1)
        assertFailsWith<SegmentException> {
            m.storeLong(-1, 1)
        }

        m.storeLong(m.limit- TypeSize.longSize, 0) // Won't crash
        assertFailsWith<SegmentException> {
            m.storeLong(m.limit - 7, 1) // Must throw
        }
    }

    @Test
    fun testCheckSum() {
        val bin = setInput()
        val expectedCheckSum = TestInformationStub.refArray.sumOf { it.toLong() }
        assertNotEquals(expectedCheckSum, bin.checkSum())
    }

    @Test
    fun testAddress(): Unit = ifJvmOrNative {
        BufMgr.withRam(DataSize._32B) { binary ->
            assertNotEquals(0, binary.address().ptr)
            assertEquals(DataSize._32B.toInt(), binary.size)
        }
    }
}