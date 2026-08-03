package com.orka.myfinances.application.viewmodels.debt.list

import com.orka.myfinances.data.dtos.debt.DebtDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.models.card.DebtCardModel
import com.orka.myfinances.ui.models.ui.DebtUiModel

fun DebtDto.toModel(): DebtCardModel {
    return DebtCardModel(
        name = "${client.firstName} ${client.lastName ?: ""}",
        description = description,
        price = price.toInt(),
        dateTime = dateTime
    )
}

fun DebtDto.toUiModel(): DebtUiModel {
    return DebtUiModel(
        model = toModel(),
        id = Id(id)
    )
}
