/**
 * Copyright (c) 2025 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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
package org.angproj.io.buf.util

import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertSame

class OnceTest {

    class Holder {
        var buddy by Once<Buddy>()
    }

    class Buddy {}

    @Test
    fun testGetValueEmpty() {
        val holder = Holder()
        assertFailsWith<UninitializedPropertyAccessException> {
            holder.buddy
        }
    }

    @Test
    fun testSetGet() {
        val buddy = Buddy()
        val holder = Holder()

        holder.buddy = buddy
        assertSame(buddy, holder.buddy)
    }

    @Test
    fun testDoubleSet() {
        val buddy = Buddy()
        val holder = Holder()

        holder.buddy = buddy

        assertFailsWith<IllegalStateException> {
            holder.buddy = Buddy()
        }
    }
}