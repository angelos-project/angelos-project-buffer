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
import org.angproj.io.buf.mem.SingleMemoryPool
import org.angproj.io.buf.util.DataSize
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
        val memA = SingleMemoryPool(DataSize._64M).allocate()
        val memB = SingleMemoryPool(DataSize._64M).allocate()

        var time2 = measureTime {}

        repeat(16) { i ->
            memA.securelyRandomize()
            time2 += measureTime {
                var pos = 0
                /*repeat(memA.size / 8) {
                    memB.setLong(pos, memA.getLong(pos))
                    pos += 8
                }*/

                do {
                    memB.setLong(pos, memA.getLong(pos))
                    pos += 8
                } while (pos < memA.size)

                /*for (i in 0 until memA.size step 8) {
                    memB.setLong(i, memA.getLong(i))
                }*/
            }
            if(memA.checkSum() != memB.checkSum()) {
                throw AssertionError("Byte arrays do not match after copy")
            }
        }

        println("Time taken to fill Memory with: $time2")
        /*BufMgr.withRam(DataSize._1G) {
            var time = measureTime { it.securelyRandomize() }
            println("Time taken to securely randomize: $time 1 GB of RAM")
        }*/
        val bytesA = ByteArray(DataSize._64M.toInt())
        val bytesB = ByteArray(DataSize._64M.toInt())

        var time1 = measureTime {}

        repeat(16) { i ->
            Random.nextBytes(bytesA)
            time1 += measureTime {
                bytesA.copyInto(bytesB)
            }
            if(!bytesA.contentEquals(bytesB)) {
                throw AssertionError("Byte arrays do not match after copy")
            }
        }

        println("Time taken to fill ByteArray with: $time1")

    }
}
