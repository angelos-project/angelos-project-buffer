package org.angproj.io.buf

import org.angproj.io.buf.util.DataSize
import kotlin.test.assertFailsWith
import kotlin.test.Test

class CopyIntoTest {
    @Test
    fun testIllegalArgument(): Unit {
        BufMgr.withRam(DataSize._128B) { mem1 ->
            BufMgr.withRam(DataSize._64B) { mem2 ->
                // Validates that negative length fails
                assertFailsWith<IllegalArgumentException> ("Negative length is invalid.") {
                    mem1.copyInto(mem2, 0, 0, -1)
                }

                // Validates that negative start index fails
                assertFailsWith<IllegalArgumentException> ("Negative start index is invalid.") {
                    mem1.copyInto(mem2, 0, -1, 0)
                }

                // Validates that negative destination offset fails
                assertFailsWith<IllegalArgumentException> ("Negative destination offset is invalid.") {
                    mem1.copyInto(mem2, -1, 0, 0)
                }

                // Validates that end index escape fails
                assertFailsWith<IllegalArgumentException> ("End index breach is invalid.") {
                    mem2.copyInto(mem1, 0, 0 ,65)
                }

                // Validates that destination end escape fails
                assertFailsWith<IllegalArgumentException> ("Destination offset end breach is invalid.") {
                    mem2.copyInto(mem1, 64, 0 ,65)
                }

                // Validates that destination end 2 escape fails
                assertFailsWith<IllegalArgumentException> ("Destination offset end 2 breach is invalid.") {
                    mem2.copyInto(mem1, 65, 0 ,64)
                }
            }
        }
    }
}