package com.orka.myfinances.application.viewmodels.sale.details

import com.orka.myfinances.data.api.sale.SaleApi
import com.orka.myfinances.data.api.sale.models.response.SaleApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.api.getById
import com.orka.myfinances.lib.format.FormatDateTime
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.viewmodel.MapSingleViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.sale.details.interactor.SaleScreenInteractor
import com.orka.myfinances.ui.screens.sale.details.interactor.SaleScreenModel
import kotlinx.coroutines.flow.asStateFlow

class SaleScreenViewModel(
    id: Id,
    private val saleApi: SaleApi,
    private val formatPrice: FormatPrice,
    private val formatDateTime: FormatDateTime,
    private val formatDecimal: FormatDecimal,
    private val navigator: Navigator,
    loading: UiText,
    failure: UiText,
    logger: Logger
) : MapSingleViewModel<SaleApiModel, SaleScreenModel>(
    id = id,
    get = { saleApi.getById(it) },
    map = { it.toScreenModel(formatPrice, formatDateTime, formatDecimal) },
    loading = loading,
    failure = failure,
    logger = logger
), SaleScreenInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
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