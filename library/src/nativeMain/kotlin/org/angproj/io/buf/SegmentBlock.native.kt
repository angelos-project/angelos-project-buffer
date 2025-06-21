/**
 * Copyright (c) 2025 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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
import kotlinx.cinterop.IntVar
import kotlinx.cinterop.LongVar
import kotlinx.cinterop.ShortVar
import kotlinx.cinterop.pointed
import kotlinx.cinterop.toCPointer
import kotlinx.cinterop.value

@OptIn(ExperimentalForeignApi::class)
internal actual inline fun <reified R : Any> NativeAccess.getByteNative(index: Long): Byte {
    return index.toCPointer<ByteVar>()!!.pointed.value
}

@OptIn(ExperimentalForeignApi::class)
internal actual inline fun <reified R : Any> NativeAccess.getShortNative(index: Long): Short {
    return index.toCPointer<ShortVar>()!!.pointed.value
}

@OptIn(ExperimentalForeignApi::class)
internal actual inline fun <reified R : Any> NativeAccess.getIntNative(index: Long): Int {
    return index.toCPointer<IntVar>()!!.pointed.value
}

@OptIn(ExperimentalForeignApi::class)
internal actual inline fun <reified R : Any> NativeAccess.getLongNative(index: Long): Long {
    return index.toCPointer<LongVar>()!!.pointed.value
}

@OptIn(ExperimentalForeignApi::class)
internal actual inline fun <reified R : Any> NativeAccess.setByteNative(
    index: Long,
    value: Byte
) {
    index.toCPointer<ByteVar>()!!.pointed.value = value
}

@OptIn(ExperimentalForeignApi::class)
internal actual inline fun <reified R : Any> NativeAccess.setShortNative(
    index: Long,
    value: Short
) {
    index.toCPointer<ShortVar>()!!.pointed.value = value
}

@OptIn(ExperimentalForeignApi::class)
internal actual inline fun <reified R : Any> NativeAccess.setIntNative(
    index: Long,
    value: Int
) {
    index.toCPointer<IntVar>()!!.pointed.value = value
}

@OptIn(ExperimentalForeignApi::class)
internal actual inline fun <reified R : Any> NativeAccess.setLongNative(
    index: Long,
    value: Long
) {
    index.toCPointer<LongVar>()!!.pointed.value = value
}