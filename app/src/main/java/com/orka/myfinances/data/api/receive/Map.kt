package com.orka.myfinances.data.api.receive

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.format.FormatTime
import com.orka.myfinances.ui.screens.history.components.ReceiveCardModel
import com.orka.myfinances.ui.screens.history.viewmodel.ReceiveUiModel

fun ReceiveApiModel.map(
    formatPrice: FormatPrice,
    formatDecimal: FormatDecimal,
    formatTime: FormatTime
): ReceiveUiModel {
    return ReceiveUiModel(
        id = Id(id),
        model = ReceiveCardModel(
            title = items.joinToString { it.product.title.name },
            price = formatPrice.formatPrice(price.toDouble()),
            size = "${formatDecimal.formatDecimal(items.size.toDouble())} items",
            dateTime = formatTime.formatTime(dateTime)
        ),
        instant = dateTime,
    )
}