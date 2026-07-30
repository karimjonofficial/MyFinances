package com.orka.myfinances.ui.screens.basket

import com.orka.myfinances.data.models.basket.BasketItem

data class BasketScreenModel(
    val items: List<BasketItemUiModel>,
    val price: String,
    val rawItems: List<BasketItem> = emptyList(),
    val sellable: Boolean
)