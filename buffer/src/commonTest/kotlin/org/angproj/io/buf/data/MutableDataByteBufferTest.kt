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
        testMutableDataBufferRead(mutableDataByteBufferOf(populateArray(createArray())))
        testMutableDataBufferWrite(mutableDataByteBufferOf(populateArray(createArray())))
        testMutableDataBufferWriteReverse(mutableDataByteBufferOf(populateArray(createArray())))


        testMutableDataBufferRead(mutableDataByteBufferOf(populateArray(createArray())).toMutableNativeDataByteBuffer())
        testMutableDataBufferWrite(mutableDataByteBufferOf(populateArray(createArray())).toMutableNativeDataByteBuffer())
        testMutableDataBufferWriteReverse(mutableDataByteBufferOf(populateArray(createArray())).toMutableNativeDataByteBuffer())

        testMutableDataBufferRead(populateMutableDataBuffer(mutableDataByteBufferOf(refSize)))
        testMutableDataBufferWrite(populateMutableDataBuffer(mutableDataByteBufferOf(refSize)))
        testMutableDataBufferWriteReverse(populateMutableDataBuffer(mutableDataByteBufferOf(refSize)))
    }
}
