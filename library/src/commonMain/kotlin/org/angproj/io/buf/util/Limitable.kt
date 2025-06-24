/**
 * Copyright (c) 2024 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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
 * Represents an element with a configurable usage limit, extending the [Sized] interface.
 *
 * The `Limitable` interface provides a mechanism to restrict the usable portion of an element
 * to a range between 0 and its total [size]. This is commonly used in buffer or memory management
 * scenarios where only a subset of the available space should be accessible at a given time.
 *
 * @property limit The current usage limit, constrained between 0 and [size].
 *
 * @see Sized
 */
public interface Limitable: Sized {
    /**
     * The limited use of an element between 0 and size.
     */
    public val limit: Int

    /**
     * Sets a new usage limit for the element.
     *
     * @param newLimit The new limit value, which should be between 0 and [size].
     */
    public fun limitAt(newLimit: Int)

    /**
     * Resets the usage limit to the full [size] of the element.
     */
    public fun clear() { limitAt(size) }
}