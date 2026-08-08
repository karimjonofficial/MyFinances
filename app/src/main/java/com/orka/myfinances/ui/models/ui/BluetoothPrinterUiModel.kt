package com.orka.myfinances.ui.models.ui

import com.orka.myfinances.lib.ui.models.SelectionItemModel
import com.orka.myfinances.printer.PrinterModel

data class BluetoothPrinterUiModel(
    override val title: String,
    override val description: String,
    override val leadingIconRes: Int? = null,
    val model: PrinterModel
) : SelectionItemModel//TODO it is introducing dependency to Res