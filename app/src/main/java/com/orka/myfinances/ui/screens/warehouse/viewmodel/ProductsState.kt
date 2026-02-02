package com.orka.myfinances.ui.screens.warehouse.viewmodel

import com.orka.myfinances.data.models.product.ProductTitle

sealed interface ProductsState {
    data object Loading : ProductsState
    data object Failure : ProductsState
    data class Success(val productTitles : List<ProductTitle>) : ProductsState
}
