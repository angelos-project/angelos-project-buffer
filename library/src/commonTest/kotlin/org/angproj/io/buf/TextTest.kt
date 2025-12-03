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

import org.angproj.io.buf.plusAssign
import org.angproj.io.buf.seg.SegmentException
import org.angproj.sec.util.TypeSize
import org.angproj.utf.Policy
import org.angproj.utf.toCodePoint
import kotlin.collections.plusAssign
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
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
    fun testTextIterator() {
        val txt = setInput()
        val iter = txt.iterator()

        iter.next()

        while (iter.hasNext()) {
            iter.next()
            assertTrue { iter.position > 0 }
            assertTrue { iter.previous != 0.toCodePoint() }
            assertTrue { iter.count > 0 }
        }
    }

    /*@Test
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

    @Test
    fun allCombos4Test() {
        val result = textListOf("Hello, ", "world!")
        assertEquals("Hello, world!", result.toTextBuffer().asText().toString())
    }*/

    @Test
    fun textOfEmpty() {
        val list = textOf()
        assertTrue(list.isEmpty())
    }

    @Test
    fun textOfVarargTexts() {
        val a = "Hello, ".toText()
        val b = "world!".toText()
        val list = textOf(a, b)
        assertEquals(2, list.size)
        assertEquals("Hello, world!", list.toText().toString())
    }

    @Test
    fun textOfVarargStrings() {
        val list = textOf("Hello, ", "world!")
        assertEquals("Hello, world!", list.toText().toString())
    }

    @Test
    fun plusAssignAndPlusOperators() {
        val a = "Foo ".toText()
        val b = "bar ".toText()
        val c = "baz!".toText()

        val result = textOf()
        result += a
        result += "bar "
        assertEquals("Foo bar ", result.toText().toString())

        val appended = result + c
        // plus returns the same list instance with the element added
        assertEquals("Foo bar baz!", appended.toTextBuffer().asText().toString())

        val combined = textOf("Foo ") + b
        assertEquals("Foo bar ", combined.toTextBuffer().asText().toString())

        val combined2 = textOf() + "baz!"
        assertEquals("baz!", combined2.toTextBuffer().asText().toString())
    }

    @Test
    fun toTextBufferAndToText() {
        val list = textOf("Hello, ".toText(), "world!".toText())
        val buffer = list.toTextBuffer()
        assertEquals("Hello, world!", buffer.asText().toString())
        val text = list.toText()
        assertEquals("Hello, world!", text.toString())
    }

    @Test
    fun plusAssignAddsAllFromMutableList() {
        val a = textOf("Hello, ")
        val b = textOf("world!", " How")
        a += b
        assertEquals("Hello, world! How", a.toText().toString())
    }

    @Test
    fun plusWithMutableListReturnsCombined() {
        val base = textOf("A")
        val other = textOf("B", "C")
        val combined = base + other
        // current semantics add into the same instance and return it
        assertEquals("ABC", combined.toText().toString())
        // ensure base was mutated as well
        assertEquals("ABC", base.toText().toString())
    }

    @Test
    fun joinWithTextSeparatorInsertsSeparators() {
        val parts = textOf("one", "two", "three")
        val joined = parts.join(", ".toText())
        // join returns a List<Text> with separators between elements
        assertEquals("one, two, three", joined.toText().toString())
        // verify expected number of elements: 5 (element, sep, element, sep, element)
        assertEquals(5, joined.size)
    }

    @Test
    fun joinWithStringSeparatorWorks() {
        val parts = textOf("x", "y")
        val joined = parts.join("::")
        assertEquals("x::y", joined.toText().toString())
    }

    @Test
    fun listToTextAndToTextBufferProduceConcatenation() {
        val list = textOf("Alpha", "Beta", "Gamma")
        val txt = list.toText()
        val buf = list.toTextBuffer(Policy.basic)
        assertEquals("AlphaBetaGamma", txt.toString())
        assertEquals("AlphaBetaGamma", buf.asText().toString())
    }

    @Test
    fun textOfEmptyAndVarargsBehave() {
        val empty = textOf()
        assertTrue(empty.isEmpty())

        val a = "Hello, ".toText()
        val b = "world!".toText()
        val list = textOf(a, b)
        assertEquals(2, list.size)
        assertEquals("Hello, world!", list.toText().toString())
    }
}