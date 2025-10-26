/**
 * Copyright (c) 2023-2025 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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

import org.angproj.io.buf.Platform

/**
 * Interface for objects that are aware of endian (byte order) settings.
 *
 * Implementations represent entities whose behavior depends on byte order (for
 * example multi-byte integer reads/writes). Implementations should expose the
 * active byte order via [currentEndian], indicate whether it differs from the
 * platform default via [isReversed], and update internal state when [setEndian]
 * is called.
 *
 * @see Platform.ENDIAN
 */
public interface EndianAware {

    /**
     * The current endian setting of the object.
     *
     * Implementations must return the active byte order used for multi-byte
     * read/write operations.
     *
     * @return the current [Platform.ENDIAN] used by this object
     * @see Platform.ENDIAN
     */
    public val currentEndian: Platform.ENDIAN

    /**
     * True if the current endian differs from the platform default.
     *
     * This value is typically `true` when [currentEndian] is not equal to the
     * platform's default endian. Implementations may use this flag to decide
     * whether byte-swapping is required.
     */
    public val isReversed: Boolean

    /**
     * Set the endian setting of the object.
     *
     * Implementations must update any internal state necessary so that subsequent
     * read/write operations use the supplied [endian].
     *
     * @param endian the new [Platform.ENDIAN] to use
     */
    public fun setEndian(endian: Platform.ENDIAN)
}