package com.orka.myfinances.ui.screens.home.viewmodel.basket

interface BasketState {
    data object Loading : BasketState

    data class Success(
        val items: List<BasketItemUiModel>,
        val price: String
    ) : BasketState
}