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

class MutableByteBufferTest : BufferTest() {

    @Test
    fun mutableByteBuffer() {
        val buf = mutableByteBufferOf(refWrite.copyOf())
        writeAny(buf)
        buf.rewind()
        readAny(buf)

        val mnbuf = buf.toMutableNativeByteBuffer()
        mnbuf.rewind()
        readAny(mnbuf)

        val buf2 = mutableByteBufferOf(size)
        for(idx in buf2.position until buf2.size) {
            buf2.setNextByte(idx.toByte())
        }
        buf2.rewind()
        writeAny(buf2)
        buf2.rewind()
        readAny(buf2)
    }
}
