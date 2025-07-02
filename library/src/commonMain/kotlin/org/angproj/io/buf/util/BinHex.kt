/**
 * Copyright (c) 2023 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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
package org.angproj.io.buf.util

import org.angproj.io.buf.Binary
import org.angproj.io.buf.Text
import org.angproj.io.buf.asBinaryBuffer
import org.angproj.sec.util.floorMod
import org.angproj.utf.AbstractUnicodeAware
import org.angproj.utf.toCodePoint


public object BinHex : AbstractUnicodeAware() {

    public fun hexToBin(txt: Text, bin: Binary) {
        val buf = bin.asBinaryBuffer()
        var odd = txt.size.floorMod(2) != 0
        var value = 0

        txt.forEach { codePoint ->
            value = (value shl 4) or (digitToNumber<Unit>(codePoint.value))

            if(odd) {
                buf.writeByte((value and 0xff).toByte())
            }
            odd = !odd
        }
    }

    public fun binToHex(bin: Binary, txt: Text) {
        val buf = bin.asBinaryBuffer()
        var txtPos = 0

        while(buf.remaining > 0) {
            val data = buf.readByte().toInt()
            txtPos += txt.storeGlyph(txtPos, numberToDigit<Unit>((data ushr 4) and 0xf).toCodePoint())
            txtPos += txt.storeGlyph(txtPos, numberToDigit<Unit>((data) and 0xf).toCodePoint())
        }
    }
}