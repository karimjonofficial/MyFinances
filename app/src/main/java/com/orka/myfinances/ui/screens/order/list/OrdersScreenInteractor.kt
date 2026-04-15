package com.orka.myfinances.ui.screens.order.list

import com.orka.myfinances.lib.ui.viewmodel.ChunkViewModel
import com.orka.myfinances.lib.ui.viewmodel.StateFul

interface OrdersScreenInteractor : StateFul, ChunkViewModel {
    fun select(order: OrderUiModel)

    companion object {
        val dummy = object : OrdersScreenInteractor {
            override fun initialize() {}
            override fun refresh() {}
            override fun loadMore() {}
            override fun select(order: OrderUiModel) {}
        }
    }
}