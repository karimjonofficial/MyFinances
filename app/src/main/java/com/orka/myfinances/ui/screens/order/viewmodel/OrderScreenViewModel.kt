package com.orka.myfinances.ui.screens.order.viewmodel

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.order.Order
import com.orka.myfinances.lib.format.FormatDate
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.SingleStateViewModel
import kotlinx.coroutines.flow.asStateFlow

class OrderScreenViewModel(
    private val order: Order,
    private val formatPrice: FormatPrice,
    private val formatDate: FormatDate,
    private val formatDecimal: FormatDecimal,
    logger: Logger
) : SingleStateViewModel<State>(
    initialState = State.Initial,
    logger = logger
) {
    val uiState = state.asStateFlow()

    override fun initialize() {
        setState(State.Success(order.toScreenModel(formatPrice, formatDate, formatDecimal)))
    }
}