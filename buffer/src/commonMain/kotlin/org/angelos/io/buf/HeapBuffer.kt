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
 * Heap buffer that is created in the heap memory.
 *
 * @constructor Create empty Heap buffer
 */
interface HeapBuffer {

    /**
     * Exposing the underlying ByteArray of the buffer.
     *
     * @return underlying array
     */
    fun getArray(): ByteArray
}