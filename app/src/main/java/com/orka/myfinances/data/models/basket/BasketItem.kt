package com.orka.myfinances.data.models.basket

import com.orka.myfinances.data.api.product.ProductApiModel

data class BasketItem(
    val product: ProductApiModel,
    val amount: Int
)