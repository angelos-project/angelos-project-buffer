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

import org.khronos.webgl.Uint32Array
import org.khronos.webgl.Uint8Array
import org.khronos.webgl.get
import org.khronos.webgl.set
import kotlin.experimental.and

internal actual class Internals {
    actual companion object {
        actual fun getEndian(): Int {
            val array = Uint8Array(4)
            val view = Uint32Array(array.buffer)
            view[0] = 1
            return when ((view[0].toShort() and array[0].toShort()).countOneBits()) {
                0 -> 1
                1 -> 2
                else -> 0
            }
        }

        actual fun copyInto(
            destination: MutableBuffer,
            destinationOffset: Int,
            source: Buffer,
            startIndex: Int,
            endIndex: Int,
        ) {
            Buffer.copyIntoContract(destination, destinationOffset, source, startIndex, endIndex)
            source.getArray().copyInto(destination.getArray(), destinationOffset, startIndex, endIndex)
        }

        actual fun reset(destination: MutableBuffer) {
            val dest = destination.getArray()
            for (index in dest.indices)
                dest[index] = 0
        }
    }
}