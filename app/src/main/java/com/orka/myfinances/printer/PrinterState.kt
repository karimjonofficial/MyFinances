package com.orka.myfinances.printer

sealed interface PrinterState {
    data object Connected : PrinterState
    data object Disconnected : PrinterState
}