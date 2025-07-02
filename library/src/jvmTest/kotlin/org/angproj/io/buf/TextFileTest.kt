package org.angproj.io.buf

import org.angproj.utf.octets
import kotlin.test.Test

class TextFileTest {

    @Test
    fun testLoadLines() {
        val josephus = BufMgr.textFile("src/jvmTest/resources/Josephus_book_XI.txt")
        val txt = josephus.asText()
        josephus.readLines().forEach {
            var position = it.start
            var chars = mutableListOf<Char>()
            do {
                val cp = txt.retrieveGlyph(position)
                position += cp.octets()
               chars.add(cp.value.toChar())
            } while (position < it.endInclusive)
            print(chars.toCharArray())
        }
    }
}