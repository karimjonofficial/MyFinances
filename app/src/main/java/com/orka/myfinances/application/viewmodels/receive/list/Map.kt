package com.orka.myfinances.application.viewmodels.receive.list

import com.orka.myfinances.data.dtos.receive.ReceiveDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.models.card.ReceiveCardModel
import com.orka.myfinances.ui.models.ui.ReceiveUiModel

fun ReceiveDto.toUiModel(): ReceiveUiModel {
    return ReceiveUiModel(
        id = Id(id),
        model = ReceiveCardModel(
            title = items.joinToString { it.productName },
            price = price.toInt(),
            size = items.size,
            dateTime = dateTime
        ),
        dateTime = dateTime,
    )
}
