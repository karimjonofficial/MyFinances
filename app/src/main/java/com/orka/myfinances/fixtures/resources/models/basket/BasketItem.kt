package com.orka.myfinances.fixtures.resources.models.basket

import com.orka.myfinances.data.models.basket.BasketItem
import com.orka.myfinances.fixtures.resources.amount
import com.orka.myfinances.fixtures.resources.models.product.product1
import com.orka.myfinances.fixtures.resources.models.product.product2


val basketItem1 = BasketItem(product1, amount)
val basketItem2 = BasketItem(product2, amount)
val basketItems = listOf(basketItem1, basketItem2)