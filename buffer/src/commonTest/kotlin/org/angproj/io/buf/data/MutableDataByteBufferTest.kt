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
 * Testing the MutableByteBuffer
 *
 * @constructor Create empty Mutable byte buffer test
 */
class MutableDataByteBufferTest : MutableDataBufferTest() {

    /**
     * Running tests on the MutableByteBuffer.
     */
    @Test
    fun mutableByteBuffer() {
        testMutableDataBufferRead(mutableStreamByteBufferOf(populateArray(createArray())))
        testMutableDataBufferWrite(mutableStreamByteBufferOf(populateArray(createArray())))
        testMutableDataBufferWriteReverse(mutableStreamByteBufferOf(populateArray(createArray())))


        testMutableDataBufferRead(mutableStreamByteBufferOf(populateArray(createArray())).toMutableNativeStreamByteBuffer())
        testMutableDataBufferWrite(mutableStreamByteBufferOf(populateArray(createArray())).toMutableNativeStreamByteBuffer())
        testMutableDataBufferWriteReverse(mutableStreamByteBufferOf(populateArray(createArray())).toMutableNativeStreamByteBuffer())

        testMutableDataBufferRead(populateMutableDataBuffer(mutableStreamByteBufferOf(refSize)))
        testMutableDataBufferWrite(populateMutableDataBuffer(mutableStreamByteBufferOf(refSize)))
        testMutableDataBufferWriteReverse(populateMutableDataBuffer(mutableStreamByteBufferOf(refSize)))
    }
}
