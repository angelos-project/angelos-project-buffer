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
package org.angelos.io.buf

import kotlin.test.Test

/**
 * Testing the ByteBuffer
 *
 * @constructor Create empty Byte buffer test
 */
class ByteBufferTest : MutableBufferTest() {

    /**
     * Running tests on the ByteBuffer.
     */
    @Test
    fun byteBuffer() {
        testMutableBufferRead(byteBufferOf(populateArray(createArray())))

        testMutableBufferRead(byteBufferOf(populateArray(createArray())).toMutableByteBuffer())
        testMutableBufferWrite(byteBufferOf(populateArray(createArray())).toMutableByteBuffer())
        testMutableBufferWriteReverse(byteBufferOf(populateArray(createArray())).toMutableByteBuffer())

        testMutableBufferRead(byteBufferOf(populateArray(createArray())).toMutableNativeByteBuffer())
        testMutableBufferWrite(byteBufferOf(populateArray(createArray())).toMutableNativeByteBuffer())
        testMutableBufferWriteReverse(byteBufferOf(populateArray(createArray())).toMutableNativeByteBuffer())
    }
}