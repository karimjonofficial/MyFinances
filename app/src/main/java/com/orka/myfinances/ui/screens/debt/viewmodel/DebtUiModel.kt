package com.orka.myfinances.ui.screens.debt.viewmodel

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.screens.debt.components.DebtCardModel

data class DebtUiModel(
    val model: DebtCardModel,
    val id: Id
)
