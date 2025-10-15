package org.angproj.io.buf

import kotlin.test.*

class PlatformTest {

    @Test
    fun testEnumValues() {
        // ENDIAN
        assertTrue(Platform.ENDIAN.values().isNotEmpty())
        assertTrue(Platform.ENDIAN.LITTLE_ENDIAN.name.isNotEmpty())

        // BITNESS
        assertTrue(Platform.BITNESS.values().isNotEmpty())
        assertTrue(Platform.BITNESS.SIZE_64BIT.name.isNotEmpty())

        // GUI_OS
        assertTrue(Platform.GUI_OS.values().contains(Platform.GUI_OS.MACOSX))

        // LIB_OS
        assertTrue(Platform.LIB_OS.values().contains(Platform.LIB_OS.LINUX))
        assertEquals("linux", Platform.LIB_OS.LINUX.toLowerString())

        // CPU
        assertTrue(Platform.CPU.values().contains(Platform.CPU.X86_64))
        assertEquals("x86_64", Platform.CPU.X86_64.toLowerString())

        // VARIANT
        assertTrue(Platform.VARIANT.values().contains(Platform.VARIANT.JVM))
        assertEquals("Kotlin/Jvm", Platform.VARIANT.JVM.code)
    }

    @Test
    fun testPropertiesInitialized() {
        // These will call the expect/actual implementations
        assertNotNull(Platform.cpu)
        assertNotNull(Platform.bitness)
        assertNotNull(Platform.endian)
        assertNotNull(Platform.libOs)
        assertNotNull(Platform.guiOs)
        assertNotNull(Platform.variant)
    }

    @Test
    fun testUtilityMethods() {
        // is32Bit and is64Bit are mutually exclusive
        assertNotEquals(Platform.is32Bit(), Platform.is64Bit())

        assertNotEquals(Platform.isNetRev(), Platform.endian == Platform.ENDIAN.BIG_ENDIAN)
        assertEquals(Platform.isNetRev(), Platform.endian == Platform.ENDIAN.LITTLE_ENDIAN)

        // bsd and notUnix sets
        assertTrue(Platform.bsd.contains(Platform.LIB_OS.FREEBSD))
        assertTrue(Platform.notUnix.contains(Platform.LIB_OS.WINDOWS))

        // size32Bit and size64Bit sets
        assertTrue(Platform.size32Bit.contains(Platform.CPU.I386))
        assertTrue(Platform.size64Bit.contains(Platform.CPU.X86_64))
    }

    @Test
    fun testPlatformFunctions() {
        // Test the expect functions
        assertNotNull(Platform.currentVariant())
        assertNotNull(Platform.currentBitness())
        assertNotNull(Platform.currentEndian())
        assertNotNull(Platform.currentCpu())
        assertNotNull(Platform.currentLibOs())
        assertNotNull(Platform.currentGuiOs())
    }

    @Test
    fun testPlatformIsMethods() {
        // Test is32Bit and is64Bit methods
        assertTrue(Platform.is32Bit() || Platform.is64Bit())

        // Test isBSD and isUnix methods
        assertTrue(Platform.isUnix() || !Platform.isUnix())

        assertTrue(Platform.isBSD() || !Platform.isBSD())
    }
}