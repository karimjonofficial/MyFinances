package com.orka.myfinances.ui.screens.order.viewmodel

import com.orka.myfinances.data.models.order.Order
import com.orka.myfinances.data.models.order.OrderItem
import com.orka.myfinances.lib.format.FormatDate
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.format.FormatTime
import com.orka.myfinances.ui.screens.clients.viewmodel.toUiModel
import com.orka.myfinances.ui.screens.order.components.OrderCardModel

fun Order.toModel(
    formatPrice: FormatPrice,
    formatDate: FormatDate,
    formatTime: FormatTime
): OrderCardModel {
    return OrderCardModel(
        title = "${client.firstName} ${client.lastName ?: ""}",
        dateTime = "${formatDate.formatDate(dateTime)} ${formatTime.formatTime(dateTime)}",
        size = "${items.size} items",
        price = formatPrice.formatPrice(price.toDouble()),
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

fun Order.toScreenModel(
    formatPrice: FormatPrice,
    formatDate: FormatDate,
    formatDecimal: FormatDecimal
): OrderScreenModel {
    return OrderScreenModel(
        price = formatPrice.formatPrice(price.toDouble()),
        completed = completed,
        startDate = formatDate.formatDate(dateTime),
        endDate = if(endDateTime != null) formatDate.formatDate(endDateTime) else "",
        items = items.map { it.toModel(formatDecimal) },
        user = user,
        client = client.toUiModel(),
        description = description
    )
}

fun OrderItem.toModel(formatDecimal: FormatDecimal): OrderItemModel {
    return OrderItemModel(
        name = product.title.name,
        amount = formatDecimal.formatDecimal(amount.toDouble())
    )
}
