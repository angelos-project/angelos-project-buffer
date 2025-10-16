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

class TextRetrievableMockitoTest {

    @Mock
    private lateinit var testRetrievable: TextRetrievable

    @BeforeTest
    fun setup() {
        testRetrievable = mock()
    }

    @Test
    fun testReadGlyph() {
        whenever(testRetrievable.retrieveGlyph(0)).thenReturn(0x7f.toCodePoint())
        assertEquals(0x7f.toCodePoint(), testRetrievable.retrieveGlyph(0))
    }
}