package com.orka.myfinances.ui.screens.debt.viewmodel

import com.orka.myfinances.data.models.Debt
import com.orka.myfinances.lib.data.now
import com.orka.myfinances.lib.format.FormatDate
import com.orka.myfinances.lib.format.FormatDateTime
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.ui.screens.clients.viewmodel.toModel
import com.orka.myfinances.ui.screens.debt.components.DebtCardModel

fun Debt.toModel(
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
        model = this.toModel(formatPrice, formatDateTime),
        debt = this
    )
}

fun List<Debt>.toMap(formatPrice: FormatPrice, formatDate: FormatDate, formatDateTime: FormatDateTime): Map<String, List<DebtUiModel>> {
    return groupBy { formatDate.formatDate(it.dateTime) }
        .mapValues { entry ->
            entry.value.map { it.toUiModel(formatPrice, formatDateTime) }
        }
}

fun Debt.toScreenModel(
    formatPrice: FormatPrice,
    formatDate: FormatDate
): DebtScreenModel {
    return DebtScreenModel(
        price = formatPrice.formatPrice(price.toDouble()),
        startDate = formatDate.formatDate(dateTime),
        endDateTime = if (endDateTime != null) formatDate.formatDate(endDateTime) else null,
        notified = notified,
        client = client.toModel(),
        isOverdue = endDateTime != null && endDateTime < now(),
        debt = this,
        description = description
    )
}