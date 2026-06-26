package com.orka.myfinances.application.viewmodels.order.list.completed

import com.orka.myfinances.R
import com.orka.myfinances.data.dtos.order.OrderDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.format.FormatTime
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.ui.screens.order.list.completed.HistoryOrderCardModel
import com.orka.myfinances.ui.screens.order.list.completed.HistoryOrderUiModel

fun OrderDto.toUiModel(
    formatDecimal: FormatDecimal,
    formatPrice: FormatPrice,
    formatTime: FormatTime
): HistoryOrderUiModel {
    return HistoryOrderUiModel(
        id = Id(id),
        model = toCardModel(formatDecimal, formatPrice, formatTime)
    )
}

fun OrderDto.toCardModel(
    formatDecimal: FormatDecimal,
    formatPrice: FormatPrice,
    formatTime: FormatTime
): HistoryOrderCardModel {
    return HistoryOrderCardModel(
        title = items.joinToString { it.product.name },
        size = formatDecimal.formatDecimal(items.size.toDouble()),
        price = formatPrice.formatPrice(price.toDouble()),
        dateTime = if (completedDateTime != null) UiText.Str(formatTime.formatTime(completedDateTime)) else UiText.Res(
            R.string.completed_date_time_is_not_provided
        )
    )
}
