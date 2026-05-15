package com.orka.myfinances.data.repositories.sale

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.basket.Basket
import com.orka.myfinances.data.repositories.toItem

fun Basket.toSaleRequest(clientId: Id): AddSaleRequest {
    return AddSaleRequest(
        clientId = clientId,
        items = items.map { it.toItem() },
        price = price,
        description = description
    )
}