package com.orka.myfinances.printer

import com.orka.myfinances.data.api.sale.models.response.SaleApiModel
import kotlinx.coroutines.flow.StateFlow

interface Printer {
    val status: StateFlow<PrinterStatus>
    fun connect()
    fun disconnect()
    fun print(sale: SaleApiModel)
}