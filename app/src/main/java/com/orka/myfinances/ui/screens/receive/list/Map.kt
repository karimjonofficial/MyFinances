package com.orka.myfinances.ui.screens.receive.list

import com.orka.myfinances.data.models.receive.Receive
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.format.FormatTime
import com.orka.myfinances.lib.ui.models.ChunkMapState
import com.orka.myfinances.ui.screens.receive.list.components.ReceiveCardModel
import com.orka.myfinances.ui.screens.receive.list.viewmodel.ReceiveUiModel

fun Receive.toCardModel(
    formatPrice: FormatPrice,
    formatTime: FormatTime
): ReceiveCardModel {
    return ReceiveCardModel(
        title = items.joinToString { it.product.title.name },
        price = formatPrice.formatPrice(price.toDouble()),
        size = "${items.size} items",
        dateTime = formatTime.formatTime(dateTime)
    )
}

fun Receive.toUiModel(
    formatPrice: FormatPrice,
    formatTime: FormatTime
): ReceiveUiModel {
    return ReceiveUiModel(
        id = this.id,
        model = this.toCardModel(formatPrice, formatTime),
        instant = dateTime
    )
}

fun List<Receive>.toChunkMapState(
    formatPrice: FormatPrice,
    formatTime: FormatTime
): ChunkMapState<ReceiveUiModel> {
    return ChunkMapState(
        count = 1,
        pageIndex = 1,
        nextPageIndex = 1,
        previousPageIndex = 1,
        content = groupBy { it.dateTime }.mapKeys { it.key.toString() }.mapValues { entry ->
            entry.value.map {
                it.toUiModel(
                    formatPrice,
                    formatTime
                )
            }
        }
    )
}