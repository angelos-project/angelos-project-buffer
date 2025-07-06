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
import org.angproj.io.buf.toText
import org.angproj.utf.Policy


public fun textListOf(vararg txts: Text): MutableList<Text> {
    return txts.toMutableList()
}

public fun textListOf(vararg strs: String = listOf<String>().toTypedArray()): MutableList<Text> {
    val txts = mutableListOf<Text>()
    strs.forEach { txts.add(it.toText()) }
    return txts
}


public operator fun MutableList<Text>.plusAssign(other: GlyphString) {
    add(other.toText())
}

public operator fun MutableList<Text>.plusAssign(other: Text) {
    add(other)
}

public operator fun MutableList<Text>.plusAssign(other: TextBuffer) {
    add(other.asText())
}

public operator fun MutableList<Text>.plusAssign(other: String) {
    add(other.toText())
}

public fun List<Text>.toTextBuffer(policy: Policy = Policy.basic): TextBuffer {
    val size = sumOf { it.limit }
    val buffer = BufMgr.text(size, policy)
    forEach { buffer.write(it) }
    buffer.applyPolicy()
    return buffer.also { it.reset() }
}