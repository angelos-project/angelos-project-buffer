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

import cbuffer.speedmemcpy
import kotlinx.cinterop.*

actual fun <B: NativeBuffer> NativeByteBufferTest.populateNativeBuffer(buf: B): B {
    val ptr = buf.getPointer()
    val arr =  populateArray(refArray.copyOf())
    memScoped {
        arr.usePinned {
            speedmemcpy(ptr.toCPointer<ByteVar>(), arr.refTo(0), arr.size.toUInt())
        }
    }
    return buf
}