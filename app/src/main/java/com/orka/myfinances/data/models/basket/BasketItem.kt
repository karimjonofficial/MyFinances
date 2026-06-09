package com.orka.myfinances.data.models.basket

import com.orka.myfinances.data.api.product.ProductApiModel

data class BasketItem(
    val product: ProductApiModel,
    val availableAmount: Int,
    val amount: Int,
    val increaseEnabled: Boolean,
    val decreaseEnabled: Boolean
)