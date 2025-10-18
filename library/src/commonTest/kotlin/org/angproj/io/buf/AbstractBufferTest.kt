/**
 * Copyright (c) 2024-2025 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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

import org.angproj.io.buf.util.UtilityAware
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse

abstract class AbstractBufferTest<E: AbstractBuffer>: UtilityAware {

    protected abstract fun setInput(): E

    @Test
    fun isView() {
        assertEquals(setInput().isView(), true)
    }

    @Test
    fun isMem() {
        assertEquals(setInput().isMem(), false)
    }

    @Test
    fun close() {
        setInput().close()
    }

    @Test
    fun testHashCode() {
        val input = setInput()
        val hashCode = input.hashCode()
        assertEquals(hashCode, input.hashCode(), "Hash code should be consistent")
    }

    @Test
    fun testCopyInto() {
        val a = setInput()
        a.segment.securelyRandomize()
        val b = setInput()

        assertFalse { a.contentEquals(b) }

        a.copyInto(b, 0, 0, b.capacity)

        assertTrue { a.contentEquals(b) }
    }
}