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
package org.angproj.io.buf

import cbuffer.speedmemcpy
import kotlinx.cinterop.*

/**
 * Populate native buffer by unsafe access to memory. Necessary to test native immutable buffer in Native.
 *
 * @param B
 * @param buf
 * @return
 */
actual fun <B : NativeBuffer> AbstractNativeByteBufferTest.populateNativeBuffer(buf: B): B {
    val ptr = buf.getPointer()
    val arr = populateArray(refArray.copyOf())
    memScoped {
        arr.usePinned {
            speedmemcpy(ptr.toCPointer<ByteVar>(), arr.refTo(0), arr.size.toUInt())
        }
    }
    return buf
}

/**
 * Testing the NativeByteBuffer.
 *
 * @constructor Create empty Native byte buffer test
 */
actual class NativeByteBufferTest : AbstractNativeByteBufferTest() {
    actual override fun nativeByteBuffer() {
        doNativeByteBuffer()
    }

}