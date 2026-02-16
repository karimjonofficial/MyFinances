package com.orka.myfinances.ui.screens.debt.viewmodel

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.Client
import com.orka.myfinances.data.models.Debt
import com.orka.myfinances.data.repositories.debt.AddDebtRequest
import com.orka.myfinances.lib.data.repositories.Add
import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.MapperListViewModel
import com.orka.myfinances.lib.viewmodel.list.ListViewModel
import com.orka.myfinances.ui.navigation.Navigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DebtScreenViewModel(
    debtRepository: Get<Debt>,
    private val add: Add<Debt, AddDebtRequest>,
    private val clientRepository: Get<Client>,
    logger: Logger,
    private val navigator: Navigator,
    loading: UiText,
    failure: UiText
) : MapperListViewModel<Debt, DebtUiModel>(
    loading = loading,
    failure = failure,
    repository = debtRepository,
    map = { it.toUiModel() },
    logger = logger
), ListViewModel<DebtUiModel> {
    override val uiState = state.asStateFlow()

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

    fun select(debt: Debt) {
        launch {
            navigator.navigateToDebt(debt)
        }
    }
}
