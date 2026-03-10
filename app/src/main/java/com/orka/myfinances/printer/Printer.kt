package com.orka.myfinances.printer

import com.orka.myfinances.data.api.sale.SaleApiModel

interface Printer {
    fun print(sale: SaleApiModel)
}