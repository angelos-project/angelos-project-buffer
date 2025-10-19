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
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.BeforeTest
import kotlin.test.assertEquals

class CleanableMockitoTest {

    @Mock
    private lateinit var cleanable: Cleanable

    @BeforeTest
    fun setup() {
        cleanable = mock()
    }

    @Test
    fun testDispose() {
        doNothing().whenever(cleanable).dispose()
        assertEquals(Unit, cleanable.dispose())
    }
}