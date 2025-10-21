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
import org.angproj.sec.util.ensure
import org.angproj.utf.Ascii
import org.angproj.utf.CodePoint
import org.angproj.utf.Policy
import org.angproj.utf.Unicode
import kotlin.math.min

public class TextBuffer internal constructor(
    segment: Segment<*>,
    view: Boolean = false,
    public val policy: Policy = Policy.basic,
    public val newLine: CodePoint = Ascii.CTRL_LF.toCodePoint()
): AbstractFlowBuffer(segment, view), Iterable<Text> {

    override fun iterator(): Iterator<Text> = object: Iterator<Text> {
        private val txt = asText()
        override fun hasNext(): Boolean = position < txt.limit
        override fun next(): Text {
            ensure<NoSuchElementException>(hasNext()) { NoSuchElementException("EOF") }
            return readLineImpl(txt, position, newLine)
        }
    }

    public fun read(): CodePoint = Unicode.readGlyphByPolicyBlk(remaining, policy) {
        segment.getByte(_position++)
    }

    public fun write(codePoint: CodePoint): Int = Unicode.writeGlyphByPolicyBlk(codePoint, remaining, policy) {
        segment.setByte(_position++, it)
    }

    private fun readLineImpl(txt: Text, start: Int, newLine: CodePoint): Text {
        val end = txt.scanUntil(start) { it != newLine }
        return txt.subText(start, end).also {
            positionAt(min(end+1, limit))
        }
    }

    public fun readLine(newLine: CodePoint = Ascii.CTRL_LF.toCodePoint()): Text = readLineImpl(asText(), position, newLine)

    public fun readLines(): List<Text> = toList()

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
