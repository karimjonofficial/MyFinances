package com.orka.myfinances.ui.screens.add.product.viewmodel

import com.orka.myfinances.data.models.folder.Warehouse

interface AddProductScreenState {
    data object Loading : AddProductScreenState
    data object Failure : AddProductScreenState
    data class Success(val warehouses: List<Warehouse>) : AddProductScreenState
}