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
        private val theArrays = arrayOf(ByteArray(0))

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

        actual fun getEndian(): Int = endian()

        actual fun copyInto(
            destination: TypePointer<Byte>,
            source: TypePointer<Byte>,
            length: Int,
        ) {
            speedmemcpy(destination, source, length)
        }

        /**
         * Native address of first element of an ByteArray.
         * adapted from toAddress() in http://mishadoff.com/blog/java-magic-part-4-sun-dot-misc-dot-unsafe/
         *
         * @param array
         * @return native pointer
         */
        actual fun nativeArrayAddress(array: ByteArray): TypePointer<Byte> {
            theArrays[0] = array
            return when (unsafe.addressSize()) {
                4 -> normalize(
                    unsafe.getInt(
                        array,
                        unsafe.arrayBaseOffset(theArrays.javaClass).toLong()
                    ) + byteArrayOffset.toInt()
                )
                8 -> unsafe.getLong(theArrays, unsafe.arrayBaseOffset(theArrays.javaClass).toLong()) + byteArrayOffset
                else -> error("Invalid address size")
            }
        }

        private fun normalize(value: Int): Long {
            return if (value >= 0) value.toLong() else 0L.inv() ushr 32 and value.toLong()
        }
    }
}