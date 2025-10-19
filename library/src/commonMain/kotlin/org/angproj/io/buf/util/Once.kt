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

import kotlin.reflect.KProperty


/**
 * A property delegate that allows a property to be set only once.
 *
 * This delegate is useful for properties that should only be initialized once,
 * such as configuration settings or resources that should not be re-initialized.
 *
 * Usage:
 * ```
 * class Example {
 *     var value: String by Once()
 * }
 * ```
 *
 * @param E The type of the property being delegated.
 */
public class Once<E: Any> {

    private lateinit var handle: E

    /**
     * Returns the value of the property.
     *
     * @param thisRef The reference to the object that holds the property.
     * @param property The property being accessed.
     * @return The value of the property.
     */
    public operator fun getValue(thisRef: Any, property: KProperty<*>): E = handle

    /**
     * Sets the value of the property, but only if it has not been set before.
     *
     * @param thisRef The reference to the object that holds the property.
     * @param property The property being set.
     * @param value The value to set.
     * @throws IllegalStateException if the property has already been initialized.
     */
    public operator fun setValue(thisRef: Any, property: KProperty<*>, value: E) {
        check(!::handle.isInitialized) { "Already initialized" }
        handle = value
    }
}