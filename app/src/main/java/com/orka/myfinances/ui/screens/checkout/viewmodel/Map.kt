package com.orka.myfinances.ui.screens.checkout.viewmodel

import com.orka.myfinances.data.models.basket.BasketItem
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice

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