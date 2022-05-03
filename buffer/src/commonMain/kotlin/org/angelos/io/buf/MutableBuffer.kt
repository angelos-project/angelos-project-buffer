/**
 * Copyright (c) 2022 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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
package org.angelos.io.buf

/**
 * Mutable buffer interface inheriting from Buffer.
 *
 * @constructor Create empty mutable buffer
 */
interface MutableBuffer : Buffer, Settable {

    /**
     * save one byte to underlying memory.
     *
     * @param index index in memory
     * @param value byte to save
     */
    fun saveByte(index: Int, value: Byte)

    /**
     * save long to underlying memory.
     *
     * @param index index in memory
     * @param value long to save
     */
    fun saveLong(index: Int, value: Long)
}