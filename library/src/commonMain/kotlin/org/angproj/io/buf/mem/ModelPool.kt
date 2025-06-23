/**
 * Copyright (c) 2024-2025 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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
package org.angproj.io.buf.mem

import org.angproj.io.buf.seg.Model
import org.angproj.io.buf.util.DataSize
import org.angproj.io.buf.util.toInt
import org.angproj.sec.util.ceilDiv

public abstract class ModelPool(allocationSize: DataSize, minSize: DataSize, maxSize: DataSize
) : AbstractPoolManager<LongArray, Model>(allocationSize, minSize, maxSize) {

    protected var allocated: Int = 0

    override fun subAllocate(size: DataSize): LongArray {
        require(allocationSize.toInt() - allocated >= size.toInt()) {
            "Not enough memory available to allocate the requested size."
        }
        require(size.toInt() in minSize.toInt()..maxSize.toInt()) {
            "Requested size must be between minSize and maxSize."
        }

        allocated += size.toInt()
        return LongArray(size.toInt().ceilDiv(8)) // Assuming 64-bit long, hence dividing by 8
    }

    override fun createSegment(
        data: LongArray
    ): Model {
        return Model(this, data)
    }
}