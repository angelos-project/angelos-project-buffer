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

import org.angproj.io.buf.seg.Segment
import org.angproj.io.buf.util.DataSize

public interface MemoryManager<S: Segment<S>> {

    public val allocationSize: DataSize
    public val segmentSize: DataSize

    public fun allocate(): S

    public fun allocate(size: Int): S

    public fun recycle(segment: S)
}