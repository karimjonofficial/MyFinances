package com.orka.myfinances.application.viewmodels.debt.details

import com.orka.myfinances.R
import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.api.debt.DebtApi
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.format.FormatDate
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.SingleStateViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.debt.details.DebtScreenModel
import com.orka.myfinances.ui.screens.debt.details.interactor.DebtScreenInteractor
import kotlinx.coroutines.flow.asStateFlow

class DebtScreenViewModel(
    private val id: Id,
    private val debtApi: DebtApi,
    private val formatPrice: FormatPrice,
    private val formatDate: FormatDate,
    private val navigator: Navigator,
    loading: UiText,
    logger: Logger
) : SingleStateViewModel<State<DebtScreenModel>>(
    initialState = State.Loading(loading),
    logger = logger
), DebtScreenInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
    }

    override fun initialize() {
        launch {
            val debt = debtApi.getById(id.value)
            if (debt != null) {
                val model = debt.map(formatPrice, formatDate)
                setState(State.Success(model))
            } else {
                setState(State.Failure(UiText.Res(R.string.failure)))
            }
        }
    }

    override fun back() {
        launch {
            navigator.back()
        }
    }

    override fun navigateToClient(id: Id) {
        launch {
            navigator.navigateToClient(id)
        }
    }
}