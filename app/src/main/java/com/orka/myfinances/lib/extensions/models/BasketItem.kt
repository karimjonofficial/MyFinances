package com.orka.myfinances.lib.extensions.models

import com.orka.myfinances.data.models.basket.BasketItem

fun List<BasketItem>.getPrice(): Int {
    return sumOf { it.product.salePrice * it.amount }
}