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

import org.angproj.io.buf.Text
import org.angproj.utf.CodePoint
import org.angproj.utf.octets

public fun Text.lookAheadUntilStop(index: Int, tokens: Set<CodePoint>): IntRange {
    var position = index

    do {
        val codePoint = retrieveGlyph(position)
        position += codePoint.octets()
    } while (tokens.contains(codePoint) && limit > position)

    return index..position
}