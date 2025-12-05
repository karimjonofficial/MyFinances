package com.orka.myfinances.ui.screens.warehouse.viewmodel

import com.orka.myfinances.data.models.StockItem
import com.orka.myfinances.data.models.folder.Warehouse

sealed interface WarehouseState {
    data object Loading : WarehouseState
    data object Failure : WarehouseState
    data class Success(val warehouse: Warehouse, val stockItems: List<StockItem>) : WarehouseState
}