package org.angproj.io.buf.util

internal fun <E: Any> E.unsupported(): Nothing {
    throw UnsupportedOperationException("Native memory management is not available.")
}