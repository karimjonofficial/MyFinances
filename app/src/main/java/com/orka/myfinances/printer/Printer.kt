package com.orka.myfinances.printer

import com.orka.myfinances.data.dtos.sale.SaleDto

interface Printer {
    fun disconnect()
    fun printSaleReceipt(sale: SaleDto)
    fun connect(model: PrinterModel)
    fun connectToDefault()
}