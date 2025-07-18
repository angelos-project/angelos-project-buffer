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
package org.angproj.io.buf.json

import org.angproj.io.buf.Text

public class Lexer(public val txt: Text, public val row: Int, public val col: Int) {
    private var intRange: IntRange = 0..0

    public var start: Int = -1
    public var end: Int = -1

    public val children: MutableList<Lexer> = mutableListOf()

    public fun position(): String = "line $row, letter $col"
}