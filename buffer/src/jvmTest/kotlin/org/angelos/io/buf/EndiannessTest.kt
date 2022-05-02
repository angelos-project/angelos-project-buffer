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

import java.nio.ByteOrder
import kotlin.test.Test
import kotlin.test.assertEquals

actual class EndiannessTest : AbstractEndianTest() {
    @Test
    actual fun nativeOrder() {
        when(Endianness.nativeOrder()) {
            Endianness.BIG_ENDIAN -> assertEquals(ByteOrder.nativeOrder(), ByteOrder.BIG_ENDIAN)
            Endianness.LITTLE_ENDIAN -> assertEquals(ByteOrder.nativeOrder(), ByteOrder.LITTLE_ENDIAN)
        }
    }
}