package com.orka.myfinances.ui.screens.order.list.completed

import com.orka.myfinances.lib.ui.viewmodel.ChunkViewModel
import com.orka.myfinances.lib.ui.viewmodel.StateFul

interface OrdersHistoryInteractor : StateFul, ChunkViewModel {
    fun select(order: HistoryOrderUiModel)

    companion object {
        val dummy = object : OrdersHistoryInteractor {
            override fun initialize() {}
            override fun refresh() {}
            override fun loadMore() {}
            override fun select(order: HistoryOrderUiModel) {}
        }
    }
}