package com.orka.myfinances.lib.extensions.models

import com.orka.myfinances.data.models.basket.Basket

fun Basket.isEmpty(): Boolean {
    return description?.isBlank() ?: false && price == 0 && items.isEmpty()
}