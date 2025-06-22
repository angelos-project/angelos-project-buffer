/**
 * Copyright (c) 2021-2025 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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

import org.angproj.io.buf.seg.Memory
import org.angproj.io.buf.seg.Segment
import org.angproj.io.buf.util.Auto
import org.angproj.io.buf.util.UtilityAware


/**
 * Abstract buffer from which all buffer implementations must inherit.
 * Implements the basic logic regarding size, limit and endianness for reading and writing space.
 *
 * @constructor
 *
 * @param size Total size of the buffer.
 * @param limit The initial limitation of how far to operate into the buffer. Must never exceed the size.
 * @param endianness The initial current endianness of the buffer.
 */
public abstract class AbstractBuffer protected constructor(
    internal val segment: Segment<*>, protected val view: Boolean = false
) : UtilityAware, Auto, Comparable<Buffer> {

    /**
     * Gives the max bytes capacity of the buffer
     * */
    public abstract val capacity: Int

    /**
     * Gives the max size of the buffers represented item
     * */
    public abstract val size: Int

    /**
     * The current limit of the buffer as defined.
     * */
    public abstract val limit: Int

    override fun isView(): Boolean = view

    override fun isMem(): Boolean = segment is Memory

    override fun close() {
        if(!isView()) segment.dispose()
    }

    public override fun equals(other: Any?): Boolean {
        if(this === other) return true
        if(other == null || this::class != other::class) return false
        other as Buffer
        return compareTo(other) == 0
    }

    public override fun hashCode(): Int = segment.hashCode()

    public override operator fun compareTo(other: Buffer): Int { return hashCode() - other.hashCode() }
}