package org.angproj.io.buf

import org.angproj.io.buf.util.DataSize
import kotlin.test.*

class BufMgrTest {

    @Test
    fun testBinaryBufferAllocation() {
        val buf = BufMgr.binary(8)
        assertEquals(8, buf.size)
        buf.close()
    }

    @Test
    fun testByteBufferReadWrite() {
        val buf = BufMgr.byteBuf(4)
        buf[0] = 42
        assertEquals(42, buf[0])
        buf.close()
    }

    @Test
    fun testUByteBufferReadWrite() {
        val buf = BufMgr.uByteBuf(4)
        buf[0] = 255u
        assertEquals(255u, buf[0])
        buf.close()
    }

    @Test
    fun testShortBufferReadWrite() {
        val buf = BufMgr.shortBuf(2)
        buf[0] = 12345
        assertEquals(12345, buf[0])
        buf.close()
    }

    @Test
    fun testUShortBufferReadWrite() {
        val buf = BufMgr.uShortBuf(2)
        buf[0] = 65535u
        assertEquals(65535u, buf[0])
        buf.close()
    }

    @Test
    fun testIntBufferReadWrite() {
        val buf = BufMgr.intBuf(2)
        buf[0] = 0xCAFEBABE.toInt()
        assertEquals(0xCAFEBABE.toInt(), buf[0])
        buf.close()
    }

    @Test
    fun testUIntBufferReadWrite() {
        val buf = BufMgr.uIntBuf(2)
        buf[0] = 0xFFFFFFFFu
        assertEquals(0xFFFFFFFFu, buf[0])
        buf.close()
    }

    @Test
    fun testLongBufferReadWrite() {
        val buf = BufMgr.longBuf(1)
        buf[0] = 0x1122334455667788L
        assertEquals(0x1122334455667788L, buf[0])
        buf.close()
    }

    @Test
    fun testULongBufferReadWrite() {
        val buf = BufMgr.uLongBuf(1)
        buf[0] = 0xFFFFFFFFFFFFFFFFu
        assertEquals(0xFFFFFFFFFFFFFFFFu, buf[0])
        buf.close()
    }

    @Test
    fun testFloatBufferReadWrite() {
        val buf = BufMgr.floatBuf(2)
        buf[0] = 3.14f
        assertEquals(3.14f, buf[0])
        buf.close()
    }

    @Test
    fun testDoubleBufferReadWrite() {
        val buf = BufMgr.doubleBuf(2)
        buf[0] = 2.718281828459045
        assertEquals(2.718281828459045, buf[0])
        buf.close()
    }

    @Test
    fun testWithRam() {
        val result = BufMgr.withRam(DataSize._32B) { binary ->
            assertEquals(DataSize._32B.toInt(), binary.size)
            123
        }
        assertEquals(123, result)
    }
}