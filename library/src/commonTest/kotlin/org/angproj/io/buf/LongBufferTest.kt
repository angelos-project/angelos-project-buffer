package org.angproj.io.buf

import kotlin.test.*

@Suppress("UNCHECKED_CAST")
class LongBufferTest : AbstractArrayBufferTest<Long, LongBuffer>() {


    @Test
    fun testBuffer() {
        testBuffer(Long.MIN_VALUE, Long.MAX_VALUE)
    }

    override fun <E> castToType(value: Long): E {
        return value.toLong() as E
    }

    override fun <E> castToLong(value: E): Long {
        return (value as Long).toLong()
    }

    override fun <T> asBuffer(bin: Binary): T {
        return bin.asLongBuffer() as T
    }
}