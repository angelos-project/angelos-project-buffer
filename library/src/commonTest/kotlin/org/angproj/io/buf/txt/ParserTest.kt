package org.angproj.io.buf.txt

import org.angproj.io.buf.toText
import kotlin.test.Test
import kotlin.test.assertEquals

class ParserTest {

    @Test
    fun testParseInteger() {
        assertEquals(Int.MAX_VALUE, Parser.parseInt(Int.MAX_VALUE.toString().toText()))
        assertEquals(1, Parser.parseInt("1".toText()))
        assertEquals(0, Parser.parseInt("0".toText()))
        assertEquals(-1, Parser.parseInt("-1".toText()))
        assertEquals(Int.MIN_VALUE, Parser.parseInt(Int.MIN_VALUE.toString().toText()))
    }
}