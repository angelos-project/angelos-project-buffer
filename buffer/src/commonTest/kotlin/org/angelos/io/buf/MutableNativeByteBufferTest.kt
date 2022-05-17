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
        testReferenceMutableBufferRead(populateMutableBuffer(mutableNativeByteBufferOf(refSize)))
        testReferenceMutableBufferWrite(populateMutableBuffer(mutableNativeByteBufferOf(refSize)))
        testReferenceMutableBufferWriteReverse(populateMutableBuffer(mutableNativeByteBufferOf(refSize)))

        testReferenceMutableBufferRead(populateMutableBuffer(mutableNativeByteBufferOf(refSize)).toMutableByteBuffer())
        testReferenceMutableBufferWrite(populateMutableBuffer(mutableNativeByteBufferOf(refSize)).toMutableByteBuffer())
        testReferenceMutableBufferWriteReverse(populateMutableBuffer(mutableNativeByteBufferOf(refSize)).toMutableByteBuffer())
    }
}