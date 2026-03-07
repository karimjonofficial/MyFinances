package com.orka.myfinances.ui.screens.debt.viewmodel

import com.orka.myfinances.data.repositories.debt.AddDebtRequest

interface DebtsInteractor {
    fun add(request: AddDebtRequest)
    fun initializeClients()
    fun select(debt: DebtUiModel)
}