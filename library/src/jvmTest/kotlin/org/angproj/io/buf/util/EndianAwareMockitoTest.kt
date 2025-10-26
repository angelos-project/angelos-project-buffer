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

import org.angproj.io.buf.Platform
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.mockito.kotlin.any
import org.mockito.kotlin.doAnswer
import kotlin.test.Test
import kotlin.test.BeforeTest
import kotlin.test.assertEquals



class EndianAwareMockitoTest {

    private lateinit var endian: EndianAware

    @BeforeTest
    fun setup() {
        endian = mock()
    }

    @Test
    fun testNative() {
        val native = Platform.endian
        whenever(endian.byteOrder).thenReturn(native)
        whenever(endian.byteSwapping).thenReturn(false)

        assertEquals(native, endian.byteOrder)
        assertEquals(false, endian.byteSwapping)
    }

    @Test
    fun testOpposite() {
        val opposite = if (Platform.endian == Platform.ENDIAN.BIG_ENDIAN)
            Platform.ENDIAN.LITTLE_ENDIAN else Platform.ENDIAN.BIG_ENDIAN

        whenever(endian.byteOrder).thenReturn(opposite)
        whenever(endian.byteSwapping).thenReturn(true)

        assertEquals(opposite, endian.byteOrder)
        assertEquals(true, endian.byteSwapping)
    }

    @Test
    fun testSetEndian() {
        // Backing state to simulate a real implementation
        var current = Platform.endian

        whenever(endian.byteOrder).thenAnswer { current }
        whenever(endian.byteSwapping).thenAnswer { current != Platform.endian }

        doAnswer { invocation ->
            current = invocation.getArgument(0) as Platform.ENDIAN
            null
        }.whenever(endian).setEndian(any())

        val newEndian = if (Platform.endian == Platform.ENDIAN.BIG_ENDIAN)
            Platform.ENDIAN.LITTLE_ENDIAN else Platform.ENDIAN.BIG_ENDIAN

        endian.setEndian(newEndian)

        assertEquals(newEndian, endian.byteOrder)
        assertEquals(true, endian.byteSwapping)
    }
}