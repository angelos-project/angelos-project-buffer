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
 * Represents a resource or object that can be closed to release underlying resources.
 *
 * The `Closeable` interface should be implemented by classes that manage resources
 * such as files, sockets, or memory buffers that require explicit release when no longer needed.
 * Calling [close] ensures that any system resources associated with the object are properly freed.
 *
 * Implementations should specify whether [close] is idempotent (safe to call multiple times)
 * and document any exceptions that may be thrown.
 */
public interface Closeable {
    // FIXME make unittest
    // FIXME make Mock

    /**
     * Closes this resource, releasing any underlying resources.
     *
     * This method should be called when the resource is no longer needed.
     * Implementations should specify the behavior if called multiple times.
     *
     * @throws Exception if an error occurs while closing the resource.
     */
    public fun close()
}