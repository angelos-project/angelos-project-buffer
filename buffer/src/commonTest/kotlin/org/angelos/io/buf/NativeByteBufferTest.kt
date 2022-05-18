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
 * Testing the NativeByteBuffer.
 *
 * @constructor Create empty Native byte buffer test
 */
class NativeByteBufferTest : MutableBufferTest() {

    /**
     * Running tests on the NativeByteBuffer.
     */
    //@Ignore // There is no implemented way to enter data in an immutable native buffer.
    @Test
    fun nativeByteBuffer() {
        testMutableBufferRead(populateNativeBuffer(nativeByteBufferOf(refSize)))

        testMutableBufferRead(populateNativeBuffer(nativeByteBufferOf(refSize)).toMutableByteBuffer())
        testMutableBufferWrite(populateNativeBuffer(nativeByteBufferOf(refSize)).toMutableByteBuffer())
        testMutableBufferWriteReverse(populateNativeBuffer(nativeByteBufferOf(refSize)).toMutableByteBuffer())

        testMutableBufferRead(populateNativeBuffer(nativeByteBufferOf(refSize)).toMutableNativeByteBuffer())
        testMutableBufferWrite(populateNativeBuffer(nativeByteBufferOf(refSize)).toMutableNativeByteBuffer())
        testMutableBufferWriteReverse(populateNativeBuffer(nativeByteBufferOf(refSize)).toMutableNativeByteBuffer())
    }
}

/**
 * Populate native buffer by using pointer arithmetic. Only for testing purposes if applicable or functional.
 *
 * @param B
 * @param buf
 * @return
 */
expect fun <B : NativeBuffer> NativeByteBufferTest.populateNativeBuffer(buf: B): B