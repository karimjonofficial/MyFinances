package com.orka.myfinances.application.viewmodels.order.list.completed

import com.orka.myfinances.data.dtos.order.OrderDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.models.card.HistoryOrderCardModel
import com.orka.myfinances.ui.models.ui.HistoryOrderUiModel

fun OrderDto.toUiModel(): HistoryOrderUiModel {
    return HistoryOrderUiModel(
        id = Id(id),
        model = toCardModel()
    )
}

fun OrderDto.toCardModel(): HistoryOrderCardModel {
    return HistoryOrderCardModel(
        title = items.joinToString { it.product.name },
        size = items.size,
        price = price.toInt(),
        dateTime = createdAt
    )
}
