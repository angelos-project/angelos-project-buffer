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
import org.angproj.io.buf.util.BinHex
import org.angproj.sec.util.ceilDiv
import org.angproj.sec.util.ensure
import org.angproj.utf.CodePoint
import org.angproj.utf.CodePointPredicate
import org.angproj.utf.Policy
import org.angproj.utf.Unicode
import org.angproj.utf.iter.CodePointIterable
import org.angproj.utf.iter.CodePointIterator
import org.angproj.utf.octets

public class Text internal constructor(
    segment: Segment<*>, view: Boolean = false, public val policy: Policy = Policy.basic, endian: Platform.ENDIAN
) : AbstractBlockBuffer(segment, view, endian), TextRetrievable, TextStorable, CodePointIterable {

    override fun iterator(): CodePointIterator = object : CodePointIterator {
        private var _prev = CodePoint.nullCodePoint
        override val previous: CodePoint
            get() = _prev
        private var _pos = 0
        override val position: Int
            get() = _pos
        private var _cnt = 0
        override val count: Int
            get() = _cnt
        override fun hasNext(): Boolean = remaining<Unit>(_pos) > 0
        override fun next(): CodePoint {
            ensure<NoSuchElementException>(hasNext()) { NoSuchElementException("No more glyphs") }
            return retrieveGlyph(_pos).also {
                _prev = it
                _pos += it.octets()
                _cnt++
            }
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
    public fun scanUntil(start: Int, predicate: CodePointPredicate): Int {
        var pos = start
        while (remaining<Unit>(pos) != 0) {
            if(!predicate(retrieveGlyph(pos))) break
            pos += Unicode.hasGlyphSize(segment.getByte(pos))
        }
        return pos
    }

    public fun subText(start: Int, end: Int): Text = when(end - start > 0) {
        true -> BufMgr.txt(end - start, policy).also { this.copyInto(it, 0, start, end) }
        else -> BufMgr.txt(0, policy)
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

// Text lists
public fun textOf(): MutableList<Text> = mutableListOf()

public fun textOf(vararg txts: Text): MutableList<Text> = txts.toMutableList()

public fun textOf(vararg strs: String): MutableList<Text> = strs.mapTo(textOf()) { it.toText() }

public operator fun MutableList<Text>.plusAssign(other: Text) { this.add(other) }

public operator fun MutableList<Text>.plusAssign(other: String) { this += other.toText() }

public operator fun MutableList<Text>.plusAssign(other: MutableList<Text>) { this.addAll(other) }

public operator fun MutableList<Text>.plus(other: Text): MutableList<Text> = this.apply { add(other) }

public operator fun MutableList<Text>.plus(other: String): MutableList<Text> = this.apply { add(other.toText()) }

public operator fun MutableList<Text>.plus(other: MutableList<Text>): MutableList<Text> = this.apply { addAll(other) }

public fun MutableList<Text>.join(separator: Text): List<Text> {
    if (this.isEmpty()) return this
    val result = mutableListOf<Text>()
    forEachIndexed { index, txt ->
        result += txt
        if (index < this.lastIndex) {
            result += separator
        }
    }
    return result.toList()
}

public fun MutableList<Text>.join(separator: String): List<Text> = join(separator.toText())

public fun List<Text>.toTextBuffer(policy: Policy = Policy.basic): TextBuffer {
    val size = sumOf { it.limit }
    val buffer = BufMgr.text(size, policy)
    forEach { buffer.write(it) }
    buffer.applyPolicy()
    return buffer.also { it.reset() }
}

public fun List<Text>.toText(): Text = toTextBuffer().asText()