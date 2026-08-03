package com.orka.myfinances.ui.models.ui

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.models.card.SaleCardModel

data class SaleUiModel(
    val model: SaleCardModel,
    val id: Id
)