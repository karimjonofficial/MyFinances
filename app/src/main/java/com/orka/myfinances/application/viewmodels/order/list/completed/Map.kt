package com.orka.myfinances.application.viewmodels.order.list.completed

import com.orka.myfinances.R
import com.orka.myfinances.data.api.order.models.response.OrderApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.format.FormatTime
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.ui.screens.order.list.completed.HistoryOrderCardModel
import com.orka.myfinances.ui.screens.order.list.completed.HistoryOrderUiModel

fun OrderApiModel.toUiModel(
    formatPrice: FormatPrice,
    formatTime: FormatTime
): HistoryOrderUiModel {
    return HistoryOrderUiModel(
        id = Id(id),
        model = toCardModel(formatPrice, formatTime)
    )
}

fun OrderApiModel.toCardModel(
    formatPrice: FormatPrice,
    formatTime: FormatTime
): HistoryOrderCardModel {
    return HistoryOrderCardModel(
        title = items.joinToString { it.product.title.name },
        count = "${items.size} items",
        price = formatPrice.formatPrice(price.toDouble()),
        dateTime = if(completedDateTime != null) UiText.Str(formatTime.formatTime(completedDateTime)) else UiText.Res(R.string.completed_date_time_is_not_provided)
    )
}