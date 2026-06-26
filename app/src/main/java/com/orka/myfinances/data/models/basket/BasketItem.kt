package com.orka.myfinances.data.models.basket

import com.orka.myfinances.data.dtos.product.ProductDto

data class BasketItem(
    val product: ProductDto,
    val availableAmount: Int,
    val amount: Int,
    val increaseEnabled: Boolean,
    val decreaseEnabled: Boolean
)
