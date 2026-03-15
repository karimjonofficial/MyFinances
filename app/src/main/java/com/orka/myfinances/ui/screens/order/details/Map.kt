package com.orka.myfinances.ui.screens.order.details

import com.orka.myfinances.application.viewmodels.sale.map
import com.orka.myfinances.data.models.order.Order
import com.orka.myfinances.data.models.order.OrderItem
import com.orka.myfinances.lib.format.FormatDateTime
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.ui.screens.client.list.toModel
import com.orka.myfinances.ui.screens.order.list.OrderItemModel

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
        client = client.toModel(),
        clientId = client.id,
        user = user.map(),
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