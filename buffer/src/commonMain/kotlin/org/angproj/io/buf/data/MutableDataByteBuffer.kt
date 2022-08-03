/**
 * Copyright (c) 2022 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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
package org.angproj.io.buf.data

import org.angproj.io.buf.Endianness

/**
 * A common expect class for the mutable data-buffer that is allocated on the heap.
 * Implementation practices may vary depending on source target and platform abilities.
 *
 * @constructor
 *
 * @param array An underlying ByteArray that may already have been populated with data at a certain limit.
 * @param size Total size of the buffer.
 * @param limit The initial limitation of how far to operate into the buffer. Must never exceed the size.
 * @param endianness The initial current endianness of the buffer.
 */
expect class MutableDataByteBuffer internal constructor(
    array: ByteArray,
    size: Int,
    limit: Int,
    endianness: Endianness,
) : AbstractMutableDataBuffer, MutableHeapDataBuffer