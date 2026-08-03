package com.orka.myfinances.application.viewmodels.receive.details

import com.orka.myfinances.application.viewmodels.toCardModel
import com.orka.myfinances.data.dtos.receive.ReceiveDto
import com.orka.myfinances.data.dtos.receive.ReceiveItemDto
import com.orka.myfinances.ui.models.item.ReceiveItemModel
import com.orka.myfinances.ui.models.screen.ReceiveScreenModel

fun ReceiveDto.toScreenModel(): ReceiveScreenModel {
    return ReceiveScreenModel(
        user = user.toCardModel(),
        price = price.toInt(),
        dateTime = dateTime,
        items = items.map { it.toModel() }
    )
}

fun ReceiveItemDto.toModel(): ReceiveItemModel {
    return ReceiveItemModel(
        name = productName,
        amount = amount.toInt()
    )
}