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
import org.angproj.io.buf.txt.GlyphIterator
import org.angproj.utf.Ascii
import org.angproj.utf.CodePoint
import org.angproj.utf.Policy
import org.angproj.utf.Unicode
import org.angproj.utf.iter.CodePointIterable
import org.angproj.utf.iter.CodePointIterator


public class TextBuffer internal constructor(
    segment: Segment<*>, view: Boolean = false, public val policy: Policy = Policy.basic
): FlowBuffer(segment, view), CodePointIterable {

    public fun read(): CodePoint = Unicode.readGlyphByPolicyBlk(remaining, policy) {
        segment.getByte(_position++)
    }

    public fun write(codePoint: CodePoint): Int = Unicode.writeGlyphByPolicyBlk(codePoint, remaining, policy) {
        segment.setByte(_position++, it)
    }

    public fun readLine(newLine: CodePoint = Ascii.CTRL_LF.toCodePoint()): Text {
        val start = position
        val txt = asText()
        val end = txt.find(start) {
            it == newLine.value // TODO(Check whether this is right or not)
        }
        positionAt(end)
        return BufMgr.txt(end - start, policy).also { this.copyInto(it, 0, start, end) }
    }

    public fun readLines(newLine: CodePoint = Ascii.CTRL_LF.toCodePoint()): List<Text> {
        val lines = mutableListOf<Text>()
        while(remaining > 0)
            lines.add(readLine(newLine))
        return lines.toList()
    }

    public fun write(txt: Text): Int {
        txt.copyInto(this, position, 0, txt.limit)
        positionAt(position + txt.limit)
        return txt.limit
    }

    public fun write(str: String): Int {
        return write(str.toText(policy))
    }

    public fun write(buf: TextBuffer): Int {
        val length = buf.limit - buf.mark
        buf.copyInto(this, position, buf.mark, buf.limit)
        positionAt(position + length)
        return length
    }

    public fun applyPolicy() {
        iterator().forEach { _ -> }
    }

    public fun asText(): Text = Text(segment, true, policy)

    override fun iterator(): CodePointIterator = GlyphIterator(asText())
}
