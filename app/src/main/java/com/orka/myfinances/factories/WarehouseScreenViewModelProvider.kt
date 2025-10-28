package com.orka.myfinances.factories

import com.orka.myfinances.data.models.folder.Warehouse

interface WarehouseScreenViewModelProvider {
    fun warehouseViewModel(warehouse: Warehouse): Any
}