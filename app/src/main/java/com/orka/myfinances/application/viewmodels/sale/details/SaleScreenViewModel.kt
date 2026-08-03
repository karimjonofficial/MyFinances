package com.orka.myfinances.application.viewmodels.sale.details

import com.orka.myfinances.data.dtos.sale.SaleDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.repositories.GetById
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.lib.viewmodel.sourceful.single.MapSingleByIdViewModel
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.printer.Printer
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.statuses.failure.failure
import com.orka.myfinances.ui.screens.sale.details.SaleScreenInteractor
import com.orka.myfinances.ui.models.screen.SaleScreenModel
import kotlinx.coroutines.flow.asStateFlow

class SaleScreenViewModel(
    id: Id,
    private val printer: Printer,
    private val getById: GetById<SaleDto>,
    private val navigator: Navigator,
    logger: Logger
) : MapSingleByIdViewModel<SaleDto, SaleScreenModel>(
    id = id,
    get = getById,
    map = { it.toScreenModel() },
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
                printer.printSaleReceipt(response)
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