/**
 * Copyright (c) 2025 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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

import org.angproj.io.buf.Platform.VARIANT
import org.angproj.io.buf.Platform.BITNESS
import org.angproj.io.buf.Platform.CPU
import org.angproj.io.buf.Platform.ENDIAN
import org.angproj.io.buf.Platform.LIB_OS
import org.angproj.io.buf.Platform.GUI_OS


public actual fun Platform.currentVariant(): VARIANT = VARIANT.WASI

public actual fun Platform.currentBitness(): BITNESS = when {
    else -> BITNESS.SIZE_32BIT // De facto
}

/**
 * Little-endian is standard
 * https://webassembly.org/docs/portability/
 * */
public actual fun Platform.currentEndian(): ENDIAN {
    return when {
        else -> ENDIAN.LITTLE_ENDIAN // De facto
    }
}

public actual fun Platform.currentCpu(): CPU = when {
    else -> CPU.UNKNOWN
}

public actual fun Platform.currentLibOs(): LIB_OS {
    return when {
        else -> LIB_OS.UNKNOWN
    }
}

public actual fun Platform.currentGuiOs(): GUI_OS {
    return when {
        else -> GUI_OS.UNKNOWN
    }
}
