package com.orka.myfinances.ui.screens.sale.viewmodel

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.sale.Sale
import com.orka.myfinances.lib.format.FormatDateTime
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.SingleStateViewModel
import kotlinx.coroutines.flow.asStateFlow

class SaleScreenViewModel(
    private val sale: Sale,
    private val formatPrice: FormatPrice,
    private val formatDateTime: FormatDateTime,
    private val formatDecimal: FormatDecimal,
    private val loading: UiText,
    logger: Logger
) : SingleStateViewModel<State>(
    initialState = State.Initial,
    logger = logger
) {
    val uiState = state.asStateFlow()

    override fun initialize() {
        launch {
            setState(State.Loading(loading))
            val model = sale.toUiModel(formatPrice, formatDateTime, formatDecimal)
            setState(State.Success(model))
        }
    }
}