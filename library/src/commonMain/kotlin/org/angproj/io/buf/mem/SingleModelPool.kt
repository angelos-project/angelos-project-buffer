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


/**
 * This class is a one time use pool, meaning it is designed to allocate a single
 * segment of bytes and then dispose of itself after recycling that segment.
 *
 * @param segmentSize The size of each segment in the pool.
 */
public class SingleModelPool(
    segmentSize: DataSize,
) : ModelPool(segmentSize, segmentSize, segmentSize) {

    /**
     * Recycles the given segment by calling the superclass's recycle method and then disposing of the pool.
     *
     * @param segment The segment to recycle.
     */
    override fun recycle(segment: Model) {
        super.recycle(segment)
        dispose()
    }
}