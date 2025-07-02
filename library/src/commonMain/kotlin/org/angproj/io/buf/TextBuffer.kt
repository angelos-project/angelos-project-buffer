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

import org.angproj.io.buf.seg.Segment
import org.angproj.utf.CodePoint
import org.angproj.utf.UnicodeAware


public class TextBuffer internal constructor(
    segment: Segment<*>, view: Boolean = false
): FlowBuffer(segment, view), UnicodeAware {

    public fun read(): CodePoint = readGlyphBlk(remaining) {
        segment.getByte(_position++)
    }

    public fun read(text: TextBuffer, offset: Int, length: Int): Int {
        text.positionAt(offset)
        var cnt = 0
        try {
            while(cnt < length) { cnt += text.write(read()) }
        } catch (_: IllegalStateException) {}
        return cnt
    }

    public fun write(codePoint: CodePoint): Int = writeGlyphBlk(codePoint, remaining) {
        segment.setByte(_position++, it)
    }

    public fun write(txt: Text): Int {
        var cnt = 0
        try {
            txt.forEach { cnt += write(it) }
        } catch (_: IllegalStateException) {}
        return cnt
    }

    protected fun write(str: String): Int {
        return write(str.toText())
    }

    public fun write(text: TextBuffer, offset: Int, length: Int): Int {
        text.positionAt(offset)
        var cnt = 0
        try {
            while(cnt < length) { cnt += write(text.read()) }
        } catch (_: IllegalStateException) {}
        return cnt
    }

    /**
     * Gets the byte [index] of the next glyph if outside [toIndex] it returns its original value,
     * or throws an error if malformed octet byte.
     * */
    protected fun jumpNext(index: Int): Int {
        val offset: Int = hasGlyphSize(segment.getByte(index)) + index
        return when {
            offset > index -> if(offset < limit) offset else index
            else -> error("Index not beginning of UTF-8 glyph.")
        }
    }

    /**
     * Seeks from byte [index] after the beginning byte of next UTF-8 glyph up to [limit].
     * */
    protected fun seekNext(index: Int): Int {
        var pos = index
        do { pos++ } while(!isGlyphStart(segment.getByte(pos)) && pos < limit)
        return pos
    }

    protected fun currentOrSeekNext(index: Int): Int = if(isGlyphStart(segment.getByte(index))) index else seekNext(index)

    /**
     * Seeks from byte [index] after the beginning byte of previous UTF-8 glyph down to [mark].
     * */
    protected fun seekPrev(index: Int): Int {
        var pos = index
        do { pos-- } while(!isGlyphStart(segment.getByte(pos)) && pos >= _mark)
        return pos
    }

    protected fun currentOrSeekPrev(index: Int): Int = if(isGlyphStart(segment.getByte(index))) index else seekPrev(index)

    /**
     * Seeks for the byte index of [offset] which is X number of multibyte characters,
     * between [fromIndex] and [toIndex] which defaults to [mark] and [limit], if the
     * seek goes out of range it returns -1.
     * */
    public fun seek(offset: Int, fromIndex: Int = mark, toIndex: Int = limit): Int {
        var current = currentOrSeekPrev(fromIndex)
        var pos = 0
        while(current < toIndex && pos < offset) {
            val next = jumpNext(current)
            if(current == next) break
            else current = next
            pos++
        }
        return if(pos == offset) current else -1
    }
}

public fun TextBuffer.asText(): Text = Text(segment, true)

