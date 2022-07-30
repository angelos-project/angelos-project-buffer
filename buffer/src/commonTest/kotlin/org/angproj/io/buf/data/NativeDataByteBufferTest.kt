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
package org.angproj.io.buf.data

import org.angproj.io.buf.NativeBuffer
import kotlin.test.Test

abstract class AbstractNativeDataByteBufferTest : MutableDataBufferTest() {

    /**
     * Running tests on the NativeByteBuffer.
     */
    @Test
    abstract fun nativeByteBuffer()

    fun doNativeByteBuffer() {
        testMutableDataBufferRead(populateNativeBuffer(nativeDataByteBufferOf(refSize)))

        testMutableDataBufferRead(populateNativeBuffer(nativeDataByteBufferOf(refSize)).toMutableDataByteBuffer())
        testMutableDataBufferWrite(populateNativeBuffer(nativeDataByteBufferOf(refSize)).toMutableDataByteBuffer())
        testMutableDataBufferWriteReverse(populateNativeBuffer(nativeDataByteBufferOf(refSize)).toMutableDataByteBuffer())

        testMutableDataBufferRead(populateNativeBuffer(nativeDataByteBufferOf(refSize)).toMutableNativeDataByteBuffer())
        testMutableDataBufferWrite(populateNativeBuffer(nativeDataByteBufferOf(refSize)).toMutableNativeDataByteBuffer())
        testMutableDataBufferWriteReverse(populateNativeBuffer(nativeDataByteBufferOf(refSize)).toMutableNativeDataByteBuffer())
    }
}

/**
 * Testing the NativeByteBuffer.
 *
 * @constructor Create empty Native byte buffer test
 */
expect class NativeDataByteBufferTest : AbstractNativeDataByteBufferTest {
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
expect fun <B : NativeBuffer> AbstractNativeDataByteBufferTest.populateNativeBuffer(buf: B): B