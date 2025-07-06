package org.angproj.io.buf.txt

import org.angproj.io.buf.toText
import kotlin.collections.plusAssign
import kotlin.test.Test

class TextListTest {

    @Test
    fun allCombosTest() {
        val result = textListOf()
        result += "Hello, ".toText()
        result += "world!".toText()
        println(result.toTextBuffer().asText().toString())
    }

    @Test
    fun allCombos2Test() {
        val result = textListOf("Hello, ".toText(), "world!".toText())
        println(result.toTextBuffer().asText().toString())
    }
}