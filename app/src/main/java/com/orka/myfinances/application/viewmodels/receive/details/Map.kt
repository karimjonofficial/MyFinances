package com.orka.myfinances.application.viewmodels.receive.details

import com.orka.myfinances.application.viewmodels.toCardModel
import com.orka.myfinances.data.api.receive.models.response.ReceiveApiModel
import com.orka.myfinances.data.api.receive.models.response.ReceiveItemApiModel
import com.orka.myfinances.lib.format.FormatDateTime
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.ui.screens.receive.details.ReceiveItemModel
import com.orka.myfinances.ui.screens.receive.details.ReceiveScreenModel

fun ReceiveApiModel.toScreenModel(
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

fun ReceiveItemApiModel.toModel(formatDecimal: FormatDecimal): ReceiveItemModel {
    return ReceiveItemModel(
        name = product.title.name,
        amount = formatDecimal.formatDecimal(amount.toDouble())
    )
}