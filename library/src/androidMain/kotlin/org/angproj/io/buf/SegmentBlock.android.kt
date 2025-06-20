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

internal actual inline fun <reified R : Any> NativeAccess.getByteNative(index: Long): Byte {
    return AnUnsafe.unsafe.getByte(index)
}

internal actual inline fun <reified R : Any> NativeAccess.getShortNative(index: Long): Short {
    return AnUnsafe.unsafe.getShort(index)
}

internal actual inline fun <reified R : Any> NativeAccess.getIntNative(index: Long): Int {
    return AnUnsafe.unsafe.getInt(index)
}

internal actual inline fun <reified R : Any> NativeAccess.getLongNative(index: Long): Long {
    return AnUnsafe.unsafe.getLong(index)
}

internal actual inline fun <reified R : Any> NativeAccess.setByteNative(
    index: Long,
    value: Byte
) {
    AnUnsafe.unsafe.putByte(index, value)
}

internal actual inline fun <reified R : Any> NativeAccess.setShortNative(
    index: Long,
    value: Short
) {
    AnUnsafe.unsafe.putShort(index, value)
}

internal actual inline fun <reified R : Any> NativeAccess.setIntNative(
    index: Long,
    value: Int
) {
    AnUnsafe.unsafe.putInt(index, value)
}

internal actual inline fun <reified R : Any> NativeAccess.setLongNative(
    index: Long,
    value: Long
) {
    AnUnsafe.unsafe.putLong(index, value)
}