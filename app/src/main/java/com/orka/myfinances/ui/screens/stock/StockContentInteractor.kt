package com.orka.myfinances.ui.screens.stock

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.ui.viewmodel.ChunkViewModel

interface StockContentInteractor : ChunkViewModel {
    fun addToBasket(id: Id)

    companion object {
        val dummy = object : StockContentInteractor {
            override fun addToBasket(id: Id) {}
            override fun loadMore() {}
            override fun refresh() {}
        }
    }
}
