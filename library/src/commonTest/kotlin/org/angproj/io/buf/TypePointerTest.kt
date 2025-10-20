/**
 * Copyright (c) 2022-2025 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertContains

class TypePointerTest {

    val ptr: Long = 0x1234567887654321L

    @Test
    fun testTypePointer() {
        val typePointer = TypePointer<SegmentBlock>(ptr)

        assertEquals(ptr, typePointer.ptr)
    }

    @Test
    fun testToPointer() {
        val typePointer = TypePointer<SegmentBlock>(ptr)

        assertEquals(typePointer.ptr, typePointer.toPointer().ptr)
    }

    @Test
    fun testToString() {
        val typePointer = TypePointer<SegmentBlock>(ptr)

        assertContains(typePointer.toString(), "TypePointer(ptr=0x1234567887654321)")
    }
}