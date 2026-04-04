package com.orka.myfinances.ui.screens.basket

import com.orka.myfinances.lib.ui.viewmodel.StateFul

interface BasketInteractor : StateFul {
    fun increase(item: BasketItemUiModel)
    fun decrease(item: BasketItemUiModel)
    fun remove(item: BasketItemUiModel)
    fun clear()
    fun checkout()

    companion object {
        val dummy = object : BasketInteractor {
            override fun increase(item: BasketItemUiModel) {}
            override fun decrease(item: BasketItemUiModel) {}
            override fun remove(item: BasketItemUiModel) {}
            override fun clear() {}
            override fun checkout() {}
            override fun initialize() {}
            override fun refresh() {}
        }
    }
}