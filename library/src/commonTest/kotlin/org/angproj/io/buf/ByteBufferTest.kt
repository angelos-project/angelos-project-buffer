package org.angproj.io.buf

import kotlin.test.*

@Suppress("UNCHECKED_CAST")
class ByteBufferTest : AbstractArrayBufferTest<Byte, ByteBuffer>() {


    @Test
    fun testBuffer() {
        testBuffer(Byte.MIN_VALUE, Byte.MAX_VALUE)
    }

    override fun <E> castToType(value: Long): E {
        return value.toByte() as E
    }

    override fun <E> castToLong(value: E): Long {
        return (value as Byte).toLong()
    }

    override fun <T> asBuffer(bin: Binary): T {
        return bin.asByteBuffer() as T
    }
}