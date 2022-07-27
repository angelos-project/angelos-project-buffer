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
package org.angproj.io.buf

import kotlin.test.Test

abstract class AbstractNativeByteBufferTest : MutableBufferTest() {

    /**
     * Running tests on the NativeByteBuffer.
     */
    @Test
    abstract fun nativeByteBuffer()

    fun doNativeByteBuffer() {
        testMutableBufferRead(populateNativeBuffer(nativeByteBufferOf(refSize)))

        testMutableBufferRead(populateNativeBuffer(nativeByteBufferOf(refSize)).toMutableByteBuffer())
        testMutableBufferWrite(populateNativeBuffer(nativeByteBufferOf(refSize)).toMutableByteBuffer())
        testMutableBufferWriteReverse(populateNativeBuffer(nativeByteBufferOf(refSize)).toMutableByteBuffer())

        testMutableBufferRead(populateNativeBuffer(nativeByteBufferOf(refSize)).toMutableNativeByteBuffer())
        testMutableBufferWrite(populateNativeBuffer(nativeByteBufferOf(refSize)).toMutableNativeByteBuffer())
        testMutableBufferWriteReverse(populateNativeBuffer(nativeByteBufferOf(refSize)).toMutableNativeByteBuffer())
    }
}

/**
 * Testing the NativeByteBuffer.
 *
 * @constructor Create empty Native byte buffer test
 */
expect class NativeByteBufferTest : AbstractNativeByteBufferTest {
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
expect fun <B : NativeBuffer> AbstractNativeByteBufferTest.populateNativeBuffer(buf: B): B