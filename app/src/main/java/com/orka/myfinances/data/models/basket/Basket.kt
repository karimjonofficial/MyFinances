package com.orka.myfinances.data.models.basket

data class Basket(
    val price: Int = 0,
    val description: String? = null,
    val items: List<BasketItem> = emptyList()
)