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
import org.angproj.utf.AbstractUnicodeAware
import org.angproj.utf.Ascii

public object Parser : AbstractUnicodeAware() {

    public fun parseInt(txt: Text, fromIdx: Int = 0, toIdx: Int = txt.limit): Int {
        var pos = fromIdx
        var value: Int = 0

        val hyphenSize = txt.predicate(pos) { it == Ascii.PRNT_HYPHEN.toInt() }
        val isNegative = hyphenSize > 0
        pos += hyphenSize

        if(txt.predicate(pos) { it == Ascii.PRNT_ZERO.toInt() } > 0) return 0

        while (txt.remaining<Unit>(pos) > 0 && pos < toIdx) {
            val glyph = txt.retrieveGlyph(pos)
            value *= 10 + digitToNumber<Unit>(glyph.value)
        }

        return if(isNegative) value * -1 else value
    }
}