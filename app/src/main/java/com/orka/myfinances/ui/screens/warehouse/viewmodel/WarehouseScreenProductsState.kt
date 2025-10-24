package com.orka.myfinances.ui.screens.warehouse.viewmodel

import com.orka.myfinances.data.models.product.Product

sealed interface WarehouseScreenProductsState {
    data object Loading : WarehouseScreenProductsState
    data object Failure : WarehouseScreenProductsState
    data class Success(val products : List<Product>) : WarehouseScreenProductsState
}
