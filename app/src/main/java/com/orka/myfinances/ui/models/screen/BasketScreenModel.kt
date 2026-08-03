package com.orka.myfinances.ui.models.screen

import com.orka.myfinances.data.models.basket.BasketItem
import com.orka.myfinances.ui.models.ui.BasketItemUiModel

data class BasketScreenModel(
    val items: List<BasketItemUiModel>,
    val price: Int,
    val rawItems: List<BasketItem> = emptyList(),
    val sellable: Boolean
)