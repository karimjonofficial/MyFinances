package com.orka.myfinances.data.models.basket

data class Basket(
    val price: Int = 0,
    val description: String = "",
    val items: List<BasketItem> = emptyList()
)