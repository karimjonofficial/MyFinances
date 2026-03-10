package com.orka.myfinances.ui.screens.debt.list

import com.orka.myfinances.data.models.Debt
import com.orka.myfinances.lib.format.FormatDate
import com.orka.myfinances.lib.format.FormatDateTime
import com.orka.myfinances.lib.format.FormatPrice

fun Debt.map(
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

fun Debt.toUiModel(
    formatPrice: FormatPrice,
    formatDateTime: FormatDateTime
): DebtUiModel {
    return DebtUiModel(
        model = map(formatPrice, formatDateTime),
        id = id
    )
}

fun List<Debt>.map(
    formatPrice: FormatPrice,
    formatDate: FormatDate,
    formatDateTime: FormatDateTime
): Map<String, List<DebtUiModel>> {
    return groupBy { formatDate.formatDate(it.dateTime) }
        .mapValues { entry ->
            entry.value.map {
                it.toUiModel(formatPrice, formatDateTime)
            }
        }
}
