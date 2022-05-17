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
        testReferenceMutableBufferRead(byteBufferOf(populateArray(createArray())))

        testReferenceMutableBufferRead(byteBufferOf(populateArray(createArray())).toMutableByteBuffer())
        testReferenceMutableBufferWrite(byteBufferOf(populateArray(createArray())).toMutableByteBuffer())
        testReferenceMutableBufferWriteReverse(byteBufferOf(populateArray(createArray())).toMutableByteBuffer())

        testReferenceMutableBufferRead(byteBufferOf(populateArray(createArray())).toMutableNativeByteBuffer())
        testReferenceMutableBufferWrite(byteBufferOf(populateArray(createArray())).toMutableNativeByteBuffer())
        testReferenceMutableBufferWriteReverse(byteBufferOf(populateArray(createArray())).toMutableNativeByteBuffer())
    }
}