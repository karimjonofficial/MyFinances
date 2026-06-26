package com.orka.myfinances.data.repositories.receive

import com.orka.myfinances.data.api.receive.models.response.ReceiveApiModel
import com.orka.myfinances.data.api.receive.models.response.ReceiveItemApiModel
import com.orka.myfinances.data.dtos.receive.ReceiveDto
import com.orka.myfinances.data.dtos.receive.ReceiveItemDto
import com.orka.myfinances.data.repositories.debt.toDto

fun ReceiveApiModel.toDto(): ReceiveDto {
    return ReceiveDto(
        id = id,
        user = user.toDto(),
        branch = branch,
        price = price,
        dateTime = dateTime,
        items = items.map { it.toDto() },
        description = description,
    )
}

fun ReceiveItemApiModel.toDto(): ReceiveItemDto {
    return ReceiveItemDto(
        id = id,
        productName = product.title.name,
        amount = amount
    )
}
