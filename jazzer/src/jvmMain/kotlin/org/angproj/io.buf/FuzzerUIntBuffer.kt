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
package org.angproj.io.buf

import com.code_intelligence.jazzer.Jazzer
import com.code_intelligence.jazzer.api.FuzzedDataProvider
import org.angproj.io.buf.util.DataSize
import org.angproj.io.buf.util.withUtility
import kotlin.test.assertEquals


public object FuzzerUIntBufferKt : FuzzPrefs() {

    @JvmStatic
    public fun fuzzerTestOneInput(data: FuzzedDataProvider): Unit = withUtility {
        BufMgr.withRam(DataSize._1K) {
            val buffer = it.asUIntBuffer()
            val array = IntArray(buffer.size) { data.consumeInt() }

            array.forEachIndexed { index, value ->
                buffer[index] = value.conv2uI()
            }
            array.forEachIndexed { index, value ->
                assertEquals(value, buffer[index].conv2I())
            }
        }
    }

    @JvmStatic
    public fun main(args: Array<String>) {
        Jazzer.main(arrayOf(
            "--target_class=${javaClass.name}",
            "-max_total_time=${maxTotalTime}"
        ))
    }
}