package com.orka.myfinances.ui.screens.stock

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.ui.viewmodel.PaginatedSearchable
import com.orka.myfinances.lib.ui.viewmodel.Refreshable

interface StockContentInteractor : Refreshable, PaginatedSearchable {
    fun addToBasket(id: Id)
    fun removeFromBasket(id: Id)
    fun expose()
    fun unExpose()

    companion object {
        val dummy = object : StockContentInteractor {
            override fun addToBasket(id: Id) {}
            override fun removeFromBasket(id: Id) {}
            override fun refresh() {}
            override fun loadMore() {}
            override fun search(query: String) {}
            override fun resetSearch() {}
            override fun searchMore() {}
            override fun expose() {}
            override fun unExpose() {}
        }
    }
}
