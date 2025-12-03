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


public class UByteBuffer internal constructor(
    segment: Segment<*>, view: Boolean, endian: Platform.ENDIAN
): AbstractArrayBuffer<UByte>(segment, view, TypeSize.uByteSize, endian) {

    override fun get(index: Int): UByte = segment.getByte(index).conv2uB()

    override fun set(index: Int, value: UByte) {
        segment.setByte(index, value.conv2B())
    }
}