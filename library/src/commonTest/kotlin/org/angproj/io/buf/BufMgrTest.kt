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
    fun testShortBufferReadWrite() {
        val buf = BufMgr.shortBuf(2)
        buf[0] = 12345
        assertEquals(12345, buf[0])
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
    fun testLongBufferReadWrite() {
        val buf = BufMgr.longBuf(1)
        buf[0] = 0x1122334455667788L
        assertEquals(0x1122334455667788L, buf[0])
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