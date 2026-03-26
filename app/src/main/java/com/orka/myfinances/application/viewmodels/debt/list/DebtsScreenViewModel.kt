package com.orka.myfinances.application.viewmodels.debt.list

import com.orka.myfinances.application.viewmodels.client.details.toItemModel
import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.api.debt.DebtApiModel
import com.orka.myfinances.data.api.client.ClientApi
import com.orka.myfinances.data.api.debt.DebtApi
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.debt.AddDebtRequest
import com.orka.myfinances.lib.format.FormatDate
import com.orka.myfinances.lib.format.FormatDateTime
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.viewmodel.MapListViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.debt.list.DebtUiModel
import com.orka.myfinances.ui.screens.debt.list.interactor.DebtsScreenInteractor
import com.orka.myfinances.ui.screens.debt.list.DialogState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.time.Instant

typealias IMapViewModel<T> = com.orka.myfinances.lib.ui.viewmodel.MapViewModel<T>

class DebtsScreenViewModel(
    private val debtApi: DebtApi,
    private val clientApi: ClientApi,
    formatPrice: FormatPrice,
    formatDate: FormatDate,
    formatDateTime: FormatDateTime,
    loading: UiText,
    failure: UiText,
    logger: Logger,
    private val navigator: Navigator
) : MapListViewModel<DebtApiModel, DebtUiModel>(
    loading = loading,
    failure = failure,
    get = { debtApi.getAll() },
    map = { it.toMap(formatPrice, formatDate, formatDateTime) },
    logger = logger
), IMapViewModel<DebtUiModel>, DebtsScreenInteractor {
    override val uiState = state.asStateFlow()

    init {
        initialize()
    }

    private val _dialogState = MutableStateFlow<DialogState>(DialogState.Loading)
    override val dialogState = _dialogState.asStateFlow()

    override fun add(id: Id, price: Int, endDateTime: Instant?, description: String?) {
        launch {
            val request = AddDebtRequest(id, price, description, endDateTime)
            if (debtApi.add(request)) {
                initialize()
            }
        }
    }

    override fun initializeClients() {
        launch {
            _dialogState.value = DialogState.Loading
            try {
                val clients = clientApi.getAll()
                if (clients != null) {
                    _dialogState.value = DialogState.Success(clients.map { it.toItemModel() })
                }
            } catch (e: Exception) {
                logger.log(
                    tag = "DebtsScreenViewModel",
                    message = e.message.toString()
                )
            }
        }
    }

    override fun select(debt: DebtUiModel) {
        launch {
            navigator.navigateToDebt(debt.id)
        }
    }
}
