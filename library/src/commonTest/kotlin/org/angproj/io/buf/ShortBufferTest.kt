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

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue


class ShortBufferTest: AbstractArrayBufferTest<Short>() {

    override val refValue: Short = TestInformationStub.refShort

    override fun setInput(): ShortBuffer {
        val lb = BufMgr.bin(capValue).asShortBuffer()
        (0 until lb.limit).forEach {
            lb[it] = refValue
        }
        return lb
    }

    @Test
    fun testNullByteBuffer() {
        assertTrue(ArrayBuffer.nullBuffer.isNull())
        assertFalse(setInput().isNull())
    }
}