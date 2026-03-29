package com.orka.myfinances.ui.screens.debt.list

import com.orka.myfinances.data.models.Debt
import com.orka.myfinances.lib.format.FormatDateTime
import com.orka.myfinances.lib.format.FormatPrice

fun Debt.toUiModel(
    formatPrice: FormatPrice,
    formatDateTime: FormatDateTime
): DebtUiModel {
    return DebtUiModel(
        model = toCardModel(formatPrice, formatDateTime),
        id = id
    )
}

fun Debt.toCardModel(
    formatPrice: FormatPrice,
    formatDateTime: FormatDateTime
): DebtCardModel {
    return DebtCardModel(
        name = "${client.firstName} ${client.lastName ?: ""}",
        description = description,
        price = formatPrice.formatPrice(price.toDouble()),
        dateTime = formatDateTime.formatDateTime(dateTime)
    )
}