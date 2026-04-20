package com.orka.myfinances.ui.screens.order.list

import com.orka.myfinances.data.models.order.Order
import com.orka.myfinances.lib.format.FormatDate
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.ui.models.ChunkMapState
import com.orka.myfinances.lib.ui.models.UiText
import kotlin.time.Clock

fun Order.toCardModel(
    formatPrice: FormatPrice,
    formatDecimal: FormatDecimal,
    formatDate: FormatDate
): OrderCardModel {
    val expired = endDateTime?.let { it < Clock.System.now() } ?: false && !completed

    return OrderCardModel(
        title = items.joinToString { it.product.title.name },
        price = formatPrice.formatPrice(price.toDouble()),
        dateTime = UiText.Str(if (endDateTime != null) formatDate.formatDate(endDateTime) else "Date is not provided"),
        size = formatDecimal.formatDecimal(items.size.toDouble()),
        completed = completed,
        expired = expired
    )
}

fun Order.toUiModel(
    formatPrice: FormatPrice,
    formatDecimal: FormatDecimal,
    formatDate: FormatDate
): OrderUiModel {
    return OrderUiModel(
        id = this.id,
        model = this.toCardModel(
            formatPrice = formatPrice,
            formatDecimal = formatDecimal,
            formatDate = formatDate
        )
    )
}

fun List<Order>.toChunkMapState(
    formatPrice: FormatPrice,
    formatDecimal: FormatDecimal,
    formatDate: FormatDate
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
                    formatDate
                )
            }
        }
    )
}