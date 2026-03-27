package com.orka.myfinances.ui.screens.basket

import com.orka.myfinances.data.models.basket.BasketItem
import com.orka.myfinances.lib.ui.viewmodel.StateFul

interface BasketInteractor : StateFul {
    fun increase(item: BasketItem)
    fun decrease(item: BasketItem)
    fun remove(item: BasketItem)
    fun clear()
    fun checkout()

    companion object {
        val dummy = object : BasketInteractor {
            override fun increase(item: BasketItem) {}
            override fun decrease(item: BasketItem) {}
            override fun remove(item: BasketItem) {}
            override fun clear() {}
            override fun checkout() {}
            override fun refresh() {}
        }
    }
}