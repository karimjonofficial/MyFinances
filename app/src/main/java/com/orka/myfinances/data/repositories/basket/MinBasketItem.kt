package com.orka.myfinances.data.repositories.basket

import com.orka.myfinances.data.models.Id

data class MinBasketItem(
    val id: Id,
    val amount: Int
)