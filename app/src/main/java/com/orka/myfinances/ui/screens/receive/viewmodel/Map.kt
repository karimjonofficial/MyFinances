package com.orka.myfinances.ui.screens.receive.viewmodel

import com.orka.myfinances.data.api.receive.ReceiveApiModel
import com.orka.myfinances.data.api.receive.ReceiveItemApiModel
import com.orka.myfinances.lib.format.FormatDateTime
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice

fun ReceiveApiModel.toUiModel(
    formatPrice: FormatPrice,
    formatDateTime: FormatDateTime,
    formatDecimal: FormatDecimal
): ReceiveUiModel {
    return ReceiveUiModel(
        price = formatPrice.formatPrice(price.toDouble()),
        dateTime = formatDateTime.formatDateTime(dateTime),
        items = items.map { it.toModel(formatDecimal) }
    )
}

fun ReceiveItemApiModel.toModel(formatDecimal: FormatDecimal): ReceiveItemModel {
    return ReceiveItemModel(
        name = product.title.name,
        amount = formatDecimal.formatDecimal(amount.toDouble())
    )
}