package org.angproj.io.buf

import org.angproj.io.buf.txt.readLines
import kotlin.test.Test

class TextFileTest {

    @Test
    fun testLoadLines() {
        val josephus = BufMgr.textFile("src/jvmTest/resources/Josephus_book_XI.txt")
        josephus.readLines().forEach {
            println(it.toGlyphBuffer().trim().toString())
        }
    }
}