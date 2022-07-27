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

import cbuffer.endian
import cbuffer.speedmemcpy
import kotlinx.cinterop.refTo
import kotlinx.cinterop.toCPointer
import kotlinx.cinterop.usePinned

internal actual class Internals {
    actual companion object {
        private val theArray = ByteArray(0) // Dummy array to access some native memory functionality.
        actual fun getEndian(): Int = endian()

        actual fun copyInto(
            destination: MutableBuffer,
            destinationOffset: Int,
            source: Buffer,
            startIndex: Int,
            endIndex: Int,
        ) {
            Buffer.copyIntoContract(destination, destinationOffset, source, startIndex, endIndex)
            theArray.usePinned {
                val src = when (source) {
                    is NativeBuffer -> (source.getPointer() + startIndex).toCPointer()!!
                    is HeapBuffer -> source.getArray().refTo(startIndex)
                    else -> error("Unknown buffer type, cannot copy!")
                }
                val dst = when (destination) {
                    is NativeBuffer -> (destination.getPointer() + destinationOffset).toCPointer()!!
                    is HeapBuffer -> destination.getArray().refTo(destinationOffset)
                    else -> error("Unknown mutable buffer type, cannot copy!")
                }
                speedmemcpy(dst, src, (endIndex - startIndex).toUInt())
            }
        }
    }
}