package org.angproj.io.buf

import org.angproj.io.buf.seg.SegmentException
import org.angproj.io.buf.util.DataSize
import org.angproj.io.buf.util.toInt
import org.angproj.io.buf.util.useWith
import kotlin.test.*

abstract class AbstractArrayBufferTest<E, T: ArrayBuffer<E>> {

    abstract fun<E> castToType(value: Long): E
    abstract fun<E> castToLong(value: E): Long
    abstract fun<T> asBuffer(bin: Binary): T

    fun<E> testBuffer(minVal: E, maxVal: E){
        BufMgr.bin(DataSize._1K.toInt()).useWith {
            val buffer: ArrayBuffer<E> = asBuffer(it)

            buffer.forEachIndexed { index, value ->
                buffer[index] = castToType(index * castToLong(maxVal))
            }

            buffer.forEachIndexed { index, value ->
                assertEquals(castToType(index * castToLong(maxVal)), buffer[index])
            }

            assertFailsWith<SegmentException> { buffer[buffer.size] = minVal }
            assertFailsWith<SegmentException> { buffer[buffer.size] }
            assertFailsWith<SegmentException> { buffer[-1] = maxVal }
            assertFailsWith<SegmentException> { buffer[-1] }

            assertEquals(buffer.limit, buffer.size)
            assertEquals(buffer.capacity, DataSize._1K.toInt())

            assertFalse { buffer.isNull() }
            assertTrue { buffer.isView() }
            assertFalse { buffer.isMem() }
        }
    }
}