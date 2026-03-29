package com.orka.myfinances.ui.screens.debt.list.interactor

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.ui.viewmodel.ChunkViewModel
import com.orka.myfinances.lib.ui.viewmodel.StateFul
import com.orka.myfinances.ui.screens.debt.list.DebtUiModel
import kotlin.time.Instant

interface DebtsScreenInteractor : StateFul, ChunkViewModel {
    fun add(id: Id, price: Int, endDateTime: Instant?, description: String?)
    fun initializeClients()
    fun select(debt: DebtUiModel)

    companion object {
        val dummy = object : DebtsScreenInteractor {
            override fun add(id: Id, price: Int, endDateTime: Instant?, description: String?) {}
            override fun initializeClients() {}
            override fun select(debt: DebtUiModel) {}
            override fun initialize() {}
            override fun refresh() {}
            override fun loadMore() {}
        }
    }
}