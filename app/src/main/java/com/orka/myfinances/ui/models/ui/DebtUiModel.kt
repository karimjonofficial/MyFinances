package com.orka.myfinances.ui.models.ui

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.models.card.DebtCardModel

data class DebtUiModel(
    val model: DebtCardModel,
    val id: Id
)