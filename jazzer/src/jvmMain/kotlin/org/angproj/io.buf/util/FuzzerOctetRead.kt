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

import com.code_intelligence.jazzer.Jazzer
import com.code_intelligence.jazzer.api.FuzzedDataProvider
import org.angproj.io.buf.FuzzPrefs
import kotlin.test.assertEquals

import java.nio.ByteBuffer
import java.nio.ByteOrder


public object FuzzerOctetReadKt : FuzzPrefs() {

    @JvmStatic
    public fun fuzzerTestOneInput(data: FuzzedDataProvider) {
        val value = data.consumeLong()
        val array = ByteArray(8)

        val buffer = ByteBuffer.wrap(array)
        if(ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
            buffer.order(ByteOrder.LITTLE_ENDIAN)
        }
        buffer.putLong(value)

        var loaded = 0L
        withUtility {
            loaded = array.readLongAt(0)
        }

        assertEquals(value, loaded)
    }

    @JvmStatic
    public fun main(args: Array<String>) {
        Jazzer.main(arrayOf(
            "--target_class=${javaClass.name}",
            "-max_total_time=${maxTotalTime}"
        ))
    }
}