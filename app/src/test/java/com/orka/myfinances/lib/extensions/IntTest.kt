package com.orka.myfinances.lib.extensions

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class IntFormatTest {

    @Test
    fun `Assert all int cases`() {
        assertEquals("0", 0.format())
        assertEquals("1", 1.format())
        assertEquals("5", 5.format())
        assertEquals("10", 10.format())
        assertEquals("999", 999.format())
        assertEquals("1, 000", 1_000.format())
        assertEquals("2, 000", 2_000.format())
        assertEquals("2, 001", 2_001.format())
        assertEquals("2, 011", 2_011.format())
        assertEquals("12, 345", 12_345.format())
        assertEquals("222, 011", 222_011.format())
        assertEquals("1, 234, 567", 1_234_567.format())
    }
}