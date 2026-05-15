package com.orka.myfinances.data.repositories

import com.orka.myfinances.data.models.basket.BasketItem
import com.orka.myfinances.lib.data.models.Item

fun BasketItem.toItem(): Item {
    return Item(product.id, amount)
}