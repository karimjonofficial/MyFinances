package com.orka.myfinances.application.viewmodels.checkout

import com.orka.myfinances.data.models.basket.BasketItem
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.ui.screens.checkout.viewmodel.BasketItemCardModel

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