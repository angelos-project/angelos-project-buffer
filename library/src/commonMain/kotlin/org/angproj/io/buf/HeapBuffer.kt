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
package org.angproj.io.buf

/**
 * Every buffer that implements this interface must allocate buffer memory inside the heap of the runtime environment.
 *
 * @constructor Allocates implemented buffer on the heap region of memory.
 */
public interface HeapBuffer : Buffer {
    /**
     * Get underlying array of buffer.
     * NOT safe for API external use.
     * May throw UnsupportedOperationException.
     *
     * @return ByteArray representation of memory allocated region.
     */
    override fun getArray(): ByteArray
}