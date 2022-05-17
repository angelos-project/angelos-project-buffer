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
        testReferenceMutableBufferRead(mutableByteBufferOf(populateArray(createArray())))
        testReferenceMutableBufferWrite(mutableByteBufferOf(populateArray(createArray())))
        testReferenceMutableBufferWriteReverse(mutableByteBufferOf(populateArray(createArray())))


        testReferenceMutableBufferRead(mutableByteBufferOf(populateArray(createArray())).toMutableNativeByteBuffer())
        testReferenceMutableBufferWrite(mutableByteBufferOf(populateArray(createArray())).toMutableNativeByteBuffer())
        testReferenceMutableBufferWriteReverse(mutableByteBufferOf(populateArray(createArray())).toMutableNativeByteBuffer())

        testReferenceMutableBufferRead(populateMutableBuffer(mutableByteBufferOf(refSize)))
        testReferenceMutableBufferWrite(populateMutableBuffer(mutableByteBufferOf(refSize)))
        testReferenceMutableBufferWriteReverse(populateMutableBuffer(mutableByteBufferOf(refSize)))
    }
}
