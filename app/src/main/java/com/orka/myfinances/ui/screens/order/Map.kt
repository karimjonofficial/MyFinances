package com.orka.myfinances.ui.screens.order

import com.orka.myfinances.data.models.order.Order
import com.orka.myfinances.lib.format.FormatDate
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.format.FormatTime
import com.orka.myfinances.ui.screens.orders.components.OrderCardModel

fun Order.map(
    formatDate: FormatDate,
    formatTime: FormatTime,
    formatPrice: FormatPrice
): OrderCardModel {
    return OrderCardModel(
        title = "${client.firstName} ${client.lastName ?: ""}",
        dateTime = "${formatDate.formatDate(dateTime)} ${formatTime.formatTime(dateTime)}",
        size = "${items.size} items",
        price = formatPrice.formatPrice(price.toDouble()),
        completed = completed
    )
}