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
package org.angproj.io.buf.util

import org.mockito.Mock
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals
import kotlin.test.Test
import kotlin.test.BeforeTest

class SizedMockitoTest {

    @Mock
    private lateinit var sized: Sized

    @BeforeTest
    fun setup() {
        sized = mock()
    }

    @Test
    fun testGetSize() {
        whenever(sized.size).doReturn(1024)
        assertEquals(1024, sized.size)
    }
}