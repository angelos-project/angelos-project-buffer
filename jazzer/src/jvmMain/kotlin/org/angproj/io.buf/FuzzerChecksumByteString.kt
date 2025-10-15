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
import org.angproj.sec.util.floorMod
import kotlin.test.assertNotEquals


public object FuzzerChecksumByteStringKt : FuzzPrefs() {

    @JvmStatic
    public fun fuzzerTestOneInput(data: FuzzedDataProvider) {
        BufMgr.withRam(DataSize._1K) {
            val buffer = it.asByteBuffer()
            buffer.forEachIndexed {
                index, _ ->
                buffer[index] = data.consumeByte()
            }
            val hashCode = buffer.hashCode()

            val index = data.consumeShort().toInt().floorMod(buffer.limit)
            val offset = data.consumeByte().toInt().floorMod(8)

            withUtility {
                val value = buffer[index]
                buffer[index] = when(offset) {
                    0 -> if(value.checkFlag0()) value.flipOffFlag0() else value.flipOnFlag0()
                    1 -> if(value.checkFlag1()) value.flipOffFlag1() else value.flipOnFlag1()
                    2 -> if(value.checkFlag2()) value.flipOffFlag2() else value.flipOnFlag2()
                    3 -> if(value.checkFlag3()) value.flipOffFlag3() else value.flipOnFlag3()
                    4 -> if(value.checkFlag4()) value.flipOffFlag4() else value.flipOnFlag4()
                    5 -> if(value.checkFlag5()) value.flipOffFlag5() else value.flipOnFlag5()
                    6 -> if(value.checkFlag6()) value.flipOffFlag6() else value.flipOnFlag6()
                    7 -> if(value.checkFlag7()) value.flipOffFlag7() else value.flipOnFlag7()
                    else -> throw IllegalArgumentException("Invalid offset: $offset")
                }
            }

            assertNotEquals(hashCode, buffer.hashCode(), "Hash code should change after modification")
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