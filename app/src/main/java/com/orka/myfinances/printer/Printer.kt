package com.orka.myfinances.printer

import com.orka.myfinances.data.dtos.sale.SaleDto
import com.orka.myfinances.data.models.printer.PrinterModel

interface Printer {
    fun disconnect()
    fun printSaleReceipt(sale: SaleDto)
    fun connect(model: PrinterModel)
}