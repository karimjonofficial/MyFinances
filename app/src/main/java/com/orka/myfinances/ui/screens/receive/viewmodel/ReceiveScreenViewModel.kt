package com.orka.myfinances.ui.screens.receive.viewmodel

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.receive.Receive
import com.orka.myfinances.lib.format.FormatDateTime
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.SingleStateViewModel
import kotlinx.coroutines.flow.asStateFlow

class ReceiveScreenViewModel(
    private val receive: Receive,
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
            val uiModel = receive.toUiModel(formatPrice, formatDateTime, formatDecimal)
            setState(State.Success(uiModel))
        }
    }
}
