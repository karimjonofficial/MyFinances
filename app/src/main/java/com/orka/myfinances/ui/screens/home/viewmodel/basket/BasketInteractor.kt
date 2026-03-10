package com.orka.myfinances.ui.screens.home.viewmodel.basket

import com.orka.myfinances.data.models.basket.BasketItem
import com.orka.myfinances.lib.ui.viewmodel.StateFul

interface BasketInteractor : StateFul {
    fun increase(item: BasketItem)
    fun decrease(item: BasketItem)
    fun remove(item: BasketItem)
    fun clear()
    fun checkout()
}