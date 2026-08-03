package com.orka.myfinances.ui.screens.product.list

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.ui.viewmodel.PaginatedSearchable
import com.orka.myfinances.lib.ui.viewmodel.Refreshable

interface ProductTitlesContentInteractor : Refreshable, PaginatedSearchable {
    fun selectProduct(id: Id)

    companion object {
        val dummy = object : ProductTitlesContentInteractor {
            override fun selectProduct(id: Id) {}
            override fun refresh() {}
            override fun loadMore() {}
            override fun search(query: String) {}
            override fun resetSearch() {}
            override fun searchMore() {}
        }
    }
}
