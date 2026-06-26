package com.orka.myfinances.application.viewmodels.receive.details

import com.orka.myfinances.application.viewmodels.toCardModel
import com.orka.myfinances.data.dtos.receive.ReceiveDto
import com.orka.myfinances.data.dtos.receive.ReceiveItemDto
import com.orka.myfinances.lib.format.FormatDateTime
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.ui.screens.receive.details.ReceiveItemModel
import com.orka.myfinances.ui.screens.receive.details.ReceiveScreenModel

fun ReceiveDto.toScreenModel(
    formatPrice: FormatPrice,
    formatDateTime: FormatDateTime,
    formatDecimal: FormatDecimal
): ReceiveScreenModel {
    return ReceiveScreenModel(
        user = user.toCardModel(),
        price = formatPrice.formatPrice(price.toDouble()),
        dateTime = formatDateTime.formatDateTime(dateTime),
        items = items.map { it.toModel(formatDecimal) }
    )
}

fun ReceiveItemDto.toModel(formatDecimal: FormatDecimal): ReceiveItemModel {
    return ReceiveItemModel(
        name = productName,
        amount = formatDecimal.formatDecimal(amount.toDouble())
    )
}