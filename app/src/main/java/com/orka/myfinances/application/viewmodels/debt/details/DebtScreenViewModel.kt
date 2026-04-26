package com.orka.myfinances.application.viewmodels.debt.details

import com.orka.myfinances.data.api.debt.DebtApi
import com.orka.myfinances.data.api.debt.models.response.DebtApiModel
import com.orka.myfinances.data.api.debt.setNotified
import com.orka.myfinances.data.api.debt.setPaid
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.api.getById
import com.orka.myfinances.lib.format.FormatDate
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.MapSingleViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.debt.details.DebtScreenModel
import com.orka.myfinances.ui.screens.debt.details.interactor.DebtScreenInteractor
import kotlinx.coroutines.flow.asStateFlow

class DebtScreenViewModel(
    id: Id,
    private val debtApi: DebtApi,
    private val formatPrice: FormatPrice,
    private val formatDate: FormatDate,
    private val navigator: Navigator,
    loading: UiText,
    failure: UiText,
    logger: Logger
) : MapSingleViewModel<DebtApiModel, DebtScreenModel>(
    id = id,
    get = { debtApi.getById(it) },
    map = { it.toScreenModel(formatPrice, formatDate) },
    loading = loading,
    failure = failure,
    logger = logger
), DebtScreenInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
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

    override fun setNotified(notified: Boolean) {
        launch {
            val oldState = state.value
            try {
                if (oldState is State.Success) {
                    setState(State.Loading(loading, oldState.value))
                    val success = debtApi.setNotified(id, notified)
                    if (success)
                        setState(State.Success(oldState.value.copy(notified  = notified)))
                    else setState(State.Success(oldState.value))
                } else {
                    setState(State.Failure(UiText.Str("Action executed in wrong state")))
                }
            } catch(e: Exception) {
                setState(State.Failure(error = UiText.Str(e.message.toString()), oldState.value))
            }
        }
    }

    override fun setPaid() {
        launch {
            val oldState = state.value
            try {
                if (oldState is State.Success) {
                    setState(State.Loading(loading, oldState.value))
                    val success = debtApi.setPaid(id)
                    if (success)
                        setState(State.Success(oldState.value.copy(completed = true)))
                    else setState(State.Success(oldState.value))
                } else {
                    setState(State.Failure(UiText.Str("Action executed in wrong state")))
                }
            } catch(e: Exception) {
                setState(State.Failure(error = UiText.Str(e.message.toString()), oldState.value))
            }
        }
    }
}