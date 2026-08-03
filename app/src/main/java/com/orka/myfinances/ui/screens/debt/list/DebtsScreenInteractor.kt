package com.orka.myfinances.ui.screens.debt.list

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.ui.viewmodel.PaginatedSearchable
import com.orka.myfinances.lib.ui.viewmodel.Refreshable
import com.orka.myfinances.ui.models.ui.DebtUiModel
import kotlin.time.Instant

interface DebtsScreenInteractor : Refreshable, PaginatedSearchable {
    fun add(id: Id, price: Int, endDateTime: Instant?, description: String?)
    fun select(debt: DebtUiModel)

    companion object {
        val dummy = object : DebtsScreenInteractor {
            override fun add(id: Id, price: Int, endDateTime: Instant?, description: String?) {}
            override fun select(debt: DebtUiModel) {}
            override fun refresh() {}
            override fun loadMore() {}
            override fun search(query: String) {}
            override fun resetSearch() {}
            override fun searchMore() {}
        }
    }
}
