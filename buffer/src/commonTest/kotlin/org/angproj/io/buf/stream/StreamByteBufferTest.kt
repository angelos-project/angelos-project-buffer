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

import org.angproj.io.buf.MutableBufferTest
import kotlin.test.Test

/**
 * Testing the ByteBuffer
 *
 * @constructor Create empty Byte buffer test
 */
class StreamByteBufferTest : MutableStreamBufferTest() {

    /**
     * Running tests on the ByteBuffer.
     */
    @Test
    fun byteBuffer() {
        testMutableStreamBufferRead(streamByteBufferOf(populateArray(createArray())))

        testMutableStreamBufferRead(streamByteBufferOf(populateArray(createArray())).toMutableStreamByteBuffer())
        testMutableStreamBufferWrite(streamByteBufferOf(populateArray(createArray())).toMutableStreamByteBuffer())
        testMutableStreamBufferWriteReverse(streamByteBufferOf(populateArray(createArray())).toMutableStreamByteBuffer())

        testMutableStreamBufferRead(streamByteBufferOf(populateArray(createArray())).toMutableNativeStreamByteBuffer())
        testMutableStreamBufferWrite(streamByteBufferOf(populateArray(createArray())).toMutableNativeStreamByteBuffer())
        testMutableStreamBufferWriteReverse(streamByteBufferOf(populateArray(createArray())).toMutableNativeStreamByteBuffer())
    }
}