package com.orka.myfinances.factories.viewmodel

import com.orka.myfinances.data.models.folder.Warehouse

interface WarehouseScreenViewModelProvider {
    fun warehouseViewModel(warehouse: Warehouse): Any
}