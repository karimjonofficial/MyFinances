package com.orka.myfinances.application.viewmodels.sale.details

import com.orka.myfinances.data.dtos.sale.SaleDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.repositories.GetById
import com.orka.myfinances.format.FormatDate
import com.orka.myfinances.format.FormatDecimal
import com.orka.myfinances.format.FormatPrice
import com.orka.myfinances.format.FormatTime
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.MapSingleViewModel
import com.orka.myfinances.printer.Printer
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.sale.details.interactor.SaleScreenInteractor
import com.orka.myfinances.ui.screens.sale.details.interactor.SaleScreenModel
import kotlinx.coroutines.flow.asStateFlow

class SaleScreenViewModel(
    id: Id,
    private val printer: Printer,
    private val getById: GetById<SaleDto>,
    private val formatPrice: FormatPrice,
    private val formatDate: FormatDate,
    private val formatTime: FormatTime,
    private val formatDecimal: FormatDecimal,
    private val navigator: Navigator,
    loading: UiText,
    failure: UiText,
    logger: Logger
) : MapSingleViewModel<SaleDto, SaleScreenModel>(
    id = id,
    get = getById,
    map = { it.toScreenModel(formatPrice, formatDate, formatTime, formatDecimal) },
    loading = loading,
    failure = failure,
    logger = logger
), SaleScreenInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
    }

    override fun print() {
        tryTransition {
            val response = getById.getById(id)
            if(response != null) {
                printer.print(response)
                State.Success(map(response))
            } else State.Failure(failure, it.value)
        }
    }

    override fun navigateToClient(clientId: Id) {
        launch {
            navigator.navigateToClient(clientId)
        }
    }

    override fun back() {
        launch {
            navigator.back()
        }
    }
}