package com.orka.myfinances.data.dtos.printer

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.printer.PrinterModel

data class PrinterDto(
    val id: Id,
    val model: PrinterModel
)