package com.orka.myfinances.ui.screens.order.list

import com.orka.myfinances.data.models.order.Order
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatNames
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.format.FormatTime

fun Order.map(
    formatNames: FormatNames,
    formatPrice: FormatPrice,
    formatDecimal: FormatDecimal,
    formatTime: FormatTime
): OrderCardModel {
    return OrderCardModel(
        title = formatNames.formatNames(items.map { it.product.title }),
        price = formatPrice.formatPrice(price.toDouble()),
        dateTime = formatTime.formatTime(dateTime),
        size = formatDecimal.formatDecimal(items.size.toDouble()),
        completed = completed
    )
}