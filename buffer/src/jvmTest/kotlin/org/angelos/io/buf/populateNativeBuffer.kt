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
package org.angelos.io.buf

/**
 * Populate native buffer by unsafe access to memory. Necessary to test native immutable buffer in JVM.
 *
 * @param B
 * @param buf
 * @return
 */
actual fun <B : NativeBuffer> NativeByteBufferTest.populateNativeBuffer(buf: B): B {
    val ptr = buf.getPointer()
    val arr = populateArray(refArray.copyOf())
    for (idx in arr.indices step 8) {
        Internals.unsafe.putLong(ptr + idx, Internals.unsafe.getLong(arr, Internals.byteArrayOffset + idx))
    }
    return buf
}