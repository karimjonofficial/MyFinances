package com.orka.myfinances.application.viewmodels.order.list.incompleted

import com.orka.myfinances.R
import com.orka.myfinances.data.api.order.models.response.OrderApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.now
import com.orka.myfinances.lib.format.FormatDate
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.ui.screens.order.list.OrderCardModel
import com.orka.myfinances.ui.screens.order.list.OrderUiModel

fun OrderApiModel.toModel(
    formatDecimal: FormatDecimal,
    formatPrice: FormatPrice,
    formatDate: FormatDate
): OrderCardModel {
    val expired = endDateTime?.let { it < now() } ?: false

    return OrderCardModel(
        title = "${client.firstName} ${client.lastName ?: ""}",
        dateTime = if (endDateTime != null) UiText.Str(formatDate.formatDate(endDateTime)) else UiText.Res(
            R.string.end_date_is_not_provided
        ),
        size = formatDecimal.formatDecimal(items.size.toDouble()),
        price = formatPrice.formatPrice(price.toDouble()),
        completed = completed,
        expired = expired && !completed
    )
}

fun OrderApiModel.toUiModel(
    formatDecimal: FormatDecimal,
    formatPrice: FormatPrice,
    formatDate: FormatDate
): OrderUiModel {
    return OrderUiModel(
        id = Id(id),
        model = this.toModel(formatDecimal, formatPrice, formatDate)
    )
}