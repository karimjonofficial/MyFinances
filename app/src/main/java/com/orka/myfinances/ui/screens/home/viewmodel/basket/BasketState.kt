package com.orka.myfinances.ui.screens.home.viewmodel.basket

import com.orka.myfinances.data.models.basket.BasketItem

interface BasketState {
    data object Loading : BasketState

    data class Success(
        val items: List<BasketItem>,
        val price: Int
    ) : BasketState
}