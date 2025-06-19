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
 * Every buffer that implements this interface must allocate buffer memory on the outside of the heap
 * of the runtime environment.
 *
 * @constructor Allocates implemented buffer outside the heap region of memory.
 */
interface NativeBuffer : Buffer {
    /**
     * Get pointer to native or heap memory.
     * NOT safe for external API use.
     * Throws UnsupportedOperationException if unavailable.
     *
     * DO NOT PLAY AROUND WITH POINTER VALUES THANK YOU!!
     *
     * @return pointer representation of memory block.
     */
    fun getPointer(): TypePointer<Byte>

    /**
     * Dispose manually of allocated memory. This is the responsibility of any library using native buffers.
     *
     */
    fun dispose()

    /**
     * Extra support for dummy implementations that actually only can use ByteArray due to
     * runtime environment restrictions.
     *
     * @return
     */
    override fun getArray(): ByteArray {
        throw UnsupportedOperationException(
            "Unimplemented necessary implementations to be overridden in cases of emergency.")
    }
}