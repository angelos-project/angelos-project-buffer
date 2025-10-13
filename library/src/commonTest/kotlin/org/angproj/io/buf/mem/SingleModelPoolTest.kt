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
package org.angproj.io.buf.mem


import org.angproj.io.buf.util.DataSize
import org.angproj.io.buf.seg.Model

class SingleModelPoolTest : AbstractFixedPoolTest<Model, SingleModelPool>() {

    override val minSize = DataSize._32B

    override fun createPool(): SingleModelPool =
        SingleModelPool(minSize)

    override fun allocate(pool: SingleModelPool, size: Int): Model =
        pool.allocate(size)

    override fun getSize(obj: Model): Int = obj.size

    override fun recycle(pool: SingleModelPool, obj: Model) {
        pool.recycle(obj)
    }
}