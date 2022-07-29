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
package org.angproj.io.buf.stream

import org.angproj.io.buf.NativeBuffer
import kotlin.test.Test

abstract class AbstractNativeStreamByteBufferTest : MutableStreamBufferTest() {

    /**
     * Running tests on the NativeByteBuffer.
     */
    @Test
    abstract fun nativeByteBuffer()

    fun doNativeByteBuffer() {
        testMutableStreamBufferRead(populateNativeBuffer(nativeStreamByteBufferOf(refSize)))

        testMutableStreamBufferRead(populateNativeBuffer(nativeStreamByteBufferOf(refSize)).toMutableStreamByteBuffer())
        testMutableStreamBufferWrite(populateNativeBuffer(nativeStreamByteBufferOf(refSize)).toMutableStreamByteBuffer())
        testMutableStreamBufferWriteReverse(populateNativeBuffer(nativeStreamByteBufferOf(refSize)).toMutableStreamByteBuffer())

        testMutableStreamBufferRead(populateNativeBuffer(nativeStreamByteBufferOf(refSize)).toMutableNativeStreamByteBuffer())
        testMutableStreamBufferWrite(populateNativeBuffer(nativeStreamByteBufferOf(refSize)).toMutableNativeStreamByteBuffer())
        testMutableStreamBufferWriteReverse(populateNativeBuffer(nativeStreamByteBufferOf(refSize)).toMutableNativeStreamByteBuffer())
    }
}

/**
 * Testing the NativeByteBuffer.
 *
 * @constructor Create empty Native byte buffer test
 */
expect class NativeStreamByteBufferTest : AbstractNativeStreamByteBufferTest {
    @Test
    override fun nativeByteBuffer()
}

/**
 * Populate native buffer by using pointer arithmetic. Only for testing purposes if applicable or functional.
 *
 * @param B
 * @param buf
 * @return
 */
expect fun <B : NativeBuffer> AbstractNativeStreamByteBufferTest.populateNativeBuffer(buf: B): B