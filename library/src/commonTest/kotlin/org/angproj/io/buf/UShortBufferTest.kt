package org.angproj.io.buf

import kotlin.test.*

@Suppress("UNCHECKED_CAST")
class UShortBufferTest : AbstractArrayBufferTest<UShort, UShortBuffer>() {


    @Test
    fun testBuffer() {
        testBuffer(UShort.MIN_VALUE, UShort.MAX_VALUE)
    }

    override fun <E> castToType(value: Long): E {
        return value.toUShort() as E
    }

    override fun <E> castToLong(value: E): Long {
        return (value as UShort).toLong()
    }

    override fun <T> asBuffer(bin: Binary): T {
        return bin.asUShortBuffer() as T
    }
}