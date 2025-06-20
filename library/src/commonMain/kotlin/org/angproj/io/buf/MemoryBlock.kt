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
package org.angproj.io.buf

import org.angproj.aux.com.Limitable

/**
 * Represents a memory block that can be used to manage memory in a structured way.
 * This interface provides methods to access and manipulate memory blocks, including
 * creating sub-blocks and checking if the block is null.
 *
 * @param E The type of the memory block.
 */
public interface MemoryBlock<E: MemoryBlock<E>>: Limitable {

    public fun TypePointer<E>.toLong(): Long = this.ptr

    /**
     * The parent memory block that this block belongs to.
     * This is used to manage the memory hierarchy and ensure proper memory management.
     */
    public val parentBlock: MemoryBlock<*>

    /**
     * Returns the pointer to the memory block.
     *
     * @return The pointer to the memory block.
     */
    public fun getPointer(): TypePointer<E>

    /**
     * Creates a sub-block of this memory block with the specified offset and size.
     * This method allows you to create a new memory block that is a portion of the original block,
     * starting at the specified offset and with the specified size.
     *
     * @param offset The offset from the start of the block where the sub-block begins.
     * @param size The size of the sub-block to create.
     * @param build A lambda function that takes the offset, size, and a TypePointer to the sub-block,
     * and returns the created sub-block.
     * @return The created sub-block of type S.
     * */
    public fun<S: MemoryBlock<S>> subBlock(offset: Int, size: Int, build: (Int, Int, TypePointer<S>) -> S): S {
        require(offset >= 0 && offset + size <= this.limit) { "Invalid offset or size for subBlock" }
        return build(offset, size, TypePointer(getPointer().ptr + offset.toLong()))
    }

    /**
     * Checks if this memory block is null.
     * A null block is a special case where the block does not contain any data.
     *
     * @return true if this block is null, false otherwise.
     */
    public fun isNull(): Boolean = this === nullBlock

    public companion object {
        /**
         * A null block is a special case where the block does not contain any data.
         * It is used to represent an empty or uninitialized memory block.
         */
        public val nullBlock: MemoryBlock<*> by lazy { createNullBlock() }
    }
}

private fun MemoryBlock.Companion.createNullBlock(): MemoryBlock<*> {
    return object : MemoryBlock<Nothing> {
        private val nullPointer: TypePointer<Nothing> = TypePointer(0L)
        override val parentBlock: MemoryBlock<*> get() = this
        override val size: Int get() = 0
        override val limit: Int get() = 0

        override fun getPointer(): TypePointer<Nothing> = nullPointer

        override fun limitAt(newLimit: Int) {
            throw UnsupportedOperationException("Cannot set limit on null block")
        }
    }
}