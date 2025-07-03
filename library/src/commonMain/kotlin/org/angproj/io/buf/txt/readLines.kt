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
import org.angproj.io.buf.TextBuffer
import org.angproj.io.buf.asText
import org.angproj.io.buf.copyInto
import org.angproj.utf.Ascii
import org.angproj.utf.toCodePoint

public fun TextBuffer.readLines(): List<Text> {
    val lines = mutableListOf<Text>()
    val txt = asText()
    val delimiter = setOf(Ascii.CTRL_LF.cp.toCodePoint())
    var position: Int = 0

    while (position < txt.limit) {
        val range = txt.lookAheadUntilFound(position, delimiter)
        position = range.last
        val subText = BufMgr.txt(range.last - range.first)
        txt.copyInto(subText, 0, range.first, range.last)
        lines.add(subText)
    }

    return lines
}