package com.orka.myfinances.fixtures.resources.models.basket

import com.orka.myfinances.data.models.basket.BasketItem
import com.orka.myfinances.fixtures.resources.amount
import com.orka.myfinances.fixtures.resources.models.product.product1
import com.orka.myfinances.fixtures.resources.models.product.product2
import com.orka.myfinances.ui.screens.checkout.map

val basketItem1 = BasketItem(product1.map(), amount)
val basketItem2 = BasketItem(product2.map(), amount)
val basketItems = listOf(basketItem1, basketItem2)