package com.orka.myfinances.printer

import com.orka.myfinances.data.models.printer.PrinterModel

sealed interface PrinterStatus {
    object Disconnected : PrinterStatus
    object Connecting : PrinterStatus
    data class Connected(val printer: PrinterModel) : PrinterStatus
    data class Error(val message: String) : PrinterStatus
}