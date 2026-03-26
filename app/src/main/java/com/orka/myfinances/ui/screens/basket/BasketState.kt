package com.orka.myfinances.ui.screens.basket

interface BasketState {
    data object Loading : BasketState

    data class Success(
        val items: List<BasketItemUiModel>,
        val price: String
    ) : BasketState
}