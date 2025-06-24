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
package org.angproj.io.buf.util

/**
 * Represents a view or wrapper around a shared memory segment.
 *
 * The `View` interface provides information about the nature of the underlying segment:
 * - Whether this instance is a view (i.e., a wrapper that should not close the underlying resource).
 * - Whether the underlying segment is a raw memory resource that may require explicit closing.
 *
 * This abstraction is useful for distinguishing between original memory resources and their views,
 * ensuring correct resource management and preventing accidental closure of shared segments.
 *
 * @see org.angproj.io.buf.util.Auto
 */
public interface View {

    /**
     * Indicates if this instance is a view (wrapper) of a shared segment.
     *
     * If `true`, this object is a view and should not close the underlying segment.
     * If `false`, this object owns the segment and may be responsible for closing it.
     *
     * @return `true` if this is a view; `false` otherwise.
     */
    public fun isView(): Boolean

    /**
     * Indicates if the underlying segment is a raw memory resource.
     *
     * If `true`, the segment represents raw memory access and may require explicit closure
     * if this instance is not a view. If `false`, the segment does not represent raw memory.
     *
     * @return `true` if the segment is raw memory; `false` otherwise.
     */
    public fun isMem(): Boolean
}