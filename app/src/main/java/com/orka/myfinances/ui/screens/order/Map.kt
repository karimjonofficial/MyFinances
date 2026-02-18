package com.orka.myfinances.ui.screens.order

import com.orka.myfinances.data.models.order.Order
import com.orka.myfinances.lib.format.FormatDate
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.format.FormatTime

fun Order.toModel(
    priceFormatter: FormatPrice,
    dateFormatter: FormatDate,
    timeFormatter: FormatTime
): OrderCardModel {
    return OrderCardModel(
        title = "${client.firstName} ${client.lastName ?: ""}",
        dateTime = "${dateFormatter.formatDate(dateTime)} ${timeFormatter.formatTime(dateTime)}",
        size = "${items.size} items",
        price = priceFormatter.formatPrice(price.toDouble()),
        completed = completed
    )
}

fun Order.toUiModel(
    priceFormatter: FormatPrice,
    dateFormatter: FormatDate,
    timeFormatter: FormatTime
): OrderUiModel {
    return OrderUiModel(
        order = this,
        model = this.toModel(priceFormatter, dateFormatter, timeFormatter)
    )
}
