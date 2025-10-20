/**
 * Copyright (c) 2024-2025 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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

import org.angproj.io.buf.seg.SegmentException
import org.angproj.sec.util.TypeSize
import org.angproj.utf.toCodePoint
import kotlin.collections.plusAssign
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith


class TextTest: AbstractBlockBufferTest<Text>() {

    override val txtLen = TestInformationStub.refArray.size

    override fun setInput(): Text {
        val buf = BufMgr.bin(txtLen)
        TestInformationStub.refArray.forEachIndexed { index, value ->
            buf.storeByte(index, value)
        }
        return buf.asText() // asText to make it a view and not original
    }

    @Test
    fun retrieveStoreGlyph() {
        val txt = setInput()
        (0 until txt.limit step 1).forEach {
            txt.storeGlyph(it, TestInformationStub.refByte.toInt().toCodePoint()) }
        (0 until txt.limit step 1).forEach {
            assertEquals(txt.retrieveGlyph(it), TestInformationStub.refByte.toInt().toCodePoint()) }
    }

    @Test
    fun testNullBinary() {
        assertFailsWith<UnsupportedOperationException> { Binary.nullBinary }
        assertFailsWith<UnsupportedOperationException> { BufMgr.bin(0).isNull() }
    }

    @Test
    fun glyphRWOutbound() {
        val m = setInput()

        m.retrieveGlyph(0)
        assertFailsWith<SegmentException> {
            m.retrieveGlyph(-1)
        }

        m.retrieveGlyph(m.limit-TypeSize.byteSize) // Won't crash
        assertFailsWith<IllegalStateException> {
            m.retrieveGlyph(m.limit) // Must throw
        }

        m.storeGlyph(0, 1.toCodePoint())
        assertFailsWith<SegmentException> {
            m.storeGlyph(-1, 1.toCodePoint())
        }

        m.storeGlyph(m.limit-TypeSize.byteSize, 0.toCodePoint()) // Won't crash
        assertFailsWith<IllegalStateException> {
            m.storeGlyph(m.limit, 1.toCodePoint()) // Must throw
        }
    }

    @Test
    fun allCombosTest() {
        val result = textListOf()
        result += "Hello, ".toText()
        result += "world!".toText()
        assertEquals("Hello, world!", result.toTextBuffer().asText().toString())
    }

    @Test
    fun allCombos2Test() {
        val result = textListOf("Hello, ".toText(), "world!".toText())
        assertEquals("Hello, world!", result.toTextBuffer().asText().toString())
    }

    @Test
    fun allCombos3Test() {
        val result = textListOf()
        result += "Foo "
        val buf = BufMgr.text(4).also {
            it.write("bar ")
            it.flip()
        }
        result += buf
        result += "baz!".toText()
        assertEquals("Foo bar baz!", result.toTextBuffer().asText().toString())
    }
}