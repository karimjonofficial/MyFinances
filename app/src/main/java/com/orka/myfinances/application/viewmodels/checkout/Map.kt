package com.orka.myfinances.application.viewmodels.checkout

import com.orka.myfinances.data.models.basket.BasketItem
import com.orka.myfinances.ui.models.item.CheckoutItemModel

fun BasketItem.toModel(): CheckoutItemModel {
    return CheckoutItemModel(
        title = product.title.name,
        price = (product.exposedPrice * amount).toInt()
    )
}