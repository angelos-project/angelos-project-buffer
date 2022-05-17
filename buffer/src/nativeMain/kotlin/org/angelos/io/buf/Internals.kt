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

import cbuffer.endian
import cbuffer.speedmemcpy
import kotlinx.cinterop.*

internal actual class Internals {
    actual companion object {
        actual fun getEndian(): Int = endian()

        actual fun nativeArrayAddress(array: ByteArray): TypePointer<Byte> = array.usePinned { it.addressOf(0).toLong() }

        actual fun copyInto(
            destination: TypePointer<Byte>,
            source: TypePointer<Byte>,
            length: Int
        ) {
            speedmemcpy(
                destination.toCPointer<Nothing>(),
                source.toCPointer<Nothing>(),
                length.toUInt()
            )
        }
    }
}