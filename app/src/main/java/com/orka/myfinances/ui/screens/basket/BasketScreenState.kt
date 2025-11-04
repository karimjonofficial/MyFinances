package com.orka.myfinances.ui.screens.basket

import com.orka.myfinances.data.models.basket.BasketItem

interface BasketScreenState {
    data object Loading : BasketScreenState

    data class Success(
        val items: List<BasketItem>,
        val price: Int
    ) : BasketScreenState
}
