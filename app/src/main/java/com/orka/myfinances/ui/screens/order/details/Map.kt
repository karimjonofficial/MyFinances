package com.orka.myfinances.ui.screens.order.details

import com.orka.myfinances.data.models.order.Order
import com.orka.myfinances.data.models.order.OrderItem
import com.orka.myfinances.lib.format.FormatDateTime
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.ui.screens.client.list.toCardModel
import com.orka.myfinances.ui.screens.order.details.OrderItemModel
import com.orka.myfinances.ui.screens.toCardModel

fun Order.map(
    formatDateTime: FormatDateTime,
    formatPrice: FormatPrice,
    formatDecimal: FormatDecimal
): OrderScreenModel {
    return OrderScreenModel(
        price = formatPrice.formatPrice(price.toDouble()),
        completed = completed,
        startDate = formatDateTime.formatDateTime(dateTime),
        endDate = if(endDateTime != null) formatDateTime.formatDateTime(endDateTime) else null,
        items = items.map { it.map(formatDecimal) },
        client = client.toCardModel(),
        clientId = client.id,
        user = user.toCardModel(),
        userId = user.id,
        description = description
    )
}

fun OrderItem.map(formatDecimal: FormatDecimal): OrderItemModel {
    return OrderItemModel(
        name = product.title.name,
        amount = formatDecimal.formatDecimal(amount.toDouble())
    )
}