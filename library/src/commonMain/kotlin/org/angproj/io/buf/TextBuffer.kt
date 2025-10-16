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
import org.angproj.utf.Ascii
import org.angproj.utf.CodePoint
import org.angproj.utf.Policy
import org.angproj.utf.Unicode
import kotlin.math.min


public class TextBuffer internal constructor(
    segment: Segment<*>, view: Boolean = false, public val policy: Policy = Policy.basic
): FlowBuffer(segment, view) {

    public fun read(): CodePoint = Unicode.readGlyphByPolicyBlk(remaining, policy) {
        segment.getByte(_position++)
    }

    public fun write(codePoint: CodePoint): Int = Unicode.writeGlyphByPolicyBlk(codePoint, remaining, policy) {
        segment.setByte(_position++, it)
    }

    private fun readLineImpl(txt: Text, start: Int, newLine: CodePoint): Text {
        val end = txt.scanUntil(start) { it != newLine }
        val size = end - start
        return BufMgr.txt(size, policy).also {
            if(size > 0) txt.copyInto(it, 0, start, end)
            positionAt(min(end+1, limit))
        }
    }

    public fun readLine(newLine: CodePoint = Ascii.CTRL_LF.toCodePoint()): Text = readLineImpl(asText(), position, newLine)

    public fun readLines(newLine: CodePoint = Ascii.CTRL_LF.toCodePoint()): List<Text> {
        val lines = mutableListOf<Text>()
        val txt = asText()
        while(remaining > 0)
            lines.add(readLineImpl(txt, position, newLine))
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
        asText().forEach { _ -> }
    }

    public fun asText(): Text = Text(segment, true, policy)
}
