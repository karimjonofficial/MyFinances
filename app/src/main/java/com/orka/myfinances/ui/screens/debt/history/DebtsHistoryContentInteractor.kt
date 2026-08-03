package com.orka.myfinances.ui.screens.debt.history

import com.orka.myfinances.lib.ui.viewmodel.PaginatedSearchable
import com.orka.myfinances.lib.ui.viewmodel.Refreshable
import com.orka.myfinances.ui.models.ui.DebtUiModel

interface DebtsHistoryContentInteractor : Refreshable, PaginatedSearchable {
    fun select(item: DebtUiModel)

    companion object {
        val dummy = object : DebtsHistoryContentInteractor {
            override fun select(item: DebtUiModel) {}
            override fun refresh() {}
            override fun loadMore() {}
            override fun search(query: String) {}
            override fun resetSearch() {}
            override fun searchMore() {}
        }
    }
}
