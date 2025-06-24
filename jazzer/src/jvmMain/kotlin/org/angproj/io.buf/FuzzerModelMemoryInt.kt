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
import org.angproj.io.buf.mem.SingleMemoryPool
import org.angproj.io.buf.mem.SingleModelPool
import org.angproj.io.buf.util.DataSize
import org.angproj.sec.util.TypeSize
import kotlin.test.assertEquals


public object FuzzerModelMemoryIntKt : FuzzPrefs() {

    @JvmStatic
    public fun fuzzerTestOneInput(data: FuzzedDataProvider) {
        val dataSize = DataSize._128B
        val typeSize = TypeSize.intSize
        val model = SingleModelPool(dataSize).allocate()
        val memory = SingleMemoryPool(dataSize).allocate()

        val loops = dataSize.toInt() / typeSize
        repeat(loops) {
            val value = data.consumeInt()
            model.setInt(it * typeSize, value)
            memory.setInt(it * typeSize, value)
        }

        repeat(loops) {
            val expected = model.getInt(it * typeSize)
            val actual = memory.getInt(it * typeSize)
            assertEquals(expected, actual, "Mismatch at index $it")
        }

        assertEquals(model.checkSum(), memory.checkSum(), "Hash codes do not match")

    }

    @JvmStatic
    public fun main(args: Array<String>) {
        Jazzer.main(arrayOf(
            "--target_class=${javaClass.name}",
            "-max_total_time=${maxTotalTime}"
        ))
    }
}