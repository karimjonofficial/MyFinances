package com.orka.myfinances.ui.screens.receive.viewmodel

import com.orka.myfinances.data.models.receive.Receive
import com.orka.myfinances.data.models.receive.ReceiveItem
import com.orka.myfinances.lib.format.FormatDateTime
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice

fun Receive.toUiModel(
    formatPrice: FormatPrice,
    formatDateTime: FormatDateTime,
    formatDecimal: FormatDecimal
): ReceiveUiModel {
    return ReceiveUiModel(
        price = formatPrice.formatPrice(price.toDouble()),
        dateTime = formatDateTime.formatDateTime(dateTime),
        receive = this,
        items = items.map { it.toModel(formatDecimal) }
    )
}

fun ReceiveItem.toModel(formatDecimal: FormatDecimal): ReceiveItemModel {
    return ReceiveItemModel(
        name = product.title.name,
        amount = formatDecimal.formatDecimal(amount.toDouble())
    )
}