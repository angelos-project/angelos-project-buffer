package org.angproj.io.buf

import kotlin.test.*

@Suppress("UNCHECKED_CAST")
class FloatBufferTest : AbstractArrayBufferTest<Float, FloatBuffer>() {


    @Test
    fun testBuffer() {
        testBuffer(Float.MIN_VALUE, Float.MAX_VALUE)
    }

    override fun <E> castToType(value: Long): E {
        return value.toFloat() as E
    }

    override fun <E> castToLong(value: E): Long {
        return (value as Float).toLong()
    }

    override fun <T> asBuffer(bin: Binary): T {
        return bin.asFloatBuffer() as T
    }
}