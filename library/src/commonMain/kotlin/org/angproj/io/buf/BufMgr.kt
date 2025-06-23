/**
 * Copyright (c) 2024-2025 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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

import org.angproj.io.buf.mem.Default
import org.angproj.io.buf.mem.SingleMemoryPool
import org.angproj.io.buf.util.DataSize
import org.angproj.io.buf.util.useWith
import org.angproj.sec.util.TypeSize

public object BufMgr {
    public fun byteBuf(size: Int): ByteBuffer = ByteBuffer(Default.allocate(size), false)

    public fun uByteBuf(size: Int): UByteBuffer = UByteBuffer(Default.allocate(size), false)

    public fun shortBuf(size: Int): ShortBuffer = ShortBuffer(Default.allocate(size * TypeSize.shortSize), false)

    public fun uShortBuf(size: Int): UShortBuffer = UShortBuffer(Default.allocate(size * TypeSize.uShortSize), false)

    public fun intBuf(size: Int): IntBuffer = IntBuffer(Default.allocate(size * TypeSize.intSize), false)

    public fun uIntBuf(size: Int): UIntBuffer = UIntBuffer(Default.allocate(size * TypeSize.uIntSize), false)

    public fun longBuf(size: Int): LongBuffer = LongBuffer(Default.allocate(size * TypeSize.longSize), false)

    public fun uLongBuf(size: Int): ULongBuffer = ULongBuffer(Default.allocate(size * TypeSize.uLongSize), false)

    public fun floatBuf(size: Int): FloatBuffer = FloatBuffer(Default.allocate(size * TypeSize.floatSize), false)

    public fun doubleBuf(size: Int): DoubleBuffer = DoubleBuffer(Default.allocate(size * TypeSize.doubleSize), false)

    /**
     * Allocates a new [Binary] with the specified size in bytes.
     * The allocated memory is automatically released when the [Binary] is closed.
     *
     * @param size The size of the binary in bytes.
     * @return A new [Binary] instance with the allocated memory.
     */
    public fun<R> withRam(size: DataSize, block: (Binary) -> R): R = Binary(SingleMemoryPool(size, size).allocate()).useWith(block)
}