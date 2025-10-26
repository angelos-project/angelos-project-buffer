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

import org.angproj.io.buf.util.Auto
import org.angproj.io.buf.util.EndianAware
import org.angproj.io.buf.util.UtilityAware

public interface Buffer : UtilityAware, EndianAware, Auto, Comparable<Buffer> {

    /**
     * Gives the max bytes capacity of the buffer
     * */
    public val capacity: Int

    /**
     * Gives the max size of the buffers represented item
     * */
    public val size: Int

    /**
     * The current limit of the buffer as defined.
     * */
    public val limit: Int

    override fun isView(): Boolean

    override fun isMem(): Boolean

    override fun close(): Unit

    public override operator fun compareTo(other: Buffer): Int

}