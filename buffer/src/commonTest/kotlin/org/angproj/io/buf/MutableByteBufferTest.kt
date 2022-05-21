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
package org.angproj.io.buf

import kotlin.test.Test

/**
 * Testing the MutableByteBuffer
 *
 * @constructor Create empty Mutable byte buffer test
 */
class MutableByteBufferTest : MutableBufferTest() {

    /**
     * Running tests on the MutableByteBuffer.
     */
    @Test
    fun mutableByteBuffer() {
        testMutableBufferRead(mutableByteBufferOf(populateArray(createArray())))
        testMutableBufferWrite(mutableByteBufferOf(populateArray(createArray())))
        testMutableBufferWriteReverse(mutableByteBufferOf(populateArray(createArray())))


        testMutableBufferRead(mutableByteBufferOf(populateArray(createArray())).toMutableNativeByteBuffer())
        testMutableBufferWrite(mutableByteBufferOf(populateArray(createArray())).toMutableNativeByteBuffer())
        testMutableBufferWriteReverse(mutableByteBufferOf(populateArray(createArray())).toMutableNativeByteBuffer())

        testMutableBufferRead(populateMutableBuffer(mutableByteBufferOf(refSize)))
        testMutableBufferWrite(populateMutableBuffer(mutableByteBufferOf(refSize)))
        testMutableBufferWriteReverse(populateMutableBuffer(mutableByteBufferOf(refSize)))
    }
}
