package com.orka.myfinances.ui.screens.debt.list.interactor

import com.orka.myfinances.data.repositories.debt.AddDebtRequest
import com.orka.myfinances.lib.ui.viewmodel.DialogViewModel
import com.orka.myfinances.lib.ui.viewmodel.MapViewModel
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.ui.viewmodel.StateFul
import com.orka.myfinances.ui.screens.debt.list.DebtUiModel
import com.orka.myfinances.ui.screens.debt.list.DialogState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface DebtsScreenInteractor : StateFul, MapViewModel<DebtUiModel>,
    DialogViewModel<DialogState> {
    fun add(request: AddDebtRequest)
    fun initializeClients()
    fun select(debt: DebtUiModel)

    companion object {
        val dummy = object : DebtsScreenInteractor {
            override fun add(request: AddDebtRequest) {}
            override fun initializeClients() {}
            override fun select(debt: DebtUiModel) {}
            override fun initialize() {}
            override val uiState: StateFlow<State> = MutableStateFlow(State.Initial)
            override val dialogState: StateFlow<DialogState> = MutableStateFlow(DialogState.Loading)
        }
    }
}