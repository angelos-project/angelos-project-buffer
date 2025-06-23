/**
 * Copyright (c) 2024 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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

import org.angproj.io.buf.seg.Bytes
import org.angproj.io.buf.util.DataSize
import org.angproj.io.buf.util.toInt


public object Default: MemoryManager<Bytes> {
    override val allocationSize: DataSize
        get() = DataSize.UNKNOWN

    override val segmentSize: DataSize
        get() = DataSize._4K

    override fun allocate(): Bytes = Bytes(this, ByteArray(segmentSize.toInt()))

    override fun allocate(size: Int): Bytes = Bytes(this, ByteArray(size))


    override fun recycle(segment: Bytes) { segment }
}