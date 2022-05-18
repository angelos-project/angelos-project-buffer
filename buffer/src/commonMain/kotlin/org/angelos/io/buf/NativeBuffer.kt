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
interface NativeBuffer : Buffer {
    /**
     * Get pointer to native or heap memory.
     * Throws UnsupportedOperationException if unavailable.
     *
     * @return pointer to first element
     */
    fun getPointer(): TypePointer<Byte>

    /**
     * Pins the underlying memory if necessary and executes the lambda.
     *
     * @param native lambda to be executed in pinned mode
     * @receiver
     */
    fun usePinned(native: (ptr: TypePointer<Byte>) -> Unit)

    /**
     * Dispose manually of allocated memory.
     *
     */
    fun dispose()
}