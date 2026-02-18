package com.orka.myfinances.ui.screens.debt.viewmodel

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.Client
import com.orka.myfinances.data.models.Debt
import com.orka.myfinances.data.repositories.debt.AddDebtRequest
import com.orka.myfinances.lib.data.repositories.Add
import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.format.FormatDate
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.format.FormatTime
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.MapViewModel
import com.orka.myfinances.ui.navigation.Navigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

typealias IMapViewModel<T> = com.orka.myfinances.lib.viewmodel.list.MapViewModel<T>

class DebtScreenViewModel(
    get: Get<Debt>,
    private val add: Add<Debt, AddDebtRequest>,
    private val clientRepository: Get<Client>,
    priceFormatter: FormatPrice,
    private val dateFormatter: FormatDate,
    timeFormatter: FormatTime,
    loading: UiText,
    failure: UiText,
    logger: Logger,
    private val navigator: Navigator
) : MapViewModel<Debt, DebtUiModel>(
    loading = loading,
    failure = failure,
    get = get,
    map = { debts ->
        debts.groupBy { dateFormatter.formatDate(it.dateTime) }
            .mapValues { entry ->
                entry.value.map { it.toUiModel(priceFormatter, dateFormatter, timeFormatter) }
            }
    },
    logger = logger
), IMapViewModel<DebtUiModel> {
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
