package com.orka.myfinances.ui.screens.warehouse.viewmodel

import com.orka.myfinances.data.models.folder.Warehouse

sealed interface WarehouseScreenWarehouseState {
    data object Loading : WarehouseScreenWarehouseState
    data object Failure : WarehouseScreenWarehouseState
    data class Success(val warehouse: Warehouse) : WarehouseScreenWarehouseState
}