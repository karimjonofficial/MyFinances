package com.orka.myfinances.ui.screens.order.list

import com.orka.myfinances.data.models.order.Order
import com.orka.myfinances.lib.ui.models.ChunkUiModel
import com.orka.myfinances.ui.models.card.OrderCardModel
import com.orka.myfinances.ui.models.ui.OrderUiModel
import kotlin.time.Clock

fun Order.toCardModel(): OrderCardModel {
    val expired = endDateTime?.let { it < Clock.System.now() } ?: false && !completed

    return OrderCardModel(
        title = items.joinToString { it.product.title.name },
        price = price,
        dateTime = dateTime,
        size = items.size,
        completed = completed,
        expired = expired
    )
}

fun Order.toUiModel(): OrderUiModel {
    return OrderUiModel(
        id = this.id,
        model = this.toCardModel()
    )
}

fun List<Order>.toChunkMapState(): ChunkUiModel<OrderUiModel> {
    return ChunkUiModel(
        size = size,
        pageIndex = 1,
        nextPageIndex = null,
        previousPageIndex = null,
        content = groupBy { it.dateTime }.mapKeys { it.key.toString() }.mapValues { entry ->
            entry.value.map {
                it.toUiModel()
            }
        }
    )
}
