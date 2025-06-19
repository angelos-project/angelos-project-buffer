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

import kotlin.test.Test

/**
 * Testing the MutableByteBuffer
 *
 * @constructor Create empty Mutable byte buffer test
 */
class MutableStreamByteBufferTest : MutableStreamBufferTest() {

    /**
     * Running tests on the MutableByteBuffer.
     */
    @Test
    fun mutableByteBuffer() {
        testMutableStreamBufferRead(mutableStreamByteBufferOf(populateArray(createArray())))
        testMutableStreamBufferWrite(mutableStreamByteBufferOf(populateArray(createArray())))
        testMutableStreamBufferWriteReverse(mutableStreamByteBufferOf(populateArray(createArray())))


        testMutableStreamBufferRead(mutableStreamByteBufferOf(populateArray(createArray())).toMutableNativeStreamByteBuffer())
        testMutableStreamBufferWrite(mutableStreamByteBufferOf(populateArray(createArray())).toMutableNativeStreamByteBuffer())
        testMutableStreamBufferWriteReverse(mutableStreamByteBufferOf(populateArray(createArray())).toMutableNativeStreamByteBuffer())

        testMutableStreamBufferRead(populateMutableStreamBuffer(mutableStreamByteBufferOf(refSize)))
        testMutableStreamBufferWrite(populateMutableStreamBuffer(mutableStreamByteBufferOf(refSize)))
        testMutableStreamBufferWriteReverse(populateMutableStreamBuffer(mutableStreamByteBufferOf(refSize)))
    }
}
