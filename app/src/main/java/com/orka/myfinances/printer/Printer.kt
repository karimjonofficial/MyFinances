package com.orka.myfinances.printer

import com.orka.myfinances.data.models.sale.Sale

interface Printer {
    fun print(sale: Sale)
}