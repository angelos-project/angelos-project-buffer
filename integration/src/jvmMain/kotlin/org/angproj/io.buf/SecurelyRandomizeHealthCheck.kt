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

import org.angproj.io.buf.util.DataSize
import org.angproj.sec.stat.AvalancheEffectTester
import org.angproj.sec.stat.BenchmarkSession
import org.angproj.sec.stat.MonteCarloTester
import kotlin.math.PI
import kotlin.math.abs

public fun healthCheck(binary: Binary): Boolean {
    val objectSponge = BinaryBenchmark(binary)
    val samplesNeeded = MonteCarloTester.Mode.MODE_64_BIT.size * 10_000_000L / objectSponge.sampleByteSize

    val session = BenchmarkSession(samplesNeeded, objectSponge.sampleByteSize, objectSponge)
    val monteCarlo = session.registerTester { MonteCarloTester(10_000_000, MonteCarloTester.Mode.MODE_64_BIT, it) }
    val avalancheEffect = session.registerTester { AvalancheEffectTester(10_000_000, it) }

    session.startRun()
    repeat(samplesNeeded.toInt()) {
        session.collectSample()
    }
    session.stopRun()
    val results = session.finalizeCollecting()

    println(results[monteCarlo]!!.report)
    println(results[avalancheEffect]!!.report)
    return !(abs(0.5 - results[avalancheEffect]!!.keyValue) > 0.01 ||
            abs(PI - results[monteCarlo]!!.keyValue) > 0.01)
}


public fun main(args: Array<String>) {
    println("Health check of Binary.securelyRandomize() is called:")
    BufMgr.withRam(DataSize._1K) {
        healthCheck(it)
    }
}
