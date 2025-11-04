package com.orka.myfinances.data.models.basket

import com.orka.myfinances.data.models.product.Product

data class BasketItem(
    val product: Product,
    val amount: Int
)