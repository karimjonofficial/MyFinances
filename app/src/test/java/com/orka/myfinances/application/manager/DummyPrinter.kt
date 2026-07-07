package com.orka.myfinances.application.manager

import com.orka.myfinances.data.dtos.sale.SaleDto
import com.orka.myfinances.printer.Printer
import com.orka.myfinances.printer.PrinterStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DummyPrinter : Printer {
    override val status: StateFlow<PrinterStatus> = MutableStateFlow(PrinterStatus.Disconnected)
    override fun connect() {}
    override fun disconnect() {}
    override fun print(sale: SaleDto) {}
}
