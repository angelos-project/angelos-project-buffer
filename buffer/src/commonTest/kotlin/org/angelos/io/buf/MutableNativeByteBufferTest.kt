/**
 * Copyright (c) 2022 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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
package org.angelos.io.buf

import kotlin.test.Test

/**
 * Testing the MutableNativeByteBuffer.
 *
 * @constructor Create empty Native byte buffer test
 */
class MutableNativeByteBufferTest : BufferTest() {

    /**
     * Running tests on the MutableNativeByteBuffer.
     */
    @Test
    fun mutableNativeByteBuffer() {
        val buf = mutableNativeByteBufferOf(size)
        writeAny(buf)
        buf.rewind()
        readAny(buf)

        val mbuf = buf.toMutableByteBuffer()
        mbuf.rewind()
        readAny(mbuf)
    }
}