package com.orka.myfinances.ui.screens.debt.viewmodel

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.Debt
import com.orka.myfinances.data.repositories.client.ClientRepository
import com.orka.myfinances.data.repositories.debt.AddDebtRequest
import com.orka.myfinances.data.repositories.debt.DebtRepository
import com.orka.myfinances.lib.ui.viewmodel.ListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DebtScreenViewModel(
    private val debtRepository: DebtRepository,
    private val clientRepository: ClientRepository,
    logger: Logger,
    coroutineScope: CoroutineScope
) : ListViewModel<Unit, Debt, Unit>(
    loading = Unit,
    failure = Unit,
    repository = debtRepository,
    logger = logger,
    coroutineScope = coroutineScope
) {

    private val _dialogState = MutableStateFlow<DialogState>(DialogState.Loading)
    val dialogState = _dialogState.asStateFlow()

    fun add(request: AddDebtRequest) = launch {
        debtRepository.add(request)?.let { initialize() }
    }

    fun initializeClients() = launch {
        _dialogState.value = DialogState.Loading
        val clients = clientRepository.get()
        if (clients != null)
            _dialogState.value = DialogState.Success(clients)
    }
}
