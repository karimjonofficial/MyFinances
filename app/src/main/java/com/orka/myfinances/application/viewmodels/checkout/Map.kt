package com.orka.myfinances.application.viewmodels.checkout

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.basket.Basket
import com.orka.myfinances.data.models.basket.BasketItem
import com.orka.myfinances.data.repositories.order.AddOrderRequest
import com.orka.myfinances.data.repositories.sale.AddSaleRequest
import com.orka.myfinances.lib.data.models.Item
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.ui.screens.checkout.viewmodel.BasketItemCardModel
import kotlin.time.Instant

fun BasketItem.toModel(formatPrice: FormatPrice, formatDecimal: FormatDecimal): BasketItemCardModel {
    return BasketItemCardModel(
        title = product.title.name,
        price = "${formatPrice.formatPrice(product.price.toDouble())} x ${
            formatDecimal.formatDecimal(
                amount.toDouble()
            )
        } = ${formatPrice.formatPrice(product.price.toDouble() * amount)}"
    )
}

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

fun Basket.toSaleRequest(clientId: Id): AddSaleRequest {
    return AddSaleRequest(
        clientId = clientId,
        items = items.map { it.toItem() },
        price = price,
        description = description
    )
}

fun BasketItem.toItem(): Item {
    return Item(product.id, amount)
}