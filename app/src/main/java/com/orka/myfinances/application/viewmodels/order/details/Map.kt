package com.orka.myfinances.application.viewmodels.order.details

import com.orka.myfinances.application.viewmodels.client.list.map
import com.orka.myfinances.application.viewmodels.toCardModel
import com.orka.myfinances.data.dtos.order.OrderDto
import com.orka.myfinances.data.dtos.order.OrderItemDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.models.screen.OrderScreenModel
import com.orka.myfinances.ui.models.item.OrderItemModel

fun OrderDto.toScreenModel(): OrderScreenModel {
    return OrderScreenModel(
        price = price.toInt(),
        completed = completed,
        startDate = createdAt,
        endDate = endDateTime,
        items = items.map { it.toItemModel() },
        client = client.map(),
        clientId = Id(client.id),
        user = user.toCardModel(),
        userId = Id(user.id),
        description = description
    )
}

fun OrderItemDto.toItemModel(): OrderItemModel {
    return OrderItemModel(
        name = product.name,
        amount = amount.toInt()
    )
}
