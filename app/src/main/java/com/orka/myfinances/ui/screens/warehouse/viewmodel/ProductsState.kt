package com.orka.myfinances.ui.screens.warehouse.viewmodel

sealed interface ProductsState {
    data object Loading : ProductsState
    data object Failure : ProductsState
    data class Success(val titles : List<ProductTitleUiModel>) : ProductsState
}
