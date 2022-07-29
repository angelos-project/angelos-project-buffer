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

import kotlin.test.Test

/**
 * Testing the ByteBuffer
 *
 * @constructor Create empty Byte buffer test
 */
class DataByteBufferTest : MutableDataBufferTest() {

    /**
     * Running tests on the ByteBuffer.
     */
    @Test
    fun byteBuffer() {
        testMutableDataBufferRead(streamByteBufferOf(populateArray(createArray())))

        testMutableDataBufferRead(streamByteBufferOf(populateArray(createArray())).toMutableStreamByteBuffer())
        testMutableDataBufferWrite(streamByteBufferOf(populateArray(createArray())).toMutableStreamByteBuffer())
        testMutableDataBufferWriteReverse(streamByteBufferOf(populateArray(createArray())).toMutableStreamByteBuffer())

        testMutableDataBufferRead(streamByteBufferOf(populateArray(createArray())).toMutableNativeStreamByteBuffer())
        testMutableDataBufferWrite(streamByteBufferOf(populateArray(createArray())).toMutableNativeStreamByteBuffer())
        testMutableDataBufferWriteReverse(streamByteBufferOf(populateArray(createArray())).toMutableNativeStreamByteBuffer())
    }
}