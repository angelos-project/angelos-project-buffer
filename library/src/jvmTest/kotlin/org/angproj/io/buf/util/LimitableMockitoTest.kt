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
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.Test
import kotlin.test.BeforeTest


class LimitableMockitoTest {

    @Mock
    private lateinit var limitable: Limitable

    @BeforeTest
    fun setup() {
        limitable = mock()
    }

    @Test
    fun testGetSize() {
        whenever(limitable.size).doReturn(1024)
        assertEquals(1024, limitable.size)
    }

    @Test
    fun testGetLimit() {
        whenever(limitable.limit).doReturn(128)
        assertEquals(128, limitable.limit)
    }

    @Test
    fun testLimitAt() {
        doNothing().whenever(limitable).limitAt(1024)
        assertEquals(Unit, limitable.limitAt(1024))
    }

    @Test
    fun testLimitAtThrowException() {
        whenever(limitable.limitAt(1025)).doThrow(IndexOutOfBoundsException())
        assertFailsWith<IndexOutOfBoundsException> { limitable.limitAt(1025) }
    }

    @Test
    fun testClear() {
        whenever(limitable.size).doReturn(1024)
        doNothing().whenever(limitable).limitAt(1024)
        assertEquals(Unit, limitable.clear())
    }
}