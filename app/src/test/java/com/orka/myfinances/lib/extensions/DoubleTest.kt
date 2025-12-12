package com.orka.myfinances.lib.extensions

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class DoubleFormatTest {

    @Test
    fun `Assert all`() {
        assertEquals("1", 1.0.format())
        assertEquals("2", 2.0.format())
        assertEquals("1.1", 1.1.format())
        assertEquals("1, 000", 1_000.0.format())
        assertEquals("2, 000", 2_000.0.format())
        assertEquals("2, 001", 2_001.0.format())
        assertEquals("2, 011", 2_011.0.format())
        assertEquals("22, 011", 22_011.0.format())
        assertEquals("222, 011", 222_011.0.format())
        assertEquals("2, 000, 011", 2_000_011.0.format())
        assertEquals("1, 000.1", 1_000.1.format())
        assertEquals("1, 000, 000.1", 1_000_000.1.format())
        //TODO assertEquals("10, 000, 000.1", 10_000_000.1.format())
    }
}