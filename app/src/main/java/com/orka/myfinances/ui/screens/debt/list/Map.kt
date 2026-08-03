package com.orka.myfinances.ui.screens.debt.list

import com.orka.myfinances.data.models.Debt
import com.orka.myfinances.ui.models.card.DebtCardModel
import com.orka.myfinances.ui.models.ui.DebtUiModel

fun Debt.toUiModel(): DebtUiModel {
    return DebtUiModel(
        model = toCardModel(),
        id = id
    )
}

fun Debt.toCardModel(): DebtCardModel {
    return DebtCardModel(
        name = "${client.firstName} ${client.lastName ?: ""}",
        description = description,
        price = price,
        dateTime = dateTime
    )
}
