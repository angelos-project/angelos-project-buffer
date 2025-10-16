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

import org.angproj.utf.toCodePoint
import org.mockito.Mock
import org.mockito.kotlin.*
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class TextStorableMockitoTest {

    @Mock
    private lateinit var textStorable: TextStorable

    @BeforeTest
    fun setup() {
        textStorable = mock()
    }

    @Test
    fun testWriteGlyph() {
        val codePoint = 0x7f.toCodePoint()
        whenever(textStorable.storeGlyph(0, codePoint)).thenReturn(1)
        assertEquals(1, textStorable.storeGlyph(0, codePoint))
    }
}