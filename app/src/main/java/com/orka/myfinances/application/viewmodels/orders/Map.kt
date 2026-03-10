package com.orka.myfinances.application.viewmodels.orders

import com.orka.myfinances.data.api.order.OrderApiModel
import com.orka.myfinances.data.api.order.OrderItemApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.format.FormatDate
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.format.FormatTime
import com.orka.myfinances.ui.screens.orders.OrderUiModel
import com.orka.myfinances.ui.screens.orders.components.OrderCardModel
import com.orka.myfinances.ui.screens.orders.components.OrderItemModel

fun OrderApiModel.toModel(
    formatPrice: FormatPrice,
    formatDate: FormatDate,
    formatTime: FormatTime
): OrderCardModel {
    return OrderCardModel(
        title = "${client.firstName} ${client.lastName ?: ""}",
        dateTime = "${formatDate.formatDate(createdAt)} ${formatTime.formatTime(createdAt)}",
        size = "${items.size} items",
        price = formatPrice.formatPrice(price.toDouble()),
        completed = completed
    )
}

fun OrderApiModel.toUiModel(
    priceFormatter: FormatPrice,
    dateFormatter: FormatDate,
    timeFormatter: FormatTime
): OrderUiModel {
    return OrderUiModel(
        id = Id(id),
        model = this.toModel(priceFormatter, dateFormatter, timeFormatter)
    )
}

fun OrderItemApiModel.toModel(formatDecimal: FormatDecimal): OrderItemModel {
    return OrderItemModel(
        name = "Product ${product.title}",
        amount = formatDecimal.formatDecimal(amount.toDouble())
    )
}


