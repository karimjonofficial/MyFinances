package com.orka.myfinances.application.viewmodels.order.list

import com.orka.myfinances.data.api.order.OrderApiModel
import com.orka.myfinances.data.api.order.OrderItemApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.format.FormatTime
import com.orka.myfinances.ui.screens.order.list.OrderItemModel
import com.orka.myfinances.ui.screens.order.list.OrderUiModel
import com.orka.myfinances.ui.screens.order.list.components.OrderCardModel

fun OrderApiModel.toModel(
    formatPrice: FormatPrice,
    formatTime: FormatTime
): OrderCardModel {
    return OrderCardModel(
        title = "${client.firstName} ${client.lastName ?: ""}",
        dateTime = formatTime.formatTime(createdAt),
        size = "${items.size} items",
        price = formatPrice.formatPrice(price.toDouble()),
        completed = completed
    )
}

fun OrderApiModel.toUiModel(
    formatPrice: FormatPrice,
    formatTime: FormatTime
): OrderUiModel {
    return OrderUiModel(
        id = Id(id),
        model = this.toModel(formatPrice, formatTime)
    )
}

fun OrderItemApiModel.toModel(formatDecimal: FormatDecimal): OrderItemModel {
    return OrderItemModel(
        name = "Product ${product.title}",
        amount = formatDecimal.formatDecimal(amount.toDouble())
    )
}


