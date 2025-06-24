package org.angproj.io.buf

import kotlin.test.*

@Suppress("UNCHECKED_CAST")
class UByteBufferTest : AbstractArrayBufferTest<UByte, UByteBuffer>() {


    @Test
    fun testBuffer() {
        testBuffer(UByte.MIN_VALUE, UByte.MAX_VALUE)
    }

    override fun <E> castToType(value: Long): E {
        return value.toUByte() as E
    }

    override fun <E> castToLong(value: E): Long {
        return (value as UByte).toLong()
    }

    override fun <T> asBuffer(bin: Binary): T {
        return bin.asUByteBuffer() as T
    }
}