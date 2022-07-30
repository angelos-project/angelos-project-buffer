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
        testMutableDataBufferRead(dataByteBufferOf(populateArray(createArray())))

        testMutableDataBufferRead(dataByteBufferOf(populateArray(createArray())).toMutableDataByteBuffer())
        testMutableDataBufferWrite(dataByteBufferOf(populateArray(createArray())).toMutableDataByteBuffer())
        testMutableDataBufferWriteReverse(dataByteBufferOf(populateArray(createArray())).toMutableDataByteBuffer())

        testMutableDataBufferRead(dataByteBufferOf(populateArray(createArray())).toMutableNativeDataByteBuffer())
        testMutableDataBufferWrite(dataByteBufferOf(populateArray(createArray())).toMutableNativeDataByteBuffer())
        testMutableDataBufferWriteReverse(dataByteBufferOf(populateArray(createArray())).toMutableNativeDataByteBuffer())
    }
}