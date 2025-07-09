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
package org.angproj.io.buf.json

import org.angproj.io.buf.GlyphReadable
import org.angproj.io.buf.Text

public object Json {
    public fun<T> objectFromJson(reader: GlyphReadable, build:  () -> T): T {
        TODO()
    }

    public fun<T> tupleArrayFromJson(reader: GlyphReadable, build:  () -> T): T {
        TODO()
    }

    public fun<T> typeArrayFromJson(reader: GlyphReadable, build:  () -> T): T {
        TODO()
    }

    public fun stringFromJson(reader: GlyphReadable, build:  () -> Text): Text {
        TODO()
    }

    public fun<T> longFromJson(reader: GlyphReadable, build:  () -> T): T {
        TODO()
    }

    public fun<T> doubleFromJson(reader: GlyphReadable, build:  () -> T): T {
        TODO()
    }

    public fun<T> booleanFromJson(reader: GlyphReadable, build:  () -> T): T {
        TODO()
    }
}