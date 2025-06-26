/**
 * Copyright (c) 2022-2025 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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

import kotlin.jvm.JvmInline


/**
 * TypePointer2 holds a native memory raw pointer while adding type assertion.
 *
 * This is a specialized version of TypePointer for MemoryBlock types.
 *
 * DON'T MESS WITH RAW POINTERS!
 */
@JvmInline
public value class TypePointer<E: MemoryBlock<E>>(internal val ptr: Long) {

    /**
     * Converts this TypePointer to a TypePointer of Nothing, which is a type that can be used
     * to represent a pointer to any type.
     *
     * @return A TypePointer of Nothing pointing to the same memory address.
     */
    public fun toPointer(): TypePointer<Nothing> = TypePointer(ptr)

    override fun toString(): String = "${this::class.simpleName}(ptr=0x${ptr.toString(16)})"
}