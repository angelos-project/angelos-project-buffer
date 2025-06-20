package org.angproj.io.buf

import kotlin.experimental.and
import kotlin.experimental.or

public typealias Glyph = Int


// https://www.ietf.org/rfc/rfc2279.txt
// https://en.wikipedia.org/wiki/UTF-8


/**
 *
 */
public fun glyphSize(glyph: Glyph): Int = when (glyph) {
    in 0x00..0x7F -> 1
    in 0x80..0x7FF -> 2
    in 0x800..0xFFFF -> 3
    in 0x1_0000..0x1F_FFFF -> 4
    in 0x20_0000..0x3FF_FFFF -> 5
    in 0x400_0000..0x7FFF_FFFF -> 6
    else -> error("Invalid UTF-8 code point: $glyph")
}


/**
 * Takes a UTF-8 leading octet as a Byte and check what size the multibyte character has.
 * Also works as validation of the first octet in a UTF-8 binary octet sequence.
 */
public fun glyphSize(octet: Byte): Int = when {
    (octet and -0B1000_0000).toInt() == 0 -> 1
    (octet and 0B1110_0000.toByte()).toInt() == 0B1100_0000 -> 2
    (octet and 0B1111_0000.toByte()).toInt() == 0B1110_0000 -> 3
    (octet and 0B1111_1000.toByte()).toInt() == 0B1111_0000 -> 4
    (octet and 0B1111_1100.toByte()).toInt() == 0B1111_1000 -> 5
    (octet and 0B1111_1110.toByte()).toInt() == 0B1111_1100 -> 6
    else -> error("Invalid UTF-8 leading octet: $octet")
}


/**
 * Validates a UTF-8 binary sequence, presuming that the first octet is already known.
 */
public fun glyphIsValid(data: ByteArray, pos: Int, size: Int): Boolean = when (size){
    1 -> true
    2 -> data[pos + 1] and -0B1000000 == (-0B10000000).toByte()
    3 -> data[pos + 1] and -0B1000000 == (-0B10000000).toByte() &&
            data[pos + 2] and -0B1000000 == (-0B10000000).toByte()
    4 -> data[pos + 1] and -0B1000000 == (-0B10000000).toByte() &&
            data[pos + 2] and -0B1000000 == (-0B10000000).toByte() &&
            data[pos + 3] and -0B1000000 == (-0B10000000).toByte()
    5 -> data[pos + 1] and -0B1000000 == (-0B10000000).toByte() &&
            data[pos + 2] and -0B1000000 == (-0B10000000).toByte() &&
            data[pos + 3] and -0B1000000 == (-0B10000000).toByte() &&
            data[pos + 4] and -0B1000000 == (-0B10000000).toByte()
    6 -> data[pos + 1] and -0B1000000 == (-0B10000000).toByte() &&
            data[pos + 2] and -0B1000000 == (-0B10000000).toByte() &&
            data[pos + 3] and -0B1000000 == (-0B10000000).toByte() &&
            data[pos + 4] and -0B1000000 == (-0B10000000).toByte() &&
            data[pos + 5] and -0B1000000 == (-0B10000000).toByte()
    else -> false
}

public fun glyphRead(data: ByteArray, pos: Int, size: Int): Glyph = when (size){
    1 -> 0B01111111 and data[pos].toInt()
    2 -> (0B00011111 and data[pos].toInt() shl 6) and
            (0B00111111 and data[pos+1].toInt())
    3 -> (0B00001111 and data[pos].toInt() shl 12) and
            (0B00111111 and data[pos+1].toInt() shl 6) and
            (0B00111111 and data[pos+2].toInt())
    4 -> (0B00000111 and data[pos].toInt() shl 18) and
            (0B00111111 and data[pos+1].toInt() shl 12) and
            (0B00111111 and data[pos+2].toInt() shl 6) and
            (0B00111111 and data[pos+3].toInt())
    5 -> (0B00000011 and data[pos].toInt() shl 24) and
            (0B00111111 and data[pos+1].toInt() shl 18) and
            (0B00111111 and data[pos+2].toInt() shl 12) and
            (0B00111111 and data[pos+3].toInt() shl 6) and
            (0B00111111 and data[pos+4].toInt())
    6 -> (0B00000001 and data[pos].toInt() shl 30) and
            (0B00111111 and data[pos+1].toInt() shl 24) and
            (0B00111111 and data[pos+2].toInt() shl 18) and
            (0B00111111 and data[pos+3].toInt() shl 12) and
            (0B00111111 and data[pos+4].toInt() shl 6) and
            (0B00111111 and data[pos+5].toInt())
    else -> error("Invalid size of UTF-8 code point: ${data[pos]}")
}

public fun glyphWrite(data: ByteArray, pos: Int, glyph: Glyph, size: Int): Unit = when (size){
    1 -> data[pos] = (0B0000000_00000000_00000000_01111111 or glyph).toByte()
    2 -> {
        data[pos] = (0B0000000_00000000_00000111_11000000 and glyph shr 6).toByte() or -0B01000000
        data[pos+1] = (0B0000000_00000000_00000000_00111111 and glyph).toByte() or -0B10000000
    }
    3 -> {
        data[pos] = (0B0000000_00000000_11110000_00000000 and glyph shr 12).toByte() or -0B00100000
        data[pos+1] = (0B0000000_00000000_00001111_11000000 and glyph shr 6).toByte() or -0B10000000
        data[pos+2] = (0B0000000_00000000_00000000_00111111 and glyph).toByte() or -0B10000000
    }
    4 -> {
        data[pos] = (0B0000000_00011100_00000000_00000000 and glyph shr 18).toByte() or -0B00010000
        data[pos+1] = (0B0000000_00000011_11110000_00000000 and glyph shr 12).toByte() or -0B10000000
        data[pos+2] = (0B0000000_00000000_00001111_11000000 and glyph shr 6).toByte() or -0B10000000
        data[pos+3] = (0B0000000_00000000_00000000_00111111 and glyph).toByte() or -0B10000000
    }
    5 -> {
        data[pos] = (0B0000011_00000000_00000000_00000000 and glyph shr 24).toByte() or -0B00001000
        data[pos+1] = (0B0000000_11111100_00000000_00000000 and glyph shr 18).toByte() or -0B10000000
        data[pos+2] = (0B0000000_00000011_11110000_00000000 and glyph shr 12).toByte() or -0B10000000
        data[pos+3] = (0B0000000_00000000_00001111_11000000 and glyph shr 6).toByte() or -0B10000000
        data[pos+4] = (0B0000000_00000000_00000000_00111111 and glyph).toByte() or -0B10000000
    }
    6 -> {
        data[pos] = (0B1000000_00000000_00000000_00000000 and glyph shr 30).toByte() or -0B00000100
        data[pos+1] = (0B0111111_00000000_00000000_00000000 and glyph shr 24).toByte() or -0B10000000
        data[pos+2] = (0B0000000_11111100_00000000_00000000 and glyph shr 18).toByte() or -0B10000000
        data[pos+3] = (0B0000000_00000011_11110000_00000000 and glyph shr 12).toByte() or -0B10000000
        data[pos+4] = (0B0000000_00000000_00001111_11000000 and glyph shr 6).toByte() or -0B10000000
        data[pos+5] = (0B0000000_00000000_00000000_00111111 and glyph).toByte() or -0B10000000
    }
    else -> error("Invalid size of UTF-8 code point: $glyph")
}


public fun ByteArray.readGlyphAt(pos: Int): Glyph = glyphRead(this, pos, glyphSize(this[pos]))

public fun ByteArray.writeGlyphAt(offset: Int, value: Glyph): Unit = glyphWrite(this, offset, value, glyphSize(value))