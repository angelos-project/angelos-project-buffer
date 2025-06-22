package org.angproj.io.buf.util

internal fun <E: Any> E.unsupported(msg: String = "Native memory management is not available."): Nothing {
    throw UnsupportedOperationException(msg)
}