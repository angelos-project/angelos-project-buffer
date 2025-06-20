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
public interface NativeBuffer : Buffer {


    /**
     * Dispose manually of allocated memory. This is the responsibility of any library using native buffers.
     *
     */
    public fun dispose()

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