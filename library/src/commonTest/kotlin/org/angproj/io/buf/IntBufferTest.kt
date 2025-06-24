package org.angproj.io.buf

import kotlin.test.*

@Suppress("UNCHECKED_CAST")
class IntBufferTest : AbstractArrayBufferTest<Int, IntBuffer>() {


    @Test
    fun testBuffer() {
        testBuffer(Int.MIN_VALUE, Int.MAX_VALUE)
    }

    override fun <E> castToType(value: Long): E {
        return value.toInt() as E
    }

    override fun <E> castToLong(value: E): Long {
        return (value as Int).toLong()
    }

    override fun <T> asBuffer(bin: Binary): T {
        return bin.asIntBuffer() as T
    }
}