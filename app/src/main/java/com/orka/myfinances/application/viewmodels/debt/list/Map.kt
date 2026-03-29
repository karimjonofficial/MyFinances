package com.orka.myfinances.application.viewmodels.debt.list

import com.orka.myfinances.data.api.debt.DebtApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.format.FormatTime
import com.orka.myfinances.ui.screens.debt.list.DebtCardModel
import com.orka.myfinances.ui.screens.debt.list.DebtUiModel

fun DebtApiModel.toModel(
    formatPrice: FormatPrice,
    formatTime: FormatTime
): DebtCardModel {
    return DebtCardModel(
        name = "${client.firstName} ${client.lastName ?: ""}",
        description = description,
        price = formatPrice.formatPrice(price.toDouble()),
        dateTime = formatTime.formatTime(dateTime)
    )
}

fun DebtApiModel.toUiModel(
    formatPrice: FormatPrice,
    formatTime: FormatTime
): DebtUiModel {
    return DebtUiModel(
        model = DebtCardModel(
            name = "${client.firstName} ${client.lastName ?: ""}",
            description = description,
            price = formatPrice.formatPrice(price.toDouble()),
            dateTime = formatTime.formatTime(dateTime)
        ),
        id = Id(id)
    )
}