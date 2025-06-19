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
 * Testing the MutableNativeByteBuffer.
 *
 * @constructor Create empty Native byte buffer test
 */
class MutableNativeStreamByteBufferTest : MutableStreamBufferTest() {

    /**
     * Running tests on the MutableNativeByteBuffer.
     */
    @Test
    fun mutableNativeByteBuffer() {
        testMutableStreamBufferRead(populateMutableStreamBuffer(mutableNativeStreamByteBufferOf(refSize)))
        testMutableStreamBufferWrite(populateMutableStreamBuffer(mutableNativeStreamByteBufferOf(refSize)))
        testMutableStreamBufferWriteReverse(populateMutableStreamBuffer(mutableNativeStreamByteBufferOf(refSize)))

        testMutableStreamBufferRead(populateMutableStreamBuffer(mutableNativeStreamByteBufferOf(refSize)).toMutableStreamByteBuffer())
        testMutableStreamBufferWrite(populateMutableStreamBuffer(mutableNativeStreamByteBufferOf(refSize)).toMutableStreamByteBuffer())
        testMutableStreamBufferWriteReverse(populateMutableStreamBuffer(mutableNativeStreamByteBufferOf(refSize)).toMutableStreamByteBuffer())
    }
}