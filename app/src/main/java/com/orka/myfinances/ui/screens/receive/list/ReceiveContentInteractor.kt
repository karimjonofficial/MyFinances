package com.orka.myfinances.ui.screens.receive.list

import com.orka.myfinances.lib.ui.viewmodel.PaginatedSearchable
import com.orka.myfinances.lib.ui.viewmodel.Refreshable
import com.orka.myfinances.ui.models.ui.ReceiveUiModel

interface ReceiveContentInteractor : Refreshable, PaginatedSearchable {
    fun select(receive: ReceiveUiModel)

    companion object {
        val dummy = object : ReceiveContentInteractor {
            override fun select(receive: ReceiveUiModel) {}
            override fun refresh() {}
            override fun loadMore() {}
            override fun search(query: String) {}
            override fun resetSearch() {}
            override fun searchMore() {}
        }
    }
}
