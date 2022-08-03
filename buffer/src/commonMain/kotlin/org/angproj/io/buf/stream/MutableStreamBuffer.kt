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
package org.angproj.io.buf.stream

import org.angproj.io.buf.MutableBuffer
import org.angproj.io.buf.Readable
import org.angproj.io.buf.Writable

/**
 * Mutable stream-buffer represents a mutable stream-buffer. Use this interface as
 * types on method parameters in order to allow third party implementations of buffers.
 *
 * @constructor Create implementation of the MutableStreamBuffer interface.
 */
interface MutableStreamBuffer : StreamBuffer, MutableBuffer, Readable, Writable