package com.orka.myfinances.ui.screens.basket

import com.orka.myfinances.lib.ui.viewmodel.Refreshable
import com.orka.myfinances.ui.models.ui.BasketItemUiModel

interface BasketInteractor : Refreshable {
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
            override fun refresh() {}
        }
    }
}
