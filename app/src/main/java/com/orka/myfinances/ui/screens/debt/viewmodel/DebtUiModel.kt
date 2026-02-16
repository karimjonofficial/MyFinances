package com.orka.myfinances.ui.screens.debt.viewmodel

import com.orka.myfinances.data.models.Debt
import com.orka.myfinances.ui.screens.debt.components.DebtCardModel

data class DebtUiModel(
    val model: DebtCardModel,
    val debt: Debt
)