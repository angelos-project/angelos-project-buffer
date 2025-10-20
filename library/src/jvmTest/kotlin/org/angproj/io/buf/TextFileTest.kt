/**
 * Copyright (c) 2025 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
 *
 * This software is available under the terms of the MIT license. Parts are licensed
 * under different terms if stated. The legal terms are attached to the LICENSE file
 * and are made available on:
 *
 *      https://opensource.org/licenses/MIT
 *
 * SPDX-License-Identifier: MIT
 *
 * Contributors:
 *      Kristoffer Paulsson - initial implementation
 */
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