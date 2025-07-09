package org.angproj.io.buf.json


import org.angproj.io.buf.BufMgr
import org.angproj.io.buf.textFile
import org.angproj.io.buf.toGlyphString
import org.angproj.io.buf.toText
import org.angproj.io.buf.txt.Parser as TextParser
import org.angproj.sec.SecureRandom
import kotlin.test.Test
import kotlin.test.assertEquals

class JsonTest {

    @Test
    fun generateDoubles() {
        repeat(128) { _ -> println(Double.fromBits(SecureRandom.readLong())) }
    }

    @Test
    fun generateIntegers() {
        repeat(128) { _ -> println(SecureRandom.readLong()) }
    }

    @Test
    fun testNumberParsing() {
       /* val numbers = BufMgr.textFile("src/jvmTest/resources/integers.txt")
        numbers.readLines().forEachIndexed { index, value ->
            //it.toGlyphString().trim().toString().toDouble()
            val lexer = Lexer(value, index + 1, 1)
            val data = JsonParser.INTEGER.parse(lexer, 0)
            println(value.substr(0, data).toGlyphString().integerOf(0..data, 10))
            //println("$data, ${value.limit-1}: ${(value.limit - 1) == data}")
        }*/

    }
}