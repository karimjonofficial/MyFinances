package com.orka.myfinances.ui.models.ui

import com.orka.myfinances.data.models.printer.PrinterModel
import com.orka.myfinances.lib.ui.models.SelectionItemModel

data class PrinterUiModel(
    override val title: String,
    override val description: String,
    override val leadingIconRes: Int? = null,
    val model: PrinterModel
) : SelectionItemModel