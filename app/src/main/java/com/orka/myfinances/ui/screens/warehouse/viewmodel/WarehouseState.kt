package com.orka.myfinances.ui.screens.warehouse.viewmodel

import com.orka.myfinances.data.models.StockItem
import com.orka.myfinances.data.models.folder.Category

sealed interface WarehouseState {
    data object Loading : WarehouseState
    data object Failure : WarehouseState
    data class Success(val category: Category, val stockItems: List<StockItem>) : WarehouseState
}