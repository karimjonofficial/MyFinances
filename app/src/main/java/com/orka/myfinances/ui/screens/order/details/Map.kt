package com.orka.myfinances.ui.screens.order.details

import com.orka.myfinances.data.models.order.Order
import com.orka.myfinances.data.models.order.OrderItem
import com.orka.myfinances.ui.screens.client.list.toCardModel
import com.orka.myfinances.ui.map.toCardModel
import com.orka.myfinances.ui.models.item.OrderItemModel
import com.orka.myfinances.ui.models.screen.OrderScreenModel

fun Order.map(): OrderScreenModel {
    return OrderScreenModel(
        price = price,
        completed = completed,
        startDate = dateTime,
        endDate = endDateTime,
        items = items.map { it.map() },
        client = client.toCardModel(),
        clientId = client.id,
        user = user.toCardModel(),
        userId = user.id,
        description = description
    )
}

fun OrderItem.map(): OrderItemModel {
    return OrderItemModel(
        name = product.title.name,
        amount = amount
    )
}
