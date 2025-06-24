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

import org.angproj.io.buf.util.DataSize
import sun.misc.Unsafe
import java.lang.reflect.Field


@Suppress("DiscouragedPrivateApi")
internal object AnUnsafe{
    internal val unsafe: Unsafe

    init {
        val f: Field = Unsafe::class.java.getDeclaredField("theUnsafe")
        f.isAccessible = true
        unsafe = f.get(null) as Unsafe
    }
}


internal actual fun NativeMemoryManager.allocateRootBlock(size: DataSize): RootBlock {
    return RootBlock(AnUnsafe.unsafe.allocateMemory(size.toLong()), size)
}

internal actual fun NativeMemoryManager.releaseRootBlock(block: RootBlock) {
    AnUnsafe.unsafe.freeMemory(block.getPointer().ptr)
}