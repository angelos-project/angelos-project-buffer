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

/**
 * Byte-buffer overflow warning is not an exception telling the byte-buffer overflowed,
 * but rather a warning beforehand, to take action and deal with the situation.
 *
 * @constructor Create empty Byte buffer overflow warning
 */
class BufferOverflowWarning : RuntimeException()