package com.orka.myfinances.ui.screens.warehouse.viewmodel

data class WarehouseScreenModel(
    val title: String,
    val productTitles: List<ProductTitleUiModel>,
    val stockItems: List<StockItemUiModel>
)