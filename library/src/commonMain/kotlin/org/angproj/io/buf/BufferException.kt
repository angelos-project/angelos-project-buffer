/**
 * Copyright (c) 2021-2022 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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
 * BufferException is thrown when any error happens inside buffer handling, except when BufferOverflowWarning is thrown.
 *
 * @constructor
 *
 * @param message The error message describing what happened.
 */
public class BufferException(message: String) : RuntimeException(message)