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

import kotlin.test.Test
import kotlin.test.assertNotNull

/**
 * Running tests for Endianness.
 *
 * @constructor Create empty Endianness test
 */
actual class EndiannessTest : AbstractEndianTest() {

    /**
     * Running tests for nativeOrder on JS never reaches the underlying system.
     *
     */
    @Test
    actual fun nativeOrder() {
        val endianness = Endianness.nativeOrder()
        assertNotNull(endianness)
    }
}