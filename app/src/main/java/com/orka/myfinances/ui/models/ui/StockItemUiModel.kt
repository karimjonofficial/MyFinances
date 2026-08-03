package com.orka.myfinances.ui.models.ui

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.models.card.StockItemCardModel

data class StockItemUiModel(
    val id: Id,
    val amount: Int,
    val salePrice: Int,
    val exposedPrice: Int,
    val model: StockItemCardModel,
)