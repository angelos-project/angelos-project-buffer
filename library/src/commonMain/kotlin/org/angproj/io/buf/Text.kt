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

import org.angproj.io.buf.seg.Segment
import org.angproj.io.buf.txt.GlyphString
import org.angproj.io.buf.util.BinHex
import org.angproj.sec.util.ceilDiv
import org.angproj.utf.CodePoint
import org.angproj.utf.Policy
import org.angproj.utf.Unicode
import org.angproj.utf.iter.CodePointIterable
import org.angproj.utf.iter.CodePointIterator
import org.angproj.utf.octets


public class Text internal constructor(
    segment: Segment<*>, view: Boolean = false, public val policy: Policy = Policy.basic
) : BlockBuffer(segment, view), TextRetrievable, TextStorable, CodePointIterable {

    override fun iterator(): CodePointIterator = object : CodePointIterator {
        private var _pos = 0
        public val position: Int
            get() = _pos
        override fun hasNext(): Boolean = remaining<Unit>(_pos) > 0
        override fun next(): CodePoint = retrieveGlyph(_pos).also {
            _pos += it.octets()
        }
    }

    override fun retrieveGlyph(position: Int): CodePoint {
        var offset = position
        return Unicode.readGlyphByPolicyBlk(
            remaining<Int>(offset), policy
        ) {
            segment.getByte(offset++)
        }
    }

    override fun storeGlyph(position: Int, codePoint: CodePoint): Int {
        var offset = position
        return Unicode.writeGlyphByPolicyBlk(
            codePoint, remaining<Int>(offset), policy
        ) {
            segment.setByte(offset++, it)
        }
    }

    /**
     * Scan from start and continue to iterate while the predicate is met, then return the start position of the next glyph.
     * */
    public fun scanUntil(start: Int, predicate: (CodePoint) -> Boolean): Int {
        var pos = start
        while (remaining<Unit>(pos) != 0) {
            if(!predicate(retrieveGlyph(pos))) break
            pos += Unicode.hasGlyphSize(segment.getByte(pos))
        }
        return pos
    }

    public fun substr(start: Int, end: Int): Text {
        return BufMgr.txt(end - start, policy).also { this.copyInto(it, 0, start, end) }
    }

    public fun applyPolicy() {
        iterator().forEach { _ -> }
    }

    public override fun toString(): String = buildString {
        this@Text.forEach { append(it.value.toChar()) }
        return this@buildString.toString()
    }
}

public fun String.toText(policy: Policy = Policy.basic): Text = BufMgr.stringToText(this, policy)

public fun Text.hexToBin(): Binary = BufMgr.bin(this.size.ceilDiv(2)).also { BinHex.hexToBin(this, it) }

public fun Text.toGlyphString(): GlyphString {
    val iter = iterator()
    val ints = mutableListOf<Int>()
    iter.forEach { ints.add(it.value) }
    return GlyphString(ints)
}