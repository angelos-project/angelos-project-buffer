package org.angproj.io.buf

import kotlin.test.*

@Suppress("UNCHECKED_CAST")
class DoubleBufferTest : AbstractArrayBufferTest<Double, DoubleBuffer>() {


    @Test
    fun testBuffer() {
        testBuffer(Double.MIN_VALUE, Double.MAX_VALUE)
    }

    override fun <E> castToType(value: Long): E {
        return value.toDouble() as E
    }

    override fun <E> castToLong(value: E): Long {
        return (value as Double).toLong()
    }

    override fun <T> asBuffer(bin: Binary): T {
        return bin.asDoubleBuffer() as T
    }
}