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

import org.angproj.io.buf.mem.Default
import org.angproj.io.buf.seg.Bytes
import org.angproj.utf.Ascii
import org.angproj.utf.CodePoint
import org.angproj.utf.octets
import org.angproj.utf.toCodePoint
import java.nio.file.FileSystems
import java.nio.file.Files


public fun BufMgr.textFile(path: String): TextBuffer {
    val realPath = FileSystems.getDefault().getPath(path)
    return TextBuffer(Bytes(Default, Files.readAllBytes(realPath)))
}

public fun Text.lookAheadUntilFound(index: Int, tokens: Set<CodePoint>): IntRange {
    var position = index

    do {
        val codePoint = retrieveGlyph(position)
        position += codePoint.octets()
    } while (!tokens.contains(codePoint) && limit > position)

    return index..position
}

public fun Text.lookAheadUntilStop(index: Int, tokens: Set<CodePoint>): IntRange {
    var position = index

    do {
        val codePoint = retrieveGlyph(position)
        position += codePoint.octets()
    } while (tokens.contains(codePoint) && limit > position)

    return index..position
}

public fun TextBuffer.readLines(): List<IntRange> {
    val lines = mutableListOf<IntRange>()
    val txt = asText()
    val delimiter = setOf(Ascii.CTRL_LF.cp.toCodePoint())
    var position: Int = 0

    while (position < txt.limit) {
        val range = txt.lookAheadUntilFound(position, delimiter)
        lines.add(range)
        position = range.last
    }

    return lines
}