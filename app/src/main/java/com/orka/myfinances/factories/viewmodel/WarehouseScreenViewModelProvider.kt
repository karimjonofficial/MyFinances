package com.orka.myfinances.factories.viewmodel

import com.orka.myfinances.data.models.folder.Category

interface WarehouseScreenViewModelProvider {
    fun warehouseViewModel(category: Category): Any
}