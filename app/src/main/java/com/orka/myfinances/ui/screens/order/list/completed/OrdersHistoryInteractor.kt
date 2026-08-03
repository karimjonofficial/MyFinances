package com.orka.myfinances.ui.screens.order.list.completed

import com.orka.myfinances.lib.ui.viewmodel.PaginatedSearchable
import com.orka.myfinances.lib.ui.viewmodel.Refreshable
import com.orka.myfinances.ui.models.ui.HistoryOrderUiModel

interface OrdersHistoryInteractor : Refreshable, PaginatedSearchable {
    fun select(order: HistoryOrderUiModel)

    companion object {
        val dummy = object : OrdersHistoryInteractor {
            override fun select(order: HistoryOrderUiModel) {}
            override fun refresh() {}
            override fun loadMore() {}
            override fun search(query: String) {}
            override fun resetSearch() {}
            override fun searchMore() {}
        }
    }
}
