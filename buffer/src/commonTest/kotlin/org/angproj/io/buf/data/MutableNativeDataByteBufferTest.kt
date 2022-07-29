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
        testMutableDataBufferRead(populateMutableDataBuffer(mutableNativeStreamByteBufferOf(refSize)))
        testMutableDataBufferWrite(populateMutableDataBuffer(mutableNativeStreamByteBufferOf(refSize)))
        testMutableDataBufferWriteReverse(populateMutableDataBuffer(mutableNativeStreamByteBufferOf(refSize)))

        testMutableDataBufferRead(populateMutableDataBuffer(mutableNativeStreamByteBufferOf(refSize)).toMutableStreamByteBuffer())
        testMutableDataBufferWrite(populateMutableDataBuffer(mutableNativeStreamByteBufferOf(refSize)).toMutableStreamByteBuffer())
        testMutableDataBufferWriteReverse(populateMutableDataBuffer(mutableNativeStreamByteBufferOf(refSize)).toMutableStreamByteBuffer())
    }
}