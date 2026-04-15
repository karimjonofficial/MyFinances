package com.orka.myfinances.ui.screens.order.list

import com.orka.myfinances.data.models.order.Order
import com.orka.myfinances.lib.format.FormatDateTime
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.ui.models.ChunkMapState
import com.orka.myfinances.lib.ui.models.UiText

fun Order.toCardModel(
    formatPrice: FormatPrice,
    formatDecimal: FormatDecimal,
    formatDateTime: FormatDateTime
): OrderCardModel {
    return OrderCardModel(
        title = items.joinToString { it.product.title.name },
        price = formatPrice.formatPrice(price.toDouble()),
        dateTime = UiText.Str(if (endDateTime != null) formatDateTime.formatDateTime(endDateTime) else "♦"),
        size = formatDecimal.formatDecimal(items.size.toDouble()),
        completed = completed
    )
}

fun Order.toUiModel(
    formatPrice: FormatPrice,
    formatDecimal: FormatDecimal,
    formatDateTime: FormatDateTime
): OrderUiModel {
    return OrderUiModel(
        id = this.id,
        model = this.toCardModel(
            formatPrice = formatPrice,
            formatDecimal = formatDecimal,
            formatDateTime = formatDateTime
        )
    )
}

fun List<Order>.toChunkMapState(
    formatPrice: FormatPrice,
    formatDecimal: FormatDecimal,
    formatDateTime: FormatDateTime
): ChunkMapState<OrderUiModel> {
    return ChunkMapState(
        count = 1,
        pageIndex = 1,
        nextPageIndex = 1,
        previousPageIndex = 1,
        content = groupBy { it.dateTime }.mapKeys { it.key.toString() }.mapValues { entry ->
            entry.value.map {
                it.toUiModel(
                    formatPrice,
                    formatDecimal,
                    formatDateTime
                )
            }
        }
    )
}