package com.orka.myfinances.application.viewmodels.debt.details

import com.orka.myfinances.R
import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.data.api.debt.DebtApi
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.format.FormatDate
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.StateFul
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
    private val loading: UiText,
    logger: Logger
) : StateFul<State<DebtScreenModel>>(
    initialState = State.Loading(loading),
    logger = logger
), DebtScreenInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
    }

    override fun initialize() {
        launch {
            try {
                val debt = debtApi.getById(id.value)
                if (debt != null) {
                    val model = debt.map(formatPrice, formatDate)
                    setState(State.Success(model))
                } else setState(State.Failure(UiText.Res(R.string.failure)))
            } catch(e: Exception) {
                setState(State.Failure(UiText.Str(e.message.toString())))
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

    override fun refresh() {
        launch {
            try {
                setState(State.Loading(loading))
                val debt = debtApi.getById(id.value)
                if (debt != null) {
                    val model = debt.map(formatPrice, formatDate)
                    setState(State.Success(model))
                } else setState(State.Failure(UiText.Res(R.string.failure)))
            } catch(e: Exception) {
                setState(State.Failure(UiText.Str(e.message.toString())))
            }
        }
    }
}