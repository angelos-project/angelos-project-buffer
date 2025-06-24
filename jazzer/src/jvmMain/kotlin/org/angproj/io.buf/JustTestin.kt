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

import com.code_intelligence.jazzer.api.FuzzedDataProvider
import org.angproj.io.buf.util.DataSize
import org.angproj.io.buf.util.toInt
import org.angproj.sec.SecureRandom
import kotlin.random.Random
import kotlin.test.assertEquals
import kotlin.time.measureTime


public object JustTestinKt : FuzzPrefs() {

    @JvmStatic
    public fun fuzzerTestOneInput(data: FuzzedDataProvider) {
        BufMgr.withRam(DataSize._1K) {
            val buffer = it.asByteBuffer()
            val array = ByteArray(buffer.size) { data.consumeByte() }

            array.forEachIndexed { index, value ->
                buffer[index] = value
            }
            array.forEachIndexed { index, value ->
                assertEquals(value, buffer[index])
            }
        }
    }

    @JvmStatic
    public fun main(args: Array<String>) {
        BufMgr.withRam(DataSize._256M) {
            var time = measureTime { it.securelyRandomize() }
            println("Time taken to securely randomize: $time 1 GB of RAM")
        }
    }
}
