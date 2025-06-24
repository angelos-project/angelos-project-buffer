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
 * Represents an object or resource with a fixed size.
 *
 * The `Sized` interface should be implemented by classes that expose a constant size,
 * such as buffers, arrays, or memory segments. The [size] property provides the
 * fixed size of the element, which does not change during the lifetime of the object.
 *
 * Implementations should document the unit and meaning of [size] (e.g., bytes, elements).
 */
public interface Sized {
    /**
     * The fixed size of the element.
     *
     * This value remains constant and represents the total size associated with the object.
     */
    public val size: Int
}