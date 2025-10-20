/**
 * Copyright (c) 2025 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
 *
 * This software is available under the terms of the MIT license. Parts are licensed
 * under different terms if stated. The legal terms are attached to the LICENSE file
 * and are made available on:
 *
 *      https://opensource.org/licenses/MIT
 *
 * SPDX-License-Identifier: MIT
 *
 * Contributors:
 *      Kristoffer Paulsson - initial implementation
 */
package org.angproj.io.buf

import org.angproj.io.buf.util.DataSize
import org.angproj.utf.toCodePoint
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse

class TextBufferTest : AbstractFlowBufferTest<TextBuffer>() {

    override fun setInput(): TextBuffer {
        val buf = BufMgr.bin(DataSize._1K.toInt()).asTextBuffer()

        // three lines, last line without trailing newline
        buf.write("First line\n")
        buf.write("Second line\n")
        buf.write("Third line")
        return buf
    }

    override val posValue: Int = 33

    @Test
    fun readWriteString() {
        val buf = setInput()
        buf.reset()
        buf.write("hello")
        buf.flip()
        val line = buf.readLine()
        assertEquals("hello", line.toString())
    }

    @Test
    fun readLinesAndIterator() {
        val buf = setInput()
        buf.flip()
        val lines = buf.readLines()
        assertEquals(3, lines.size)

        // iterator should produce the same number of lines
        buf.positionAt(0)
        var count = 0
        for (t in buf) count++
        assertEquals(3, count)
    }

    @Test
    fun writeTextAndBufferCopy() {
        val src = BufMgr.text(DataSize._1K.toInt())
        src.write("copyme")
        src.flip()

        val dst = BufMgr.text(DataSize._1K.toInt())
        dst.reset()

        val written = dst.write(src)
        assertEquals(src.limit - src.mark, written)
    }

    @Test
    fun toBinary_preservesChecksum() {
        val buf = setInput()
        buf.flip()
        val bin = buf.asBinary()
        assertEquals(buf.segment.checkSum(), bin.segment.checkSum())
    }

    @Test
    fun readBoundsCheck() {
        val m = setInput()

        // negative position is illegal for read
        assertFailsWith<IllegalArgumentException> {
            m.positionAt(-1)
            m.read()
        }

        // position at limit must throw on read
        assertFailsWith<IllegalStateException> {
            m.positionAt(m.limit)
            m.read()
        }
    }

    @Test
    fun writeBoundsCheck() {
        val m = setInput()

        // negative position illegal for write
        assertFailsWith<IllegalArgumentException> {
            m.positionAt(-1)
            m.write("x")
        }

        // writing at limit should throw segment-level exception
        m.positionAt(m.limit)
        assertFailsWith<IllegalArgumentException> {
            m.write("x")
        }
    }

    @Test
    fun readWriteCodePoint() {
        val buf = setInput()
        buf.reset()

        val codePoint = 0x1F600.toCodePoint() // 😀
        buf.write(codePoint) // write codepoint directly
        buf.flip()

        val readCp = buf.read()
        assertEquals(codePoint, readCp)
    }

    @Test
    fun testNotNull() {
        assertFalse { setInput().isNull() }
    }
}