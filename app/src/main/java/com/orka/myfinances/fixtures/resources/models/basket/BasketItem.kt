package com.orka.myfinances.fixtures.resources.models.basket

import com.orka.myfinances.data.models.basket.BasketItem
import com.orka.myfinances.data.repositories.stock.toDto
import com.orka.myfinances.fixtures.resources.amount
import com.orka.myfinances.fixtures.resources.models.product.product1
import com.orka.myfinances.fixtures.resources.models.product.product2
import com.orka.myfinances.ui.screens.checkout.map

val basketItem1 = BasketItem(
    product = product1.map().toDto(),
    availableAmount = amount,
    amount = amount,
    increaseEnabled = true,
    decreaseEnabled = true
)
val basketItem2 = BasketItem(
    product = product2.map().toDto(),
    availableAmount = amount,
    amount = amount,
    increaseEnabled = true,
    decreaseEnabled = true
)
val basketItems = listOf(basketItem1, basketItem2)
