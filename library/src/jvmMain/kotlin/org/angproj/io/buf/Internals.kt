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
package org.angproj.io.buf

import sun.misc.Unsafe
import java.lang.reflect.Field

internal actual class Internals {
    actual companion object {
        internal val unsafe: Unsafe
        internal val byteArrayOffset: Long

        init {
            val f: Field = Unsafe::class.java.getDeclaredField("theUnsafe")
            f.isAccessible = true
            unsafe = f.get(null) as Unsafe
            byteArrayOffset = unsafe.arrayBaseOffset(ByteArray::class.java).toLong()

            System.loadLibrary("jni-buffer") // Load underlying library via JNI.
        }

        @JvmStatic
        private external fun endian(): Int

        @JvmStatic
        private external fun speedmemcpy(dest: Long, src: Long, n: Int)

        @JvmStatic
        private external fun speedbzero(s: Long, n: Int)

        actual fun getEndian(): Int = endian()

        actual fun copyInto(
            destination: MutableBuffer,
            destinationOffset: Int,
            source: Buffer,
            startIndex: Int,
            endIndex: Int,
        ) {
            Buffer.copyIntoContract(destination, destinationOffset, source, startIndex, endIndex)

            val length = endIndex - startIndex
            val l = length.floorDiv(Buffer.LONG_SIZE) * Buffer.LONG_SIZE

            when (destination) {
                is NativeBuffer -> when (source) {
                    is NativeBuffer -> speedmemcpy(
                        destination.getPointer() + destinationOffset,
                        source.getPointer() + startIndex,
                        length
                    )

                    is HeapBuffer -> {
                        val dest = destination.getPointer()
                        val src = source.getArray()
                        for (index in 0 until l step Buffer.LONG_SIZE) {
                            unsafe.putLong(dest + index, unsafe.getLong(src, byteArrayOffset + index))
                        }
                        for (index in l until length) {
                            unsafe.putByte(dest + index, src[index])
                        }
                    }
                }

                is HeapBuffer -> when (source) {
                    is NativeBuffer -> {
                        val dest = destination.getArray()
                        val src = source.getPointer()
                        for (index in 0 until l step Buffer.LONG_SIZE) {
                            unsafe.putLong(dest, byteArrayOffset + index, unsafe.getLong(src + index))
                        }
                        for (index in l until length) {
                            dest[index] = unsafe.getByte(src + index)
                        }
                    }

                    is HeapBuffer -> {
                        val dest = destination.getArray()
                        val src = source.getArray()
                        for (index in 0 until l step Buffer.LONG_SIZE) {
                            unsafe.putLong(dest, byteArrayOffset + index, unsafe.getLong(src, byteArrayOffset + index))
                        }
                        for (index in l until length) {
                            dest[index] = src[index]
                        }
                    }
                }
            }
        }

        actual fun reset(destination: MutableBuffer) {
            val l = destination.size.floorDiv(Buffer.LONG_SIZE) * Buffer.LONG_SIZE

            when (destination) {
                is NativeBuffer -> speedbzero(
                    destination.getPointer(),
                    destination.size
                )

                is HeapBuffer -> {
                    val dest = destination.getArray()
                    for (index in 0 until l step Buffer.LONG_SIZE) {
                        unsafe.putLong(dest, byteArrayOffset + index, 0)
                    }
                    for (index in l until destination.size) {
                        dest[index] = 0
                    }
                }
            }
        }
    }
}