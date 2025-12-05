package com.orka.myfinances.ui.screens.warehouse.viewmodel

import com.orka.myfinances.data.models.product.Product

sealed interface ProductsState {
    data object Loading : ProductsState
    data object Failure : ProductsState
    data class Success(val products : List<Product>) : ProductsState
}
