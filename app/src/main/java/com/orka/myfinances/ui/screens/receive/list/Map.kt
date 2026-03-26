package com.orka.myfinances.ui.screens.receive.list

import com.orka.myfinances.data.models.receive.Receive
import com.orka.myfinances.lib.format.FormatDate
import com.orka.myfinances.lib.format.FormatNames
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.format.FormatTime
import com.orka.myfinances.ui.screens.receive.list.components.ReceiveCardModel
import com.orka.myfinances.ui.screens.receive.list.viewmodel.ReceiveUiModel

fun Receive.toCardModel(
    format: FormatNames,
    priceFormatter: FormatPrice,
    dateFormatter: FormatDate,
    timeFormatter: FormatTime
): ReceiveCardModel {
    return ReceiveCardModel(
        title = format.formatNames(items.map { it.product.title }),
        price = priceFormatter.formatPrice(price.toDouble()),
        size = "${items.size} items",
        dateTime = "${dateFormatter.formatDate(dateTime)} ${timeFormatter.formatTime(dateTime)}"
    )
}

fun Receive.toUiModel(
    format: FormatNames,
    priceFormatter: FormatPrice,
    dateFormatter: FormatDate,
    timeFormatter: FormatTime
): ReceiveUiModel {
    return ReceiveUiModel(
        id = this.id,
        model = this.toCardModel(format, priceFormatter, dateFormatter, timeFormatter),
        instant = dateTime
    )
}