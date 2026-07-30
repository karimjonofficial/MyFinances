package com.orka.myfinances.application.viewmodels.order.list.incompleted

import com.orka.myfinances.data.dtos.order.OrderDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.format.FormatDecimal
import com.orka.myfinances.format.FormatPrice
import com.orka.myfinances.lib.data.now
import com.orka.myfinances.ui.screens.order.list.OrderCardModel
import com.orka.myfinances.ui.screens.order.list.OrderUiModel

fun OrderDto.toModel(
    formatDecimal: FormatDecimal,
    formatPrice: FormatPrice
): OrderCardModel {
    val expired = endDateTime?.let { it < now() } ?: false

    return OrderCardModel(
        title = "${client.firstName} ${client.lastName ?: ""}",
        dateTime = ,
        size = formatDecimal.formatDecimal(items.size.toDouble()),
        price = formatPrice.formatPrice(price.toDouble()),
        completed = completed,
        expired = expired && !completed
    )
}

fun OrderDto.toUiModel(
    formatDecimal: FormatDecimal,
    formatPrice: FormatPrice
): OrderUiModel {
    return OrderUiModel(
        id = Id(id),
        model = this.toModel(formatDecimal, formatPrice)
    )
}
