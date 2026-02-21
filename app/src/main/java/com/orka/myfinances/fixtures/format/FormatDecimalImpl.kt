package com.orka.myfinances.fixtures.format

import com.orka.myfinances.lib.format.FormatDecimal

class FormatDecimalImpl : FormatDecimal {
    override fun formatDecimal(value: Double): String {
        return "1000.00"
    }
}