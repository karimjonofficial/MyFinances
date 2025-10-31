package com.orka.myfinances.factories.viewmodel

import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Warehouse

interface ViewModelProvider {
    fun homeViewModel(): Any
    fun templatesViewModel(): Any
    fun addTemplateViewModel(): Any
    fun addProductViewModel(): Any
    fun warehouseViewModel(warehouse: Warehouse): Any
    fun catalogViewModel(catalog: Catalog): Any
}