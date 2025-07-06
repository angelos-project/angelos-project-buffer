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