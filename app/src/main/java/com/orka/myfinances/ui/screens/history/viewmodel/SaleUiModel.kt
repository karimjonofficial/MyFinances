package com.orka.myfinances.ui.screens.history.viewmodel

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.screens.history.components.SaleCardModel

data class SaleUiModel(
    val model: SaleCardModel,
    val id: Id
)