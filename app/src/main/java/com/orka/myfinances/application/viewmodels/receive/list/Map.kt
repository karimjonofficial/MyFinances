package com.orka.myfinances.application.viewmodels.receive.list

import com.orka.myfinances.data.dtos.receive.ReceiveDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.format.FormatTime
import com.orka.myfinances.ui.screens.receive.list.components.ReceiveCardModel
import com.orka.myfinances.ui.screens.receive.list.viewmodel.ReceiveUiModel

fun ReceiveDto.toUiModel(
    formatPrice: FormatPrice,
    formatDecimal: FormatDecimal,
    formatTime: FormatTime
): ReceiveUiModel {
    return ReceiveUiModel(
        id = Id(id),
        model = ReceiveCardModel(
            title = items.joinToString { it.productName },
            price = formatPrice.formatPrice(price.toDouble()),
            size = formatDecimal.formatDecimal(items.size.toDouble()),
            dateTime = formatTime.formatTime(dateTime)
        ),
        instant = dateTime,
    )
}