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
package org.angproj.io.buf

import org.angproj.aux.io.Segment
import org.angproj.aux.util.UtilityAware


public abstract class SpeedCopy internal constructor(segment: Segment<*>): UtilityAware {
    public abstract val capacity: Int
    public abstract val size: Int
    public abstract val limit: Int
    internal val segment: Segment<*> = segment
}