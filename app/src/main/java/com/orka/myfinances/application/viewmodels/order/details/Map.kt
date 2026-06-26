package com.orka.myfinances.application.viewmodels.order.details

import com.orka.myfinances.application.viewmodels.client.list.map
import com.orka.myfinances.application.viewmodels.toCardModel
import com.orka.myfinances.data.dtos.order.OrderDto
import com.orka.myfinances.data.dtos.order.OrderItemDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.format.FormatDateTime
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.ui.screens.order.details.OrderScreenModel
import com.orka.myfinances.ui.screens.order.details.OrderItemModel

fun OrderDto.toScreenModel(
    formatPrice: FormatPrice,
    formatDateTime: FormatDateTime,
    formatDecimal: FormatDecimal
): OrderScreenModel {
    return OrderScreenModel(
        price = formatPrice.formatPrice(price.toDouble()),
        completed = completed,
        startDate = formatDateTime.formatDateTime(createdAt),
        endDate = if (endDateTime != null) formatDateTime.formatDateTime(endDateTime) else null,
        items = items.map { it.toItemModel(formatDecimal) },
        client = client.map(),
        clientId = Id(client.id),
        user = user.toCardModel(),
        userId = Id(user.id),
        description = description
    )
}

fun OrderItemDto.toItemModel(formatDecimal: FormatDecimal): OrderItemModel {
    return OrderItemModel(
        name = product.name,
        amount = formatDecimal.formatDecimal(amount.toDouble())
    )
}
