package com.orka.myfinances.ui.screens.product.list

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.ui.viewmodel.ChunkViewModel

interface ProductTitlesContentInteractor : ChunkViewModel {
    fun selectProduct(id: Id)

    companion object {
        val dummy = object : ProductTitlesContentInteractor {
            override fun selectProduct(id: Id) {}
            override fun loadMore() {}
            override fun refresh() {}
        }
    }
}
