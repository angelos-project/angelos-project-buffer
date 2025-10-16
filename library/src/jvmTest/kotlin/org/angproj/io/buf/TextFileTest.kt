package org.angproj.io.buf

import org.angproj.io.buf.txt.toTextBuffer
import java.nio.file.FileSystems
import java.nio.file.Files
import kotlin.test.Test
import kotlin.test.assertTrue

class TextFileTest {

    @Test
    fun testLoadLines() {
        val filePath = "src/jvmTest/resources/Josephus_book_XI.txt"
        val josephus = BufMgr.textFile(filePath)
        val lines = josephus.readLines()

        Files.readString(FileSystems.getDefault().getPath(filePath)).split("\n").forEachIndexed { index, string ->
            assertTrue { string.toText().contentEquals(lines.get(index)) }
        }
    }
}