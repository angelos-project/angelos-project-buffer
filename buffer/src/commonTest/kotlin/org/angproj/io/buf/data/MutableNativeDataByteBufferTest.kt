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

import org.angproj.io.buf.stream.toMutableStreamByteBuffer
import kotlin.test.Test

/**
 * Testing the MutableNativeByteBuffer.
 *
 * @constructor Create empty Native byte buffer test
 */
class MutableNativeDataByteBufferTest : MutableDataBufferTest() {

    /**
     * Running tests on the MutableNativeByteBuffer.
     */
    @Test
    fun mutableNativeByteBuffer() {
        testMutableDataBufferRead(populateMutableDataBuffer(mutableNativeDataByteBufferOf(refSize)))
        testMutableDataBufferWrite(populateMutableDataBuffer(mutableNativeDataByteBufferOf(refSize)))
        testMutableDataBufferWriteReverse(populateMutableDataBuffer(mutableNativeDataByteBufferOf(refSize)))

        testMutableDataBufferRead(populateMutableDataBuffer(mutableNativeDataByteBufferOf(refSize)).toMutableDataByteBuffer())
        testMutableDataBufferWrite(populateMutableDataBuffer(mutableNativeDataByteBufferOf(refSize)).toMutableDataByteBuffer())
        testMutableDataBufferWriteReverse(populateMutableDataBuffer(mutableNativeDataByteBufferOf(refSize)).toMutableDataByteBuffer())
    }
}