package com.orka.myfinances.ui.screens.debt.viewmodel

import com.orka.myfinances.data.models.Debt
import com.orka.myfinances.lib.format.FormatDate
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.format.FormatTime
import com.orka.myfinances.ui.screens.debt.components.DebtCardModel

fun Debt.toModel(
    priceFormatter: FormatPrice,
    dateFormatter: FormatDate,
    timeFormatter: FormatTime
): DebtCardModel {
    return DebtCardModel(
        name = "${client.firstName} ${client.lastName ?: ""}",
        description = description,
        price = priceFormatter.formatPrice(price.toDouble()),
        dateTime = "${dateFormatter.formatDate(dateTime)} ${timeFormatter.formatTime(dateTime)}"
    )
}

fun Debt.toUiModel(
    priceFormatter: FormatPrice,
    dateFormatter: FormatDate,
    timeFormatter: FormatTime
): DebtUiModel {
    return DebtUiModel(
        model = this.toModel(priceFormatter, dateFormatter, timeFormatter),
        debt = this
    )
}
