package com.orka.myfinances.fixtures.format

import com.orka.myfinances.lib.format.FormatPrice

class FormatPriceImpl : FormatPrice {
    override fun formatPrice(price: Double): String {
        return "1000.00 UZS"
    }
}