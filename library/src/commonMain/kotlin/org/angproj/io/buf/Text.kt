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
import org.angproj.io.buf.txt.GlyphIterator
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

    override fun iterator(): CodePointIterator = GlyphIterator(this)

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

    public fun find(start: Int, tokens: Set<Int>): Int {
        val iter = GlyphIterator(this, start)
        while(iter.hasNext()) {
            if(tokens.contains(iter.next().value))
                break
        }
        return iter.position
    }

    public fun parse(start: Int, tokens: Set<Int>): Int {
        val iter = GlyphIterator(this, start)
        while(iter.hasNext()) {
            if(!tokens.contains(iter.next().value))
                break
        }
        return iter.position - 1
    }

    /**
     * Scans the text starting from the specified position, advancing the iterator until the predicate [logic] returns true.
     * Searching for the beginning of compliance.
     *
     * @param start The starting position (in code points) within the text.
     * @param logic A predicate function that takes a code point (as `Int`) and returns `true` to indicate a match.
     * @return The position (in code points) where [logic] first returned true, or the end position if not found.
     */
    public fun find(start: Int, logic: (Int) -> Boolean): Int {
        val iter = GlyphIterator(this, start)
        while(iter.hasNext()) {
            if(logic(iter.next().value))
                break
        }

        return iter.position
    }

    /**
     * Scans the text starting from the specified position, advancing the iterator as long as the predicate [logic] returns false.
     * Searching for the end of compliance
     *
     * @param start The starting position (in code points) within the text.
     * @param logic A predicate function that takes a code point (as `Int`) and returns `true` to continue parsing.
     * @return The position (in code points) of the last code point for which [logic] returned true, or `start - 1` if none matched.
     */
    public fun parse(start: Int, logic: (Int) -> Boolean): Int {
        var pos = start
        while(remaining<Int>(pos) > 0) {
            val size = predicate(pos, logic)
            if(size == 0)
                break
            pos += size
        }
        return pos
    }

    /**
     * Gives the size of the glyph if predicate is true or zero
     * */
    public fun predicate(pos: Int, predicate: (Int) -> Boolean): Int {
        val next = retrieveGlyph(pos)
        return if(predicate(next.value)) next.octets() else 0
    }

    public fun startsWith(prefix: Text, startIndex: Int): Boolean {
        var pos = 0
        while (prefix.remaining<Unit>(pos) > 0) {
            val glyph = prefix.retrieveGlyph(pos)
            pos += glyph.octets()
            if(retrieveGlyph(startIndex + pos) != glyph)
                return false
        }
        return true
    }

    public fun substr(start: Int, end: Int): Text {
        return BufMgr.txt(end - start, policy).also { this.copyInto(it, 0, start, end) }
    }

    public fun applyPolicy() {
        iterator().forEach { _ -> }
    }

    public override fun toString(): String {
        val text = mutableListOf<Char>()
        forEach { text.add(it.value.toChar()) }
        return text.toCharArray().concatToString()
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