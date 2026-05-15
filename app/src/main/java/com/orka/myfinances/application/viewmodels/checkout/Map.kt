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
        price = "${formatPrice.formatPrice(product.exposedPrice.toDouble())} x ${
            formatDecimal.formatDecimal(
                amount.toDouble()
            )
        } = ${formatPrice.formatPrice(product.exposedPrice.toDouble() * amount)}"
    )
}