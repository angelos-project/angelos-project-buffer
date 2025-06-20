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

import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.free
import kotlinx.cinterop.nativeHeap
import kotlinx.cinterop.toCPointer
import kotlinx.cinterop.toLong
import org.angproj.io.buf.util.DataSize

@OptIn(ExperimentalForeignApi::class)
internal actual fun NativeMemoryManager.allocateRootBlock(size: DataSize): RootBlock {
    return RootBlock(nativeHeap.allocArray<ByteVar>(size.size).toLong(), size)
}

@OptIn(ExperimentalForeignApi::class)
internal actual fun NativeMemoryManager.releaseRootBlock(block: RootBlock) {
    nativeHeap.free(block.getPointer().ptr.toCPointer<ByteVar>()!!)
}