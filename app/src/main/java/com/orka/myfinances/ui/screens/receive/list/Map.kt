package com.orka.myfinances.ui.screens.receive.list

import com.orka.myfinances.data.models.receive.Receive
import com.orka.myfinances.lib.ui.models.ChunkUiModel
import com.orka.myfinances.ui.models.card.ReceiveCardModel
import com.orka.myfinances.ui.models.ui.ReceiveUiModel

fun Receive.toCardModel(): ReceiveCardModel {
    return ReceiveCardModel(
        title = items.joinToString { it.product.title.name },
        price = price,
        size = items.size,
        dateTime = dateTime
    )
}

fun Receive.toUiModel(): ReceiveUiModel {
    return ReceiveUiModel(
        id = this.id,
        model = this.toCardModel(),
        dateTime = dateTime
    )
}

fun List<Receive>.toChunkMapState(): ChunkUiModel<ReceiveUiModel> {
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
