package org.angproj.io.buf

import kotlin.test.*

@Suppress("UNCHECKED_CAST")
class UIntBufferTest : AbstractArrayBufferTest<UInt, UIntBuffer>() {


    @Test
    fun testBuffer() {
        testBuffer(UInt.MIN_VALUE, UInt.MAX_VALUE)
    }

    override fun <E> castToType(value: Long): E {
        return value.toUInt() as E
    }

    override fun <E> castToLong(value: E): Long {
        return (value as UInt).toLong()
    }

    override fun <T> asBuffer(bin: Binary): T {
        return bin.asUIntBuffer() as T
    }
}