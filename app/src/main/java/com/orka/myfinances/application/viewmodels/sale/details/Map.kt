package com.orka.myfinances.application.viewmodels.sale.details

import com.orka.myfinances.application.viewmodels.client.list.map
import com.orka.myfinances.application.viewmodels.toCardModel
import com.orka.myfinances.data.dtos.sale.SaleDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.models.item.Item
import com.orka.myfinances.ui.models.screen.SaleScreenModel

fun SaleDto.toScreenModel(): SaleScreenModel {
    return SaleScreenModel(
        id = Id(id),
        price = price.toInt(),
        dateTime = dateTime,
        client = client.map(),
        user = user.toCardModel(),
        clientId = Id(client.id),
        items = items.map {
            Item(
                title = it.productName,
                supportingText = it.amount.toString()
            )
        },
        description = description
    )
}
