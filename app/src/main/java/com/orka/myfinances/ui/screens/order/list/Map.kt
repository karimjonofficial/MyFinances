package com.orka.myfinances.ui.screens.order.list

import com.orka.myfinances.data.models.order.Order
import com.orka.myfinances.lib.format.FormatDateTime
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatNames
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.ui.models.UiText

fun Order.map(
    formatNames: FormatNames,
    formatPrice: FormatPrice,
    formatDecimal: FormatDecimal,
    formatDateTime: FormatDateTime
): OrderCardModel {
    return OrderCardModel(
        title = formatNames.formatNames(items.map { it.product.title }),
        price = formatPrice.formatPrice(price.toDouble()),
        dateTime = UiText.Str(if(endDateTime != null) formatDateTime.formatDateTime(endDateTime) else "♦"),
        size = formatDecimal.formatDecimal(items.size.toDouble()),
        completed = completed
    )
}