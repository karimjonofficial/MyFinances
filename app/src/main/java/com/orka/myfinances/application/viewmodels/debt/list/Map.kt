package com.orka.myfinances.application.viewmodels.debt.list

import com.orka.myfinances.data.api.debt.DebtApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.format.FormatDate
import com.orka.myfinances.lib.format.FormatDateTime
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.ui.screens.debt.list.DebtCardModel
import com.orka.myfinances.ui.screens.debt.list.DebtUiModel

fun DebtApiModel.toModel(
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

fun DebtApiModel.toUiModel(
    formatPrice: FormatPrice,
    formatDateTime: FormatDateTime
): DebtUiModel {
    return DebtUiModel(
        model = this.toModel(formatPrice, formatDateTime),
        id = Id(id)
    )
}

fun List<DebtApiModel>.toMap(
    formatPrice: FormatPrice,
    formatDate: FormatDate,
    formatDateTime: FormatDateTime
): Map<String, List<DebtUiModel>> {
    return groupBy { formatDate.formatDate(it.dateTime) }
        .mapValues { entry ->
            entry.value.map { it.toUiModel(formatPrice, formatDateTime) }
        }
}