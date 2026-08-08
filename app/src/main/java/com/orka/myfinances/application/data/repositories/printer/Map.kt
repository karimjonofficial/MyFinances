package com.orka.myfinances.application.data.repositories.printer

import com.orka.myfinances.data.database.entities.PrinterEntity
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.dtos.printer.PrinterDto
import com.orka.myfinances.printer.PrinterModel

fun PrinterEntity.toModel(): PrinterDto {
    return PrinterDto(
        id = Id(id),
        model = PrinterModel(
            name = name,
            address = address
        )
    )
}