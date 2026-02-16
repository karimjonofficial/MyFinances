package com.orka.myfinances.ui.screens.debt.viewmodel

import com.orka.myfinances.data.models.Debt
import com.orka.myfinances.ui.screens.debt.components.DebtCardModel

fun Debt.toModel(): DebtCardModel {
    return DebtCardModel(
        name = "${client.firstName} ${client.lastName}",
        description = description,
        price = "$$price",
        dateTime = "$dateTime"
    )
}

fun Debt.toUiModel(): DebtUiModel {
    return DebtUiModel(
        model = this.toModel(),
        debt = this
    )
}