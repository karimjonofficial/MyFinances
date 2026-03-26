package com.orka.myfinances.application.viewmodels.sale.details

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.api.sale.SaleApi
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.format.FormatDateTime
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.SingleStateViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.sale.details.interactor.SaleScreenInteractor
import com.orka.myfinances.ui.screens.sale.details.interactor.SaleScreenModel
import kotlinx.coroutines.flow.asStateFlow

class SaleScreenViewModel(
    private val id: Id,
    private val saleApi: SaleApi,
    private val formatPrice: FormatPrice,
    private val formatDateTime: FormatDateTime,
    private val formatDecimal: FormatDecimal,
    private val navigator: Navigator,
    private val loading: UiText,
    private val failure: UiText,
    logger: Logger
) : SingleStateViewModel<State<SaleScreenModel>>(
    initialState = State.Loading(loading),
    logger = logger
), SaleScreenInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
    }

    override fun initialize() {
        launch {
            setState(State.Loading(loading))
            try {
                val sale = saleApi.getById(id.value)
                if (sale != null) {
                    val model = sale.map(formatPrice, formatDateTime, formatDecimal)
                    setState(State.Success(model))
                } else {
                    setState(State.Failure(failure))
                }
            } catch (e: Exception) {
                setState(State.Failure(UiText.Str(e.message.toString())))
            }
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