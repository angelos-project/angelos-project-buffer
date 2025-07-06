package org.angproj.io.buf

import org.angproj.io.buf.txt.toTextBuffer
import kotlin.test.Test
import kotlin.test.assertContentEquals

class TextFileTest {

    @Test
    fun testLoadLines() {
        val josephus = BufMgr.textFile("src/jvmTest/resources/Josephus_book_XI.txt")

        assertContentEquals(josephus, josephus.readLines().toTextBuffer())
    }
}