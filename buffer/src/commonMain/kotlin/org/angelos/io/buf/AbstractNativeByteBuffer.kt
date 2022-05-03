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
package org.angelos.io.buf

/**
 * Abstract native byte buffer.
 *
 * @constructor
 *
 * @param size
 * @param limit
 * @param position
 * @param endianness
 */
abstract class AbstractNativeByteBuffer internal constructor(
    size: Int,
    limit: Int,
    position: Int,
    endianness: Endianness
) : AbstractByteBuffer(size, limit, position, endianness)