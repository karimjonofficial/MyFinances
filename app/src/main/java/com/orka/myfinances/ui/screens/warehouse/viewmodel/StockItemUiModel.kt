package com.orka.myfinances.ui.screens.warehouse.viewmodel

import com.orka.myfinances.data.models.StockItem
import com.orka.myfinances.ui.screens.warehouse.components.StockItemCardModel

data class StockItemUiModel(
    val model: StockItemCardModel,
    val item: StockItem
)