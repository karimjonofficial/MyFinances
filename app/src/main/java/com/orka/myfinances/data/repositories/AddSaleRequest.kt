package com.orka.myfinances.data.repositories

import com.orka.myfinances.data.models.basket.Basket

data class AddSaleRequest(
    val basket: Basket,
    val client: Int
)