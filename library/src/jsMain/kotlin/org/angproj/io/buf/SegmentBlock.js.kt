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

internal actual inline fun <reified R : Any> NativeAccess.getByteNative(index: Int): Byte {
    unsupported()
}

internal actual inline fun <reified R : Any> NativeAccess.getShortNative(index: Int): Short {
    unsupported()
}

internal actual inline fun <reified R : Any> NativeAccess.getIntNative(index: Int): Int {
    unsupported()
}

internal actual inline fun <reified R : Any> NativeAccess.getLongNative(index: Int): Long {
    unsupported()
}

internal actual inline fun <reified R : Any> NativeAccess.setByteNative(
    index: Int,
    value: Byte
) {
    unsupported()
}

internal actual inline fun <reified R : Any> NativeAccess.setShortNative(
    index: Int,
    value: Short
) {
    unsupported()
}

internal actual inline fun <reified R : Any> NativeAccess.setIntNative(
    index: Int,
    value: Int
) {
    unsupported()
}

internal actual inline fun <reified R : Any> NativeAccess.setLongNative(
    index: Int,
    value: Long
) {
    unsupported()
}