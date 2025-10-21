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


public interface ArrayBuffer<E>: Buffer, Iterable<E>{

    override fun iterator(): Iterator<E>

    public val indices: IntRange
    public val lastIndex: Int

    public override val capacity: Int

    public override val size: Int

    public override val limit: Int

    public operator fun get(index: Int): E
    public operator fun set(index: Int, value: E)

    public fun isNull(): Boolean

    override fun isView(): Boolean

    override fun isMem(): Boolean

    override fun close(): Unit

    public override operator fun compareTo(other: Buffer): Int
}