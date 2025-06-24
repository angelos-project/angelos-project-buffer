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
 * Represents an object or resource that can be explicitly cleaned up or disposed of.
 *
 * The `Cleanable` interface should be implemented by classes that manage resources
 * such as memory, handles, or other system resources that require explicit release
 * when no longer needed. Calling [dispose] ensures that any resources associated
 * with the object are properly freed.
 *
 * Implementations should specify whether [dispose] is idempotent (safe to call multiple times)
 * and document any exceptions that may be thrown.
 */
public interface Cleanable {

    /**
     * Disposes of the resources held by this object.
     *
     * This method should be called when the object is no longer needed to free up resources.
     * Implementations should specify the behavior if called multiple times.
     *
     * @throws Exception if an error occurs while disposing of the resource.
     */
    public fun dispose()
}