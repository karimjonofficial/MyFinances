package com.orka.myfinances.ui.screens.debt.viewmodel

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.Debt
import com.orka.myfinances.lib.format.FormatDate
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.SingleStateViewModel
import kotlinx.coroutines.flow.asStateFlow

class DebtScreenViewModel(
    private val debt: Debt,
    private val formatPrice: FormatPrice,
    private val formatDate: FormatDate,
    logger: Logger
) : SingleStateViewModel<State>(
    initialState = State.Initial,
    logger = logger
) {
    val uiState = state.asStateFlow()

    override fun initialize() {
        val model = debt.toScreenModel(formatPrice, formatDate)
        setState(State.Success(model))
    }
}