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
 * Represents an automatic memory resource that combines the capabilities of a [View] and [Closeable].
 *
 * Implementations of this interface provide a view over a memory region and support explicit resource management
 * via the [close] method. The [Auto] interface is intended for use with memory buffers or similar resources
 * that require deterministic cleanup.
 *
 * Typical usage is to use the [useWith] extension function to ensure the resource is properly closed after use.
 *
 * @see View for view-related operations.
 * @see Closeable for resource management.
 */
public interface Auto : View, Closeable

/**
 * Executes the given [block] function on this [Auto] resource and closes it if it represents a memory resource.
 *
 * This function ensures that the [Auto] resource is properly closed after the [block] is executed,
 * but only if the resource is not a view (`!isView()`) and is a memory resource (`isMem()`).
 * It is intended to be used for safe and deterministic cleanup of memory resources.
 *
 * Example usage:
 * ```kotlin
 *     myAutoResource.useWith { resource ->
 *         // Work with the resource
 *     }
 *     // Resource is closed if appropriate
 * ```
 * @param block The function to execute with this [Auto] resource.
 * @return The result of [block] function.
 * @see Auto
 * @see Closeable
 */
public inline fun<reified T: Auto, R> T.useWith(block: (T) -> R
): R {
   return try {
        block(this)
    } finally {
        if(!isView() && isMem()) close()
    }
}