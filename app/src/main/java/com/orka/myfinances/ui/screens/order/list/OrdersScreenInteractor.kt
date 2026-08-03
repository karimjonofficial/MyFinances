package com.orka.myfinances.ui.screens.order.list

import com.orka.myfinances.lib.ui.viewmodel.PaginatedSearchable
import com.orka.myfinances.lib.ui.viewmodel.Refreshable
import com.orka.myfinances.ui.models.ui.OrderUiModel

interface OrdersScreenInteractor : Refreshable, PaginatedSearchable {
    fun select(order: OrderUiModel)

    companion object {
        val dummy = object : OrdersScreenInteractor {
            override fun select(order: OrderUiModel) {}
            override fun refresh() {}
            override fun loadMore() {}
            override fun search(query: String) {}
            override fun resetSearch() {}
            override fun searchMore() {}
        }
    }
}
