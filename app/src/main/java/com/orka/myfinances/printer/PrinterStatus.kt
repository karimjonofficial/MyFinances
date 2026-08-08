package com.orka.myfinances.printer

sealed interface PrinterStatus {
    object Disconnected : PrinterStatus
    object Connecting : PrinterStatus
    data class Connected(val printer: PrinterModel) : PrinterStatus
    data class Error(val message: String) : PrinterStatus
}