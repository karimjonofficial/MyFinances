package com.orka.myfinances.ui.screens.stock

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.models.card.StockItemCardModel

data class StockItemUiModel(
    val id: Id,
    val amount: Int,
    val salePrice: String,
    val exposedPrice: String,
    val model: StockItemCardModel,
)
