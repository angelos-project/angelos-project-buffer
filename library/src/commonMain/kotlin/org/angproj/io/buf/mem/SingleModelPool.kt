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

public class SingleModelPool(
    segmentSize: DataSize,
) : ModelPool(segmentSize, segmentSize, segmentSize) {
    // This class is intentionally left empty. It serves as a concrete implementation of BytesPool
    // that can be used to create instances of BytesPool with arbitrary sizes.
    // The actual allocation and recycling logic is handled in the BytesPool class.

    override fun recycle(segment: Model) {
        super.recycle(segment)
        dispose()
    }
}