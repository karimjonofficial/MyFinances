package com.orka.myfinances.printer

import com.orka.myfinances.data.api.sale.models.response.SaleApiModel

interface Printer {
    fun print(sale: SaleApiModel)
}