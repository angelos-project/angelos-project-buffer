package org.angproj.io.buf

import kotlin.test.*

@Suppress("UNCHECKED_CAST")
class ULongBufferTest : AbstractArrayBufferTest<ULong, ULongBuffer>() {


    @Test
    fun testBuffer() {
        testBuffer(ULong.MIN_VALUE, ULong.MAX_VALUE)
    }

    override fun <E> castToType(value: Long): E {
        return value.toULong() as E
    }

    override fun <E> castToLong(value: E): Long {
        return (value as ULong).toLong()
    }

    override fun <T> asBuffer(bin: Binary): T {
        return bin.asULongBuffer() as T
    }
}