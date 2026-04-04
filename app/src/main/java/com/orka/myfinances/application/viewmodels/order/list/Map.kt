package com.orka.myfinances.application.viewmodels.order.list

import com.orka.myfinances.R
import com.orka.myfinances.data.api.order.models.response.OrderApiModel
import com.orka.myfinances.data.api.order.models.response.OrderItemApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.format.FormatDateTime
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.ui.screens.order.list.OrderItemModel
import com.orka.myfinances.ui.screens.order.list.OrderUiModel
import com.orka.myfinances.ui.screens.order.list.OrderCardModel

fun OrderApiModel.toModel(
    formatPrice: FormatPrice,
    formatDateTime: FormatDateTime
): OrderCardModel {
    return OrderCardModel(
        title = "${client.firstName} ${client.lastName ?: ""}",
        dateTime = if(endDateTime != null) UiText.Str(formatDateTime.formatDateTime(endDateTime)) else UiText.Res(R.string.end_date_is_not_provided),
        size = "${items.size} items",
        price = formatPrice.formatPrice(price.toDouble()),
        completed = completed
    )
}

fun OrderApiModel.toUiModel(
    formatPrice: FormatPrice,
    formatDateTime: FormatDateTime
): OrderUiModel {
    return OrderUiModel(
        id = Id(id),
        model = this.toModel(formatPrice, formatDateTime)
    )
}

fun OrderItemApiModel.toModel(formatDecimal: FormatDecimal): OrderItemModel {
    return OrderItemModel(
        name = "Product ${product.title}",
        amount = formatDecimal.formatDecimal(amount.toDouble())
    )
}


