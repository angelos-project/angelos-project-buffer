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
 * Native buffer that is created in the native memory.
 *
 * @constructor Create empty Native buffer
 */
interface NativeBuffer: Buffer {

    /**
     * Memory allocated externally outside the buffer.
     */
    val allocated: Boolean

    /**
     * Get native pointer to underlying ByteArray
     *
     * @return native pointer
     */
    fun getPointer(): Long

    /**
     * Dispose manually of externally allocated memory.
     */
    fun dispose()
}