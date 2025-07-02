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
import org.angproj.utf.CodePoint
import org.angproj.utf.UnicodeAware
import org.angproj.utf.octets


public class Text internal constructor(
    segment: Segment<*>, view: Boolean = false
) : BlockBuffer(segment, view), TextRetrievable, TextStorable, Iterable<CodePoint>, UnicodeAware {

    public fun count(): Int {
        var cnt = 0
        var idx = 0
        while (idx < limit) {
            idx = jumpNext(idx)
            cnt++
        }
        return cnt
    }

    protected fun jumpNext(index: Int): Int {
        val offset: Int = hasGlyphSize(segment.getByte(index)) + index
        return when {
            offset > index -> offset
            else -> error("Index not start of UTF-8 glyph.")
        }
    }

    override fun iterator(): Iterator<CodePoint> = object : Iterator<CodePoint> {
        private var _position = 0
        public val position: Int
            get() = _position
        override fun hasNext(): Boolean = remaining<Int>(_position) > 0
        override fun next(): CodePoint = this@Text.retrieveGlyph(_position).also { _position += it.octets() }
    }

    override fun retrieveGlyph(position: Int): CodePoint {
        var offset = position
        return readGlyphBlk(remaining<Int>(offset)) { segment.getByte(offset++) }
    }

    override fun storeGlyph(position: Int, codePoint: CodePoint): Int {
        var offset = position
        return writeGlyphBlk(codePoint, remaining<Int>(offset)) { segment.setByte(offset++, it) }
    }

    public fun checkSum(key: Long = 0): Long = segment.checkSum(key)
}

public fun String.toText(): Text = BufMgr.stringToText(this)

public fun Text.hexToBin(): Binary = BufMgr.bin(this.size.ceilDiv(2)).also { BinHex.hexToBin(this, it) }


/*public operator fun Text.plus(other: Text): MutableList<Text> = mutableListOf(this, other)

public operator fun MutableList<Text>.plus(other: Text): MutableList<Text> = also { MutableList.add(other) }

public operator fun MutableList<Text>.plusAssign(other: Text) {
    MutableList.add(other)
}

public fun List<Text>.toBinary(): Text {
    val out = BufMgr.txt(sumOf { it.limit })
    var dstOff = 0
    forEach {
        it.copyInto(out, dstOff, 0, it.limit)
        dstOff += it.limit
    }
    return out
}*/