package com.orka.myfinances.ui.screens.debt.viewmodel

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.Client
import com.orka.myfinances.data.models.Debt
import com.orka.myfinances.data.repositories.debt.AddDebtRequest
import com.orka.myfinances.lib.data.repositories.Add
import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.ListViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DebtScreenViewModel(
    debtRepository: Get<Debt>,
    private val add: Add<Debt, AddDebtRequest>,
    private val clientRepository: Get<Client>,
    logger: Logger,
    loading: UiText,
    failure: UiText
) : ListViewModel<Debt>(
    loading = loading,
    failure = failure,
    repository = debtRepository,
    logger = logger
) {
    private val _dialogState = MutableStateFlow<DialogState>(DialogState.Loading)
    val dialogState = _dialogState.asStateFlow()

    fun add(request: AddDebtRequest) = launch {
        add.add(request)?.let { initialize() }
    }

    fun initializeClients() = launch {
        _dialogState.value = DialogState.Loading
        val clients = clientRepository.get()
        if (clients != null)
            _dialogState.value = DialogState.Success(clients)
    }
}
