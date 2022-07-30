/**
 * Copyright (c) 2021-2022 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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

import org.angproj.io.buf.NativeBuffer

/**
 * Populate native buffer, not possible in JS.
 *
 * @param B
 * @param buf
 * @return
 */
actual fun <B : NativeBuffer> AbstractNativeDataByteBufferTest.populateNativeBuffer(buf: B): B {
    error("Virtually impossible to fill a 'native' buffer in JS.")
}

/**
 * Testing the NativeByteBuffer.
 *
 * @constructor Create empty Native byte buffer test
 */
actual class NativeDataByteBufferTest : AbstractNativeDataByteBufferTest() {
    actual override fun nativeByteBuffer() {
    }
}