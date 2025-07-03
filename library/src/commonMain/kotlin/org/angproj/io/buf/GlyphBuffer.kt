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

import org.angproj.io.buf.util.Sized
import org.angproj.utf.AbstractUnicodeAware
import org.angproj.utf.Ascii
import org.angproj.utf.CodePoint
import org.angproj.utf.toCodePoint


public class GlyphBuffer(
    initialGlyphs: MutableList<Int> = mutableListOf()
) : AbstractUnicodeAware(), Sized, TextRetrievable, TextStorable, Iterable<CodePoint> {

    private val glyphs: MutableList<Int> = initialGlyphs

    private var _cursor: Int = glyphs.size

    public val cursor: Int
        get() = _cursor

    public fun cursorAt(newCursor: Int) {
        require(newCursor in 0..size)
        _cursor = newCursor
    }

    override val size: Int
        get() = glyphs.size

    public fun insert(codePoint: CodePoint) {
        glyphs.add(_cursor, codePoint.value)
        _cursor++
    }

    public fun delete(): CodePoint {
        if(_cursor == 0)
            return REPLACEMENT_CHARACTER.toCodePoint()
        val removed = glyphs.removeAt(_cursor - 1)
        _cursor--
        return removed.toCodePoint()
    }

    public fun first(): CodePoint = glyphs.first().toCodePoint()

    public fun last(): CodePoint = glyphs.last().toCodePoint()

    public fun trim(chars: Set<Int> = invisibleCharacters): GlyphBuffer {
        if(glyphs.isNotEmpty()) while(glyphs.firstOrNull() in chars) { glyphs.removeAt(0) }
        if(glyphs.isNotEmpty()) while(glyphs.lastOrNull() in chars) { glyphs.removeAt(glyphs.lastIndex) }
        return this
    }

    override fun retrieveGlyph(position: Int): CodePoint {
        return glyphs[position].toCodePoint()
    }

    override fun storeGlyph(position: Int, codePoint: CodePoint): Int {
        glyphs[position] = codePoint.value
        return codePoint.value
    }

    public fun octets(): Int = glyphs.sumOf { unicodeOctetSize<Int>(it) }

    public fun toText(): Text {
        val txt = BufMgr.txt(octets())
        var pos = 0

        glyphs.forEach {
            pos += txt.storeGlyph(pos, it.toCodePoint())
        }

        return txt
    }

    public override fun toString(): String {
        val ca = CharArray(glyphs.size) { glyphs[it].toChar() }
        return ca.concatToString()
    }

    override fun iterator(): Iterator<CodePoint> = object : Iterator<CodePoint> {
        var pos = 0
        override fun next(): CodePoint = glyphs[pos].toCodePoint()
        override fun hasNext(): Boolean = pos < size
    }

    public companion object {
        public val invisibleCharacters: Set<Int> = setOf(
            Ascii.CTRL_HT.cp,
            Ascii.CTRL_LF.cp,
            Ascii.CTRL_VT.cp,
            Ascii.CTRL_FF.cp,
            Ascii.CTRL_CR.cp,
            Ascii.PRNT_SPACE.cp
        )
    }
}

