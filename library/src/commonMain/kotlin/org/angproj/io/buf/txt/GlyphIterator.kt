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
import org.angproj.utf.Policy
import org.angproj.utf.iter.CodePointIterator
import org.angproj.utf.octets

public class GlyphIterator(private val txt: Text, index: Int = 0) : CodePointIterator {

    private val policy: Policy = txt.policy

    private var _position: Int = index

    public val position: Int
        get() = _position

    override fun hasNext(): Boolean = txt.limit > _position

    override fun next(): CodePoint = txt.retrieveGlyph(_position).also {
        _position += it.octets()
    }
}