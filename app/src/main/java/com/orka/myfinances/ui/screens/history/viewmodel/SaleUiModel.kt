package com.orka.myfinances.ui.screens.history.viewmodel

import com.orka.myfinances.data.models.sale.Sale
import com.orka.myfinances.ui.screens.history.SaleCardModel

data class SaleUiModel(
    val model: SaleCardModel,
    val sale: Sale
)