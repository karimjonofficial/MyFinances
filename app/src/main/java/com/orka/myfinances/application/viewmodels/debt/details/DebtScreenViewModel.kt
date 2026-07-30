package com.orka.myfinances.application.viewmodels.debt.details

import com.orka.myfinances.data.dtos.debt.DebtDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.debt.SetNotified
import com.orka.myfinances.data.repositories.debt.SetPaid
import com.orka.myfinances.format.FormatDate
import com.orka.myfinances.format.FormatPrice
import com.orka.myfinances.lib.data.repositories.GetById
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.lib.viewmodel.sourceful.chunk.ExecutedFromFailure
import com.orka.myfinances.lib.viewmodel.sourceful.single.MapSingleByIdViewModel
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.debt.details.DebtScreenModel
import com.orka.myfinances.ui.screens.debt.details.interactor.DebtScreenInteractor
import kotlinx.coroutines.flow.asStateFlow

class DebtScreenViewModel(
    id: Id,
    getById: GetById<DebtDto>,
    private val setPaid: SetPaid,
    private val setNotified: SetNotified,
    private val formatPrice: FormatPrice,
    private val formatDate: FormatDate,
    private val navigator: Navigator,
    logger: Logger
) : MapSingleByIdViewModel<DebtDto, DebtScreenModel>(
    id = id,
    get = getById,
    map = { it.toScreenModel(formatPrice, formatDate) },
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
                val success = setNotified.setNotified(id, notified)
                if (success)
                    State.Success(oldState.value.copy(notified = notified))
                else oldState
            } else State.Failure(ExecutedFromFailure, oldState.value)
        }
    }

    override fun setPaid() {
        tryTransition { oldState ->
            if (oldState is State.Success) {
                val success = setPaid.setPaid(id)
                if (success)
                    State.Success(oldState.value.copy(completed = true))
                else oldState
            } else State.Failure(ExecutedFromFailure, oldState.value)
        }
    }
}
