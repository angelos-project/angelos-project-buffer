/**
 * Copyright (c) 2021-2022 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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
package org.angproj.io.buf.stream

import org.angproj.io.buf.Endianness

/**
 * A common expect class for the mutable stream-buffer that is allocated in native memory on the outside of the heap.
 * Implementation practices may vary depending on source target and platform abilities.
 *
 * @constructor
 *
 * @param size Total size of the buffer.
 * @param limit The initial limitation of how far to operate into the buffer. Must never exceed the size.
 * @param position The initial position in the buffer.
 * @param endianness The initial current endianness of the buffer.
 */
expect class MutableNativeStreamByteBuffer internal constructor(
    size: Int,
    limit: Int,
    position: Int,
    endianness: Endianness,
) : AbstractMutableStreamBuffer, MutableNativeStreamBuffer