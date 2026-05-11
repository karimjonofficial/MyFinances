package com.orka.myfinances.ui.screens.stock

import com.orka.myfinances.data.models.Id

data class StockItemUiModel(
    val id: Id,
    val salePrice: String,
    val exposedPrice: String,
    val model: StockItemCardModel,
)
