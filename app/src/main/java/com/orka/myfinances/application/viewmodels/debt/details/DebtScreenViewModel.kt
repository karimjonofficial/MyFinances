package com.orka.myfinances.application.viewmodels.debt.details

import com.orka.myfinances.data.dtos.debt.DebtDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.debt.DebtRepository
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
    private val repository: DebtRepository,
    private val formatPrice: FormatPrice,
    private val formatDate: FormatDate,
    private val navigator: Navigator,
    loading: UiText,
    failure: UiText,
    logger: Logger
) : MapSingleViewModel<DebtDto, DebtScreenModel>(
    id = id,
    get = { repository.getById(it) },
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
        tryTransition { oldState ->
            if (oldState is State.Success) {
                val success = repository.setNotified(id, notified)
                if (success)
                    State.Success(oldState.value.copy(notified = notified))
                else oldState
            } else {
                State.Failure(UiText.Str("Action executed in wrong state"), oldState.value)
            }
        }
    }

    override fun setPaid() {
        tryTransition { oldState ->
            if (oldState is State.Success) {
                val success = repository.setPaid(id)
                if (success) {
                    State.Success(oldState.value.copy(completed = true))
                } else oldState
            } else {
                State.Failure(UiText.Str("Action executed in wrong state"), oldState.value)
            }
        }
    }
}
