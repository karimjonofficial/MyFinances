package com.orka.myfinances.data.repositories.order

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.basket.Basket
import com.orka.myfinances.data.repositories.toItem
import kotlin.time.Instant

fun Basket.toOrderRequest(
    clientId: Id,
    endDateTime: Instant? = null
): AddOrderRequest {
    return AddOrderRequest(
        clientId = clientId,
        items = items.map { it.toItem() },
        price = price,
        endDateTime = endDateTime,
        description = description
    )
}