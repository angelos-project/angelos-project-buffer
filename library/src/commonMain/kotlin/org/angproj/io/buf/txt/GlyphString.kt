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
package org.angproj.io.buf.txt

import org.angproj.io.buf.BufMgr
import org.angproj.io.buf.Text
import org.angproj.io.buf.TextRetrievable
import org.angproj.io.buf.TextStorable
import org.angproj.io.buf.util.Sized
import org.angproj.utf.AbstractUnicodeAware
import org.angproj.utf.Ascii
import org.angproj.utf.CodePoint
import org.angproj.utf.alphabetOf
import org.angproj.utf.iter.CodePointIterator
import org.angproj.utf.octets
import org.angproj.utf.toCodePoint


public class GlyphString(
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

    public fun trimLeft(chars: Set<Int> = invisibleCharacters): GlyphString {
        if(glyphs.isNotEmpty()) while(glyphs.firstOrNull() in chars) { glyphs.removeAt(0) }
        return this
    }

    public fun trimRight(chars: Set<Int> = invisibleCharacters): GlyphString {
        if(glyphs.isNotEmpty()) while(glyphs.lastOrNull() in chars) { glyphs.removeAt(glyphs.lastIndex) }
        return this
    }

    public fun trim(chars: Set<Int> = invisibleCharacters): GlyphString = trimLeft(chars).trimRight(chars)

    override fun retrieveGlyph(position: Int): CodePoint {
        return glyphs[position].toCodePoint()
    }

    override fun storeGlyph(position: Int, codePoint: CodePoint): Int {
        glyphs[position] = codePoint.value
        return codePoint.value
    }

    public fun integerOf(range: IntRange, multiple: Long): Long {
        var value: Long = 0
        range.forEach {
            value *= multiple + digitToNumber<Unit>(glyphs[it])
        }
        return value
    }

    public fun fractionOf(range: IntRange, fraction: Double): Double {
        var fractionSize = 1.0
        var value = .0
        range.forEach {
            fractionSize *= fraction
            value += digitToNumber<Unit>(glyphs[it]) * fractionSize
        }
        return value
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

    override fun iterator(): CodePointIterator = object : CodePointIterator {
        var pos = 0
        override fun next(): CodePoint = glyphs[pos].toCodePoint()
        override fun hasNext(): Boolean = pos < size
    }

    public companion object Companion {
        public val invisibleCharacters: Set<Int> by lazy {
            alphabetOf(
                Ascii.CTRL_HT,
                Ascii.CTRL_LF,
                Ascii.CTRL_VT,
                Ascii.CTRL_FF,
                Ascii.CTRL_CR,
                Ascii.PRNT_SPACE
            )
        }

        public const val octMultiple: Long = 8
        public const val octFraction: Double = 1.0 / 8.0

        public val octal: Set<Int> by lazy {
            alphabetOf(
                Ascii.PRNT_ZERO,
                Ascii.PRNT_ONE,
                Ascii.PRNT_TWO,
                Ascii.PRNT_THREE,
                Ascii.PRNT_FOUR,
                Ascii.PRNT_FIVE,
                Ascii.PRNT_SIX,
                Ascii.PRNT_SEVEN,
            )
        }

        public const val decMultiple: Long = 10
        public const val decFraction: Double = 1.0 / 10.0

        public val decimal: Set<Int> by lazy {
            octal + alphabetOf(
                Ascii.PRNT_EIGHT,
                Ascii.PRNT_NINE
            )
        }

        public const val hexMultiple: Long = 16
        public const val hexFraction: Double = 1.0 / 16.0

        public val hex: Set<Int> by lazy {
            decimal + alphabetOf(
                Ascii.PRNT_A_UP,
                Ascii.PRNT_A_LOW,
                Ascii.PRNT_B_UP,
                Ascii.PRNT_B_LOW,
                Ascii.PRNT_C_UP,
                Ascii.PRNT_C_LOW,
                Ascii.PRNT_D_UP,
                Ascii.PRNT_D_LOW,
                Ascii.PRNT_E_UP,
                Ascii.PRNT_E_LOW,
                Ascii.PRNT_F_UP,
                Ascii.PRNT_F_LOW
            )
        }
    }
}

