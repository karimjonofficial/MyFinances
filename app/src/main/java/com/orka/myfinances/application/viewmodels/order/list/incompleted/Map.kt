package com.orka.myfinances.application.viewmodels.order.list.incompleted

import com.orka.myfinances.data.dtos.order.OrderDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.now
import com.orka.myfinances.ui.models.card.OrderCardModel
import com.orka.myfinances.ui.models.ui.OrderUiModel

fun OrderDto.toModel(): OrderCardModel {
    val expired = endDateTime?.let { it < now() } ?: false

    return OrderCardModel(
        title = "${client.firstName} ${client.lastName ?: ""}",
        dateTime = createdAt,
        size = items.size,
        price = price.toInt(),
        completed = completed,
        expired = expired && !completed
    )
}

fun OrderDto.toUiModel(): OrderUiModel {
    return OrderUiModel(
        id = Id(id),
        model = this.toModel()
    )
}
