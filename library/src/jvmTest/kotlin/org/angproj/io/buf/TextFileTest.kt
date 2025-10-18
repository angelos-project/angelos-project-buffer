package org.angproj.io.buf

import java.nio.file.FileSystems
import java.nio.file.Files
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TextFileTest {

    @Test
    fun testLoadLines() {
        val filePath = "src/jvmTest/resources/Josephus_book_XI.txt"
        val josephus = BufMgr.textFile(filePath)
        val lines = josephus.iterator()

        Files.readString(FileSystems.getDefault().getPath(filePath)).split("\n").forEach { string ->
            assertTrue { string.toText().contentEquals(lines.next()) }
        }

        assertFalse { lines.hasNext() }
    }
}