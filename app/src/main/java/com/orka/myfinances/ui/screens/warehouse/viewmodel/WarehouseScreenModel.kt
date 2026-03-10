package com.orka.myfinances.ui.screens.warehouse.viewmodel

import com.orka.myfinances.data.models.folder.Category

data class WarehouseScreenModel(
    val category: Category,
    val productTitles: List<ProductTitleUiModel>,
    val stockItems: List<StockItemUiModel>
)