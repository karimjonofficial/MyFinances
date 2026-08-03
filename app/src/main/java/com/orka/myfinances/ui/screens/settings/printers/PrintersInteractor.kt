package com.orka.myfinances.ui.screens.settings.printers

import com.orka.myfinances.ui.models.ui.PrinterUiModel

interface PrintersInteractor {
    fun refresh()
    fun connect(printer: PrinterUiModel)
}