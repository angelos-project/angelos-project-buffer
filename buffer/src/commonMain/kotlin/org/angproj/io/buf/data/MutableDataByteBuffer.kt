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
 * Mutable data byte buffer implementation on the heap.
 *
 * @constructor
 *
 * @param array
 * @param size
 * @param limit
 * @param endianness
 */
expect class MutableDataByteBuffer internal constructor(
    array: ByteArray,
    size: Int,
    limit: Int,
    endianness: Endianness,
) : AbstractMutableDataBuffer, MutableHeapDataBuffer