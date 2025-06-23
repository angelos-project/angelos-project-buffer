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

import org.angproj.io.buf.seg.Segment
import org.angproj.sec.util.TypeSize


public class ByteBuffer internal constructor(
    segment: Segment<*>, view: Boolean = false
): ArrayBuffer<Byte>(segment, view, TypeSize.byteSize) {

    override fun get(index: Int): Byte = segment.getByte(index)

    override fun set(index: Int, value: Byte) {
        segment.setByte(index, value)
    }
}