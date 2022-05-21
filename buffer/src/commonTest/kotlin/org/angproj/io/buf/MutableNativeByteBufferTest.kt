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
 * Testing the MutableNativeByteBuffer.
 *
 * @constructor Create empty Native byte buffer test
 */
class MutableNativeByteBufferTest : MutableBufferTest() {

    /**
     * Running tests on the MutableNativeByteBuffer.
     */
    @Test
    fun mutableNativeByteBuffer() {
        testMutableBufferRead(populateMutableBuffer(mutableNativeByteBufferOf(refSize)))
        testMutableBufferWrite(populateMutableBuffer(mutableNativeByteBufferOf(refSize)))
        testMutableBufferWriteReverse(populateMutableBuffer(mutableNativeByteBufferOf(refSize)))

        testMutableBufferRead(populateMutableBuffer(mutableNativeByteBufferOf(refSize)).toMutableByteBuffer())
        testMutableBufferWrite(populateMutableBuffer(mutableNativeByteBufferOf(refSize)).toMutableByteBuffer())
        testMutableBufferWriteReverse(populateMutableBuffer(mutableNativeByteBufferOf(refSize)).toMutableByteBuffer())
    }
}