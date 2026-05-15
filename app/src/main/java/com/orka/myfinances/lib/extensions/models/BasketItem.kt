package com.orka.myfinances.lib.extensions.models

import com.orka.myfinances.data.models.basket.BasketItem

fun List<BasketItem>.getExposedPrice(): Long {
    return sumOf { it.product.exposedPrice * it.amount }
}

fun List<BasketItem>.getSalePrice(): Long {
    return sumOf { it.product.salePrice * it.amount }
}